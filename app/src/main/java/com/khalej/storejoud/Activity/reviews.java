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

import com.khalej.storejoud.Adapter.RecyclerAdapter_cart;
import com.khalej.storejoud.Adapter.RecyclerAdapter_review;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_reviews;
import com.khalej.storejoud.model.contact_reviews;

import java.util.HashMap;
import java.util.List;

public class reviews extends AppCompatActivity {
    ImageView back;
    AppCompatButton appCompatButtonRegister;
    Intent intent;
    String id;
    contact_reviews contact_reviews;
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewCart;
    private RecyclerView.LayoutManager layoutManager;
    List<contact_reviews.reviews>reviews;
    RecyclerAdapter_review recyclerAdapter_review;
    ProgressBar progressBar;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        intent=getIntent();
        id=intent.getStringExtra("id");
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
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
        appCompatButtonRegister=findViewById(R.id.appCompatButtonRegister);
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i =new Intent(reviews.this,write_review.class);
              i.putExtra("id",id);
              startActivity(i);
              finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fetchInfo_reviews();
    }
    public void fetchInfo_reviews() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_reviews> call = apiinterface.getcontacts_reviews(headers,id);
        call.enqueue(new Callback<contact_reviews>() {
            @Override
            public void onResponse(Call<contact_reviews> call, Response<contact_reviews> response) {
                progressBar.setVisibility(View.GONE);
                contact_reviews=response.body();
                try {
                    reviews=contact_reviews.getPayload();
                    if (reviews.size()!=0||!(reviews.isEmpty())) {
                        recyclerAdapter_review = new RecyclerAdapter_review(reviews.this, reviews);
                        recyclerviewCart.setAdapter(recyclerAdapter_review);

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_reviews> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
