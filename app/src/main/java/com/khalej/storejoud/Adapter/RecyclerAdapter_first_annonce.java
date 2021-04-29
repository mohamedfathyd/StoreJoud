package com.khalej.storejoud.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.khalej.storejoud.Activity.Product_fragment;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.apiinterface_home;
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


public class RecyclerAdapter_first_annonce extends RecyclerView.Adapter<RecyclerAdapter_first_annonce.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_category.catrgory> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;

    public RecyclerAdapter_first_annonce(Context context, List<contact_category.catrgory> contactslist){
        this.contactslist=contactslist;
        this.context=context;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_view_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            holder.name.setText(contactslist.get(position).getName_by_lang());

            Glide.with(context).load("https://storejoud.com/"+
                    contactslist.get(position).getIcon()).error(R.drawable.logocolor).into(holder.image);
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_fragment subCategoryFrag= new Product_fragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle bundle = new Bundle();
                bundle.putString("name",contactslist.get(position).getId());

                subCategoryFrag.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, subCategoryFrag)
                        .addToBackStack(null).commit();
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.photo);
            name=itemView.findViewById(R.id.name);

        }
    }



}