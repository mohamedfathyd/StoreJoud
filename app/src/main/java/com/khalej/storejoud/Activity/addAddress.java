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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;

import java.util.HashMap;

public class addAddress extends AppCompatActivity {
    ImageView back;
    EditText firstName,secondName,address,city,zip,phone;
    AppCompatButton appCompatButtonRegister;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    ProgressDialog progressDialog;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_add_address);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        back=findViewById(R.id.back);
        firstName=findViewById(R.id.firstName);
        secondName=findViewById(R.id.secondName);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        zip=findViewById(R.id.zip);
        phone=findViewById(R.id.phone);
        appCompatButtonRegister=findViewById(R.id.appCompatButtonRegister);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchAddAddress();
            }
        });
    }
    public void fetchAddAddress(){
        progressDialog = ProgressDialog.show(addAddress.this, "جاري أضافة عنوان جديد", "Please wait...", false, false);
        progressDialog.show();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        String name=firstName.getText().toString()+" "+secondName.getText().toString();
        Call<ResponseBody> call= apiinterface.getcontacts_addaddress(headers,name,phone.getText().toString(),address.getText().toString(),
               city.getText().toString(),zip.getText().toString() );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){


                    try {
                        Dialog dialog1;
                        dialog1 = new Dialog(addAddress.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        TextView message=dialog1.findViewById(R.id.message);
                        message.setText("تم أضافة عنوان جديد بنجاح");
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
