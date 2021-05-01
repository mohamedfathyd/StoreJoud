package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.khalej.storejoud.Adapter.RecyclerAdapter_notification;
import com.khalej.storejoud.Adapter.RecyclerAdapter_review;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_notifications;
import com.khalej.storejoud.model.contact_notifications;

import java.util.HashMap;
import java.util.List;

public class offer_notification extends AppCompatActivity {
    ImageView back;
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewCart;
    private RecyclerView.LayoutManager layoutManager;
    List<contact_notifications.notification> notifications;
    contact_notifications contact_notifications;
    RecyclerAdapter_notification recyclerAdapter_notification;
    ProgressBar progressBar;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_notification);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fetchInfo_notifications();
    }

    public void fetchInfo_notifications() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_notifications> call = apiinterface.getcontacts_notifications(headers);
        call.enqueue(new Callback<contact_notifications>() {
            @Override
            public void onResponse(Call<contact_notifications> call, Response<contact_notifications> response) {
                progressBar.setVisibility(View.GONE);
                contact_notifications=response.body();
                try {
                    notifications=contact_notifications.getPayload();
                    if (notifications.size()!=0||!(notifications.isEmpty())) {
                        recyclerAdapter_notification = new RecyclerAdapter_notification(offer_notification.this, notifications);
                        recyclerviewCart.setAdapter(recyclerAdapter_notification);

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_notifications> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

