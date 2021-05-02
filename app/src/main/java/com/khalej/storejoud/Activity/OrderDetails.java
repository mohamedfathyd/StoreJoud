package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.khalej.storejoud.R;
import com.khalej.storejoud.model.contact_myOrders;

import java.util.List;

public class OrderDetails extends AppCompatActivity {
    ImageView back;
    List<contact_myOrders.products>products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order_details);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}