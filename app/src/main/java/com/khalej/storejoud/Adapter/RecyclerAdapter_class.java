package com.khalej.storejoud.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_single_product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_class extends RecyclerView.Adapter<RecyclerAdapter_class.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_single_product.classifications> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;

    public RecyclerAdapter_class(Context context, List<contact_single_product.classifications> contactslist){
        this.contactslist=contactslist;
        this.context=context;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.classficiation_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            if(sharedpref.getString("language","").trim().equals("ar")){
                holder.desc.setText(contactslist.get(position).getAr_name());
                holder.name.setText(contactslist.get(position).getCategorizations().get(0).getAr_name());
            }
            else{
                holder.desc.setText(contactslist.get(position).getEn_name());
                holder.name.setText(contactslist.get(position).getCategorizations().get(0).getEn_name());
            }


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

        TextView name,desc;

        public MyViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.details);

        }
    }



}