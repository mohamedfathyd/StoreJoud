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

import com.khalej.storejoud.Adapter.RecyclerAdapter_address;
import com.khalej.storejoud.Adapter.RecyclerAdapter_review;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_address;
import com.khalej.storejoud.model.contact_address;

import java.util.HashMap;
import java.util.List;

public class addresses extends AppCompatActivity {
    ImageView back;
    AppCompatButton addAddress;
    contact_address contact_address;
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewCart;
    private RecyclerView.LayoutManager layoutManager;
    List<contact_address.address> addresses;
    RecyclerAdapter_address recyclerAdapter_address;
    ProgressBar progressBar;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_addresses);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        recyclerviewCart=findViewById(R.id.recyclerviewCart);
        progressBar=findViewById(R.id.progressBar_subject);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewCart.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewCart.setHasFixedSize(true);
        back=findViewById(R.id.back);
        addAddress=findViewById(R.id.appCompatButtonRegister);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addresses.this,addAddress.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fetchInfo_address();
    }
    public void fetchInfo_address() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_address> call = apiinterface.getcontacts_address(headers);
        call.enqueue(new Callback<contact_address>() {
            @Override
            public void onResponse(Call<contact_address> call, Response<contact_address> response) {
                progressBar.setVisibility(View.GONE);
                contact_address=response.body();
                try {
                    addresses=contact_address.getPayload();
                    if (addresses.size()!=0||!(addresses.isEmpty())) {
                        recyclerAdapter_address = new RecyclerAdapter_address(addresses.this, addresses);
                        recyclerviewCart.setAdapter(recyclerAdapter_address);

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_address> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}