package com.khalej.storejoud.Activity;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khalej.storejoud.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {
    LinearLayout main,category,cart,offers,account;
    TextView main_text,category_text,cart_text,offers_text,account_text;
    ImageView main_img,category_img,cart_img,offers_img,account_img;
    Realm realm;
    String lang;
    int x=0;
    String token="";
    Intent intent;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang = sharedpref.getString("language", "").trim();
        if (lang.equals(null)) {
            edt.putString("language", "ar");
            lang = "en";
            edt.apply();
        }
        intent = getIntent();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        Intialize();
        Fragment fragment = new main_fragment();
        loadFragment(fragment);
        main.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                allgray();
                main_img.setBackgroundResource(R.drawable.ic_home_color);
                main_text.setTextColor(getResources().getColor(R.color.orange));
                Fragment fragment;
                fragment = new main_fragment();
                loadFragment(fragment);
                x=0;
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                allgray();
                category_img.setBackgroundResource(R.drawable.ic_search_color);
                category_text.setTextColor(getResources().getColor(R.color.orange));
                Fragment fragment;
                fragment = new category_fragment();
                loadFragment(fragment);
                x=1;
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                allgray();
                cart_img.setBackgroundResource(R.drawable.ic_cart_color);
                cart_text.setTextColor(getResources().getColor(R.color.orange));
                Fragment fragment;
                fragment = new basket_fragment();
                loadFragment(fragment);
                x=1;
            }
        });
        offers.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                allgray();
                offers_img.setBackgroundResource(R.drawable.ic_offer_color);
                offers_text.setTextColor(getResources().getColor(R.color.orange));
                Fragment fragment;
                fragment = new Offers_fragment();
                loadFragment(fragment);
                x=1;
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                allgray();
                account_img.setBackgroundResource(R.drawable.ic_user_color);
                account_text.setTextColor(getResources().getColor(R.color.orange));
                Fragment fragment;
                fragment = new account_fragment();
                loadFragment(fragment);
                x=1;
            }
        });
    }
    public void Intialize(){
        main=findViewById(R.id.main);
        category=findViewById(R.id.category);
        cart=findViewById(R.id.cart);
        offers=findViewById(R.id.offers);
        account=findViewById(R.id.account);
        main_text=findViewById(R.id.main_text);
        category_text=findViewById(R.id.category_text);
        category_img=findViewById(R.id.category_img);
        main_img=findViewById(R.id.main_img);
        cart_text=findViewById(R.id.cart_text);
        cart_img=findViewById(R.id.cart_img);
        offers_text=findViewById(R.id.offers_text);
        offers_img=findViewById(R.id.offers_img);
        account_text=findViewById(R.id.account_text);
        account_img=findViewById(R.id.account_img);
    }
    @SuppressLint("ResourceAsColor")
    public void allgray(){
        main_img.setBackgroundResource(R.drawable.ic_home);
        main_text.setTextColor(getResources().getColor(R.color.header_gray));
        category_img.setBackgroundResource(R.drawable.ic_search);
        category_text.setTextColor(getResources().getColor(R.color.header_gray));
        cart_img.setBackgroundResource(R.drawable.ic_cart);
        cart_text.setTextColor(getResources().getColor(R.color.header_gray));
        offers_img.setBackgroundResource(R.drawable.ic_offer);
        offers_text.setTextColor(getResources().getColor(R.color.header_gray));
        account_img.setBackgroundResource(R.drawable.ic_user);
        account_text.setTextColor(getResources().getColor(R.color.header_gray));
    }
    @Override
    public void onBackPressed() {
        if(x==0){
            finish();}
        else{
            Fragment fragment;
            fragment = new main_fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",intent.getIntExtra("id",0));
            fragment.setArguments(bundle);
            loadFragment(fragment);

            x=0;
        }
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
