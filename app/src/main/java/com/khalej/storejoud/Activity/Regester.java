package com.khalej.storejoud.Activity;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_general_user;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Regester extends AppCompatActivity {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AppCompatButton regeister;
    private apiinterface_home apiinterface;
    private contact_general_user contactList;
    AppCompatButton appCompatButtonRegisterservcies;
    ProgressDialog progressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    CallbackManager callbackManager;
    CheckBox Terms;
    TextView ShowTerms;
    Intent intent;
    EditText textInputEditTextphone,textInputEditTextuser,textInputEditTextemail,textInputEditTextpass;
    String mVerificationId,code;
    private FirebaseAuth mAuth;
    String codee="966";
    CountryCodePicker ccp;
    ProgressDialog progressDialog1;
    EditText num;  Dialog dialog;
    CheckBox checkBox;
    TextView login;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        setContentView(R.layout.register);
        inisialize();

        intent=getIntent();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        login=findViewById(R.id.login);
        textInputEditTextphone=findViewById(R.id.textInputEditTextphone);
        textInputEditTextuser=findViewById(R.id.textInputEditTextuser);
        textInputEditTextemail=findViewById(R.id.textInputEditTextemail);
        textInputEditTextpass=findViewById(R.id.textInputEditTextpass);
        checkBox=findViewById(R.id.check);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Regester.this,Login.class));
            }
        });
        appCompatButtonRegisterservcies=findViewById(R.id.appCompatButtonRegisterservcies);
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textInputEditTextpass.getText().toString().length() <= 5) {
                    Toast.makeText(Regester.this, "كلمة المرور يجب ان تتكون من 6 احرف او ارقام على الأقل", Toast.LENGTH_LONG).show();
                    return;
                }
                if(textInputEditTextuser.getText().toString().equals("")||textInputEditTextpass.getText().toString().equals("")
                        ||textInputEditTextemail.getText().toString().equals("")||textInputEditTextphone.getText().toString().equals("")){
                    Toast.makeText(Regester.this,"من فضلك ادخل بيانات التسجيل كامله" ,Toast.LENGTH_LONG).show();
                    return;
                }
                fetchInfo();

            }
        });
    }


    public void fetchInfo() {
        progressDialog = ProgressDialog.show(Regester.this, "جاري انشاء الحساب", "Please wait...", false, false);
        progressDialog.show();
         apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user> call = apiinterface.getcontacts_newaccount(textInputEditTextuser.getText().toString(),textInputEditTextpass.getText().toString(),
                textInputEditTextemail.getText().toString(),textInputEditTextphone.getText().toString());
        call.enqueue(new Callback<contact_general_user>() {
            @Override
            public void onResponse(Call<contact_general_user> call, Response<contact_general_user> response) {
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   // Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(Regester.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                contactList = response.body();

                try{
                progressDialog.dismiss();
                edt.putString("id",contactList.getPayload().getUser_info().getId());
                edt.putString("name",contactList.getPayload().getUser_info().getFull_name());
                edt.putString("phone",contactList.getPayload().getUser_info().getPhone());
                edt.putString("address",contactList.getPayload().getUser_info().getEmail());
                    edt.putString("type","customer");
                edt.putString("token",contactList.getPayload().getToken());
                edt.putString("remember","yes");
                edt.apply();
                    Dialog dialog1;
                    dialog1 = new Dialog(Regester.this);
                    dialog1.setContentView(R.layout.dialog_success);
                    dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView message=dialog1.findViewById(R.id.message);
                    message.setText("تم تفعيل حسابك بنجاح");
                    dialog1.show();
                    startActivity(new Intent(Regester.this,MainActivity.class));}
                catch (Exception e){
                    Toast.makeText(Regester.this, e+"", Toast.LENGTH_LONG).show();
                    Log.d("message2",e+"");
                }


            }

            @Override
            public void onFailure(Call<contact_general_user> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Regester.this, "هناك خطأ بالتسجيل حاول مره أخري", Toast.LENGTH_LONG).show();


            }
        });
    }
    public void inisialize() {
        Terms=findViewById(R.id.check);
        ShowTerms=findViewById(R.id.showterms);
        regeister = (AppCompatButton) findViewById(R.id.appCompatButtonRegisterservcies);

    }

}
