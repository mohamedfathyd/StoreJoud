package com.khalej.storejoud.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.storejoud.Activity.Product_fragment;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_category;
import com.khalej.storejoud.model.contact_single_product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_colors extends RecyclerView.Adapter<RecyclerAdapter_colors.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_single_product.colors> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;

    public RecyclerAdapter_colors(Context context, List<contact_single_product.colors> contactslist){
        this.contactslist=contactslist;
        this.context=context;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.color_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {

            holder.image.setColorFilter(Color.parseColor(contactslist.get(position).getColor()));
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.color);

        }
    }



}