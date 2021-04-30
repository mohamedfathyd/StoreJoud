package com.khalej.storejoud.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.khalej.storejoud.model.contact_cart;
import com.khalej.storejoud.model.contact_category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_cart extends RecyclerView.Adapter<RecyclerAdapter_cart.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_cart.cart> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;

    public RecyclerAdapter_cart(Context context, List<contact_cart.cart> contactslist){
        this.contactslist=contactslist;
        this.context=context;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            holder.name.setText(contactslist.get(position).getProduct().getName_by_lang());
            holder.count.setText(contactslist.get(position).getQuantity()+"");
            holder.price.setText(contactslist.get(position).getTotal_price()+"$");
            Glide.with(context).load("https://storejoud.com/"+
                    contactslist.get(position).getProduct().getMedia_links().get(0)).error(R.drawable.logocolor).into(holder.image);


        }
        catch (Exception e){}
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
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

        ImageView image,minus,plus;
        TextView name,price,count;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            count=itemView.findViewById(R.id.count);
            minus=itemView.findViewById(R.id.minus);
            plus=itemView.findViewById(R.id.plus);

        }
    }



}