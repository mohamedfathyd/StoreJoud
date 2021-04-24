package com.khalej.storejoud.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.khalej.storejoud.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;

public class SplashActivity extends AppCompatActivity {
    ImageView i;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange));
        setContentView(R.layout.activity_splash);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);

        i = (ImageView) findViewById(R.id.imageView);
        i.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(sharedpref.getString("remember","").trim().equals("yes")){
                    edt.putFloat("totalprice",0);
                    edt.apply();
                    if(sharedpref.getString("type","").equals("customer")){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));}
                    else{
                        edt.putString("language","ar");
                        edt.apply();
                         startActivity(new Intent(SplashActivity.this, IntroOne.class));
                    }

                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, IntroOne.class));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}