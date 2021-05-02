package com.khalej.storejoud.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.storejoud.Activity.OrderDetails;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_myOrders;
import com.khalej.storejoud.model.contact_reviews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_orders extends RecyclerView.Adapter<RecyclerAdapter_orders.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_myOrders.orders> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;

    public RecyclerAdapter_orders(Context context, List<contact_myOrders.orders> contactslist){
        this.contactslist=contactslist;
        this.context=context;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            holder.code.setText(contactslist.get(position).getInvoice_id()+"");
            holder.status.setText(contactslist.get(position).getOrder_status()+"");
            holder.date.setText(contactslist.get(position).getPayment_method());
            holder.items.setText(contactslist.get(position).getProducts().size()+"");
            holder.price.setText(contactslist.get(position).getTotal()+"$");
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(context, OrderDetails.class);
//                Bundle bundle=new Bundle();
//                bundle.putParcelableArrayList("product", (ArrayList<? extends Parcelable>) new ArrayList<>(contactslist.get(position).getProducts()));
//                i.putExtras(bundle);
//                context.startActivity(i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView code,status,date,items,price;

        public MyViewHolder(View itemView) {
            super(itemView);

            code=itemView.findViewById(R.id.code);
            status=itemView.findViewById(R.id.status);
            date=itemView.findViewById(R.id.date);
            items=itemView.findViewById(R.id.items);
            price=itemView.findViewById(R.id.price);


        }
    }



}