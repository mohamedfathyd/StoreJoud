package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khalej.storejoud.R;

import java.util.Calendar;

public class BirthDateUpdate extends AppCompatActivity {
    ImageView back;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AppCompatButton save;
    TextView Bdate;
    String datee="";
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_birth_date_update);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        back=findViewById(R.id.back);
        Bdate=findViewById(R.id.date);
        save=findViewById(R.id.save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(BirthDateUpdate.this,account.class));
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putString("birthDate",datee);
                edt.apply();
                Dialog dialog1;
                dialog1 = new Dialog(BirthDateUpdate.this);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText("تم تعديل تاريخ الميلاد  بنجاح");
                dialog1.show();
            }
        });
//        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
        Bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(BirthDateUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String month="";
                                if((monthOfYear+1)<10){
                                    month="0"+(monthOfYear+1);
                                }
                                else{
                                    month= String.valueOf((monthOfYear+1));
                                }
                                String day="";
                                if((dayOfMonth+1)<10){
                                    day="0"+(dayOfMonth);
                                }
                                else{
                                    day= String.valueOf((dayOfMonth));
                                }
                                Bdate.setText(year + "-" + month+ "-" + day);
                                datee=year + "-" + month+ "-" + day;
                            }
                        }, day, month, year);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });
    }
}