package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.khalej.storejoud.Adapter.RecyclerAdapter_orders;
import com.khalej.storejoud.Adapter.RecyclerAdapter_review;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_myOrders;
import com.khalej.storejoud.model.contact_reviews;

import java.util.HashMap;
import java.util.List;

public class Order extends AppCompatActivity {
    ImageView back;
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewCart;
    private RecyclerView.LayoutManager layoutManager;
    List<contact_myOrders.orders> orders;
    RecyclerAdapter_orders recyclerAdapter_orders;
    ProgressBar progressBar;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    contact_myOrders contact_myOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        back=findViewById(R.id.back);
        recyclerviewCart=findViewById(R.id.recyclerviewCart);
        progressBar=findViewById(R.id.progressBar_subject);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewCart.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewCart.setHasFixedSize(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fetchInfo_orders();
    }
    public void fetchInfo_orders() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_myOrders> call = apiinterface.getcontacts_myOrders(headers);
        call.enqueue(new Callback<contact_myOrders>() {
            @Override
            public void onResponse(Call<contact_myOrders> call, Response<contact_myOrders> response) {
                progressBar.setVisibility(View.GONE);
                contact_myOrders=response.body();
                try {
                    orders=contact_myOrders.getPayload();
                    if (orders.size()!=0||!(orders.isEmpty())) {
                        recyclerAdapter_orders = new RecyclerAdapter_orders(Order.this, orders);
                        recyclerviewCart.setAdapter(recyclerAdapter_orders);

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_myOrders> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}