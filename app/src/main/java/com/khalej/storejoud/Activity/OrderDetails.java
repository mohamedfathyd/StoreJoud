package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.khalej.storejoud.R;

public class OrderDetails extends AppCompatActivity {
    ImageView back;
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