package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_general_user_update;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class PhoneUpdate extends AppCompatActivity {
    ImageView back;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AppCompatButton save;
    EditText phone;
    contact_general_user_update userData;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_phone_update);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        back=findViewById(R.id.back);
        phone=findViewById(R.id.phone);
        save=findViewById(R.id.save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PhoneUpdate.this,account.class));
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone.getText().toString()!=null){
                    fetchwithoutImage();
                }
                else{
                    Toast.makeText(PhoneUpdate.this,"???? ???????? ???????? ?????????? ???????? ????????????",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void fetchwithoutImage(){
        progressDialog = ProgressDialog.show(PhoneUpdate.this, "???????? ?????????? ????????????????", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user_update> call = apiinterface.updateProfile(headers,sharedpref.getString("name","")
                ,sharedpref.getString("address",""),phone.getText().toString());
        call.enqueue(new Callback<contact_general_user_update>() {
            @Override
            public void onResponse(Call<contact_general_user_update> call, Response<contact_general_user_update> response) {
                progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(PhoneUpdate.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(),"???????? ???????????? ?????????????? ???? ??????  ???? ???????? ???? ?????? ?????????? ???????????????? ???????? ????????",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                userData=response.body();
                //  Toast.makeText(getContext(),userData.isStatus()+"",Toast.LENGTH_LONG).show();
                edt.putString("name",userData.getPayload().getFull_name());
                edt.putString("phone",userData.getPayload().getPhone());
                edt.putString("address",userData.getPayload().getEmail());
                edt.apply();
                Dialog dialog1;
                dialog1 = new Dialog(PhoneUpdate.this);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText("???? ?????????? ?????? ???????????? ??????????");
                dialog1.show();
            }

            @Override
            public void onFailure(Call<contact_general_user_update> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
