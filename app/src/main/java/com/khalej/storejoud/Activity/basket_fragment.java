package com.khalej.storejoud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.storejoud.Adapter.RecyclerAdapter_cart;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products_inside;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_cart;
import com.khalej.storejoud.model.contact_cart;
import com.khalej.storejoud.model.contact_copon;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class basket_fragment extends Fragment {
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewCart;
    private RecyclerView.LayoutManager layoutManager;


    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;
    ProgressBar progressBar;
    contact_cart contact_cart;
    contact_copon contact_copon;
    List<contact_cart.cart> cartList;
    RecyclerAdapter_cart recyclerAdapter_cart;
    EditText copon;
    TextView total,ship,charge,finaltotal,cobounAdd;
    double discount;
    AppCompatButton appCompatButton;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_basket, container, false);
       // id = getArguments().getInt("id");

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        recyclerviewCart=view.findViewById(R.id.recyclerviewCart);
        progressBar=view.findViewById(R.id.progressBar_subject);
        copon=view.findViewById(R.id.copon);
        total=view.findViewById(R.id.total);
        ship=view.findViewById(R.id.ship);
        charge=view.findViewById(R.id.charge);
        cobounAdd=view.findViewById(R.id.cobounAdd);
        cobounAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 fetchInfo_copon();
            }
        });
        finaltotal=view.findViewById(R.id.finaltotal);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewCart.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewCart.setHasFixedSize(true);
        fetchInfo_products();
        appCompatButton=view.findViewById(R.id.appCompatButtonRegister);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),addresses_order.class);
                intent.putExtra("promoCode",copon.getText().toString());
                startActivity(intent);

            }
        });
        return view;
    }
    public void fetchInfo_products() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerviewCart.setVisibility(View.VISIBLE);
        recyclerviewCart.setAdapter(null);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);

        recyclerviewCart.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewCart.setHasFixedSize(true);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_cart> call = apiinterface.getcontacts_cart(headers,sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_cart>() {
            @Override
            public void onResponse(Call<contact_cart> call, Response<contact_cart> response) {
                progressBar.setVisibility(View.GONE);
                contact_cart=response.body();
                try {
                    cartList=contact_cart.getPayload();
                    if (cartList.size()!=0||!(cartList.isEmpty())) {
                        recyclerAdapter_cart = new RecyclerAdapter_cart(getActivity(), cartList,basket_fragment.this);
                        recyclerviewCart.setAdapter(recyclerAdapter_cart);

                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        cartList.clear();
                        recyclerviewCart.removeAllViews();
                        recyclerviewCart.removeAllViewsInLayout();
                        recyclerviewCart.setVisibility(View.GONE);
                    }
                    double totall=0;
                    for(int i=0;cartList.size()>0;i++){
                        totall+=cartList.get(i).getTotal_price();
                        total.setText(totall+"$");
                        finaltotal.setText(totall+"$");
                        ship.setText(0.0+"$");
                        charge.setText(0.0+"$");
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_cart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void fetchInfo_copon() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_copon> call = apiinterface.getcontacts_copon(headers,copon.getText().toString());
        call.enqueue(new Callback<contact_copon>() {
            @Override
            public void onResponse(Call<contact_copon> call, Response<contact_copon> response) {
                progressBar.setVisibility(View.GONE);
                contact_copon=response.body();
                try {
                    discount=contact_copon.getPayload().getDiscount();
                    double totall= (Double.parseDouble(finaltotal.getText().toString())*(discount/100));
                    total.setText(totall+"$");
                    finaltotal.setText(totall+"$");
                } catch (Exception e) {
                    Toast.makeText(getContext(),"كود غير صالح",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<contact_copon> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
