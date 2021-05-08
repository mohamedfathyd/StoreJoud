package com.khalej.storejoud.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_cart;
import com.khalej.storejoud.model.contact_reviews;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_review extends RecyclerView.Adapter<RecyclerAdapter_review.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_reviews.reviews> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;

    public RecyclerAdapter_review(Context context, List<contact_reviews.reviews> contactslist){
        this.contactslist=contactslist;
        this.context=context;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            holder.name.setText(contactslist.get(position).getName());
            holder.review.setText(contactslist.get(position).getReview()+"");
            holder.date.setText(contactslist.get(position).getCreated_at());
            holder.rate.setRating((float) contactslist.get(position).getRate());
            Glide.with(context).load("https://storejoud.com/"+
                    contactslist.get(position).getCustomer().getLogo_url()).error(R.drawable.logocolor).into(holder.image);


        }
        catch (Exception e){}


    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name,review,date;
        RatingBar rate;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.photo);
            name=itemView.findViewById(R.id.name);
            review=itemView.findViewById(R.id.desc);
            date=itemView.findViewById(R.id.date);
            rate=itemView.findViewById(R.id.rate);


        }
    }



}