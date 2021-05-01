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
import com.khalej.storejoud.Activity.Favourit_fragment;
import com.khalej.storejoud.Activity.Product_fragment;
import com.khalej.storejoud.Activity.basket_fragment;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_cart;
import com.khalej.storejoud.model.contact_category;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    basket_fragment bask_fragment;
    public RecyclerAdapter_cart(Context context, List<contact_cart.cart> contactslist,basket_fragment bask_fragment){
        this.contactslist=contactslist;
        this.context=context;
        this.bask_fragment=bask_fragment;
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
           if(contactslist.get(position).getQuantity()>1){
               fetchUpdate(contactslist.get(position).getId(),"decrease_quantity");
           }
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchUpdate(contactslist.get(position).getId(),"increase_quantity");
            }
        });
        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchRemove(contactslist.get(position).getId());
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image,minus,plus,delete_icon;
        TextView name,price,count;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            count=itemView.findViewById(R.id.count);
            minus=itemView.findViewById(R.id.minus);
            plus=itemView.findViewById(R.id.plus);
            delete_icon=itemView.findViewById(R.id.delete_icon);

        }
    }

    public void fetchUpdate(String id,String qunt){
        progressDialog = ProgressDialog.show(context, "جاري تعديل كمية الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<ResponseBody> call = apiinterface.updatecart(headers,id,qunt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                bask_fragment.fetchInfo_products();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void fetchRemove(String id){
        progressDialog = ProgressDialog.show(context, "جاري مسح الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<ResponseBody> call = apiinterface.delete_cart(headers,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                bask_fragment.fetchInfo_products();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}