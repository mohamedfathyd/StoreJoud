package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.khalej.storejoud.R;

public class SelectLang extends AppCompatActivity {
    AppCompatButton a;
    Spinner lang;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.whitee));
        setContentView(R.layout.activity_select_lang);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        a=findViewById(R.id.one);
        lang=findViewById(R.id.lang);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(lang.getSelectedItem().toString().equals("English")){
               edt.putString("language","en");
               edt.apply();
           }else{
               edt.putString("language","ar");
               edt.apply();
           }
                startActivity(new Intent(SelectLang.this, Login.class));
            }
        });
    }
}
