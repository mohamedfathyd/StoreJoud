package com.khalej.storejoud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.khalej.storejoud.R;
import com.khalej.storejoud.model.apiinterface_home;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class account_fragment extends Fragment {
    private apiinterface_home apiinterface;
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout profile,address,lang,order,logOut;

    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
       // id = getArguments().getInt("id");

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        profile=view.findViewById(R.id.profile);
        logOut=view.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putString("imageProfile","");
                edt.putInt("type",0);
                edt.putFloat("wallet",0);
                edt.putString("token","");
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),account.class));
            }
        });
        address=view.findViewById(R.id.address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),addresses.class));
            }
        });
        lang=view.findViewById(R.id.lang);
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ChangeLang.class));
            }
        });
        order=view.findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Order.class));
            }
        });

        return view;
    }

}
