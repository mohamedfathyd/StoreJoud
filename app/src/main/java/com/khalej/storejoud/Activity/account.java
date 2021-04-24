package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khalej.storejoud.R;

public class account extends AppCompatActivity {
    ImageView back;
    LinearLayout genderUpdate,dateUpdate,emailUpdate,phoneUpdate,passUpdate;
    TextView name,gender,phone,phoneInside,pass,email,date;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_account);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        Intialize();
        gender.setText(sharedpref.getString("gender","male"));
        name.setText(sharedpref.getString("name","Store Joud"));
        phone.setText(sharedpref.getString("phone","+02000000000"));
        phoneInside.setText(sharedpref.getString("phone","+02000000000"));
        email.setText(sharedpref.getString("address","Store.Joud@gmail.com"));
        date.setText(sharedpref.getString("birthDate","20-10-2020"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        genderUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(account.this,GenderUpdate.class));
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,NameUpdate.class));
            }
        });
        phoneUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,PhoneUpdate.class));
            }
        });
        emailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,EmailUpdate.class));
            }
        });
        passUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,PasswordUpdate.class));
            }
        });
        dateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,BirthDateUpdate.class));
            }
        });

    }
    public void Intialize(){
        back=findViewById(R.id.back);
        genderUpdate=findViewById(R.id.genderUpdate);
        dateUpdate=findViewById(R.id.dateUpdate);
        emailUpdate=findViewById(R.id.emailUpdate);
        phoneUpdate=findViewById(R.id.phoneUpdate);
        passUpdate=findViewById(R.id.passUpdate);
        name=findViewById(R.id.Username);
        gender=findViewById(R.id.gender);
        phone=findViewById(R.id.phone);
        phoneInside=findViewById(R.id.phone_inside);
        pass=findViewById(R.id.password);
        email=findViewById(R.id.email);
        date=findViewById(R.id.birthdate);
    }
}
