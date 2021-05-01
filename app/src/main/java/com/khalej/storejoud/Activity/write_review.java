package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;

import java.util.HashMap;

public class write_review extends AppCompatActivity {
    ImageView back;
    RatingBar rate;
    EditText rateText;
    AppCompatButton appCompatButtonRegister;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Intent intent;
    String id;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        intent=getIntent();
        id=intent.getStringExtra("id");
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        back = findViewById(R.id.back);
        rate = findViewById(R.id.rate);
        rateText = findViewById(R.id.rateText);
        appCompatButtonRegister=findViewById(R.id.appCompatButtonRegister);
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 fetchAddToFavourit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void fetchAddToFavourit(){
        progressDialog = ProgressDialog.show(write_review.this, "جاري أضافة تعليقك", "Please wait...", false, false);
        progressDialog.show();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        Call<ResponseBody> call= apiinterface.getcontacts_addreview(headers,id,rateText.getText().toString(), (int) rate.getRating());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){


                    try {
                        Dialog dialog1;
                        dialog1 = new Dialog(write_review.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        TextView message=dialog1.findViewById(R.id.message);
                        message.setText("تم أضافة تعليقك بنجاح");
                        dialog1.show();
                    }
                    catch (Exception e){
                        progressDialog.dismiss();

                    }
                }
                else{
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}

