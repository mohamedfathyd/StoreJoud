package com.khalej.storejoud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products_inside;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products_inside_f;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_products;

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

public class Favourit_fragment extends Fragment {
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewProduct;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    RelativeLayout order;
    private RecyclerAdapter_first_products_inside_f recyclerAdapter_first_products;
    contact_products contact_products;
    List<contact_products.product> productList;
    ProgressBar progressBar;
    int x = 0;
    LinearLayout error;
    int y = 0;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;
    AppCompatButton appCompatButtonRegisterservcies;
    String name;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product, container, false);
        appCompatButtonRegisterservcies=view.findViewById(R.id.appCompatButtonRegisterservcies);
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_fragment nextFrag= new main_fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        error=view.findViewById(R.id.error);
        progressBar=view.findViewById(R.id.progressBar_subject);
        recyclerviewProduct = (RecyclerView) view.findViewById(R.id.recyclerviewProduct);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewProduct.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewProduct.setHasFixedSize(true);
        fetchInfo_productsById();
        return view;
    }

    public void fetchInfo_productsById() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerviewProduct.setVisibility(View.VISIBLE);
        recyclerviewProduct.setAdapter(null);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);

        recyclerviewProduct.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewProduct.setHasFixedSize(true);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_products> call = apiinterface.getcontacts_products_favorites(headers,sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_products>() {
            @Override
            public void onResponse(Call<contact_products> call, Response<contact_products> response) {
                progressBar.setVisibility(View.GONE);
                contact_products=response.body();
                try {
                    productList=contact_products.getPayload();
                    if (productList.size()!=0||!(productList.isEmpty())) {
                        recyclerAdapter_first_products = new RecyclerAdapter_first_products_inside_f(getActivity(), productList,
                                Favourit_fragment.this);
                        recyclerviewProduct.setAdapter(recyclerAdapter_first_products);

                    }
                    else{
                        error.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        productList.clear();
                        recyclerviewProduct.removeAllViews();
                        recyclerviewProduct.removeAllViewsInLayout();
                        recyclerviewProduct.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_products> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
