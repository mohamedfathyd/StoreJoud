package com.khalej.storejoud.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.storejoud.Activity.CategoryDetails;
import com.khalej.storejoud.Activity.Favourit_fragment;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_products;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_first_products_inside_f extends RecyclerView.Adapter<RecyclerAdapter_first_products_inside_f.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_products.product> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;
    Favourit_fragment favourit_fragment;
    public RecyclerAdapter_first_products_inside_f(Context context, List<contact_products.product> contactslist, Favourit_fragment favourit_fragment){
        this.contactslist=contactslist;
        this.context=context;
        this.favourit_fragment = favourit_fragment;
      }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_page_list_f,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){
               holder.name.setText(contactslist.get(position).getAr_name());

            }else{
                holder.name.setText(contactslist.get(position).getEn_name());

            }
            holder.price.setText(contactslist.get(position).getPrice_after_discount()+"$");
            holder.oldPrice.setText(contactslist.get(position).getPrice()+"$");
            double discount=100-((Double.parseDouble(contactslist.get(position).getPrice_after_discount())
                    /Double.parseDouble(contactslist.get(position).getPrice()))*100);
            holder.offer.setText(discount+"% off");
            if(contactslist.get(position).getAverage_rate().equals("0"))holder.rate.setVisibility(View.VISIBLE);
            else {
                holder.rate.setVisibility(View.VISIBLE);
                holder.rate.setRating(Float.parseFloat(contactslist.get(position).getAverage_rate()));
            }
            holder.offerL.setVisibility(View.VISIBLE);
            Glide.with(context).load("https://storejoud.com/"+
                    contactslist.get(position).getMedia_links().get(0)).error(R.drawable.logo).into(holder.image);
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryDetails.class);
                intent.putExtra("id",contactslist.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.favourit_icon.setOnClickListener(new View.OnClickListener() {
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

        ImageView image,favourit_icon;
        TextView name,price,offer,oldPrice;
        RatingBar rate;
        LinearLayout offerL;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.photo);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            rate=itemView.findViewById(R.id.rate);
            offer=itemView.findViewById(R.id.offer);
            oldPrice=itemView.findViewById(R.id.old_price);
            offerL=itemView.findViewById(R.id.offerL);
            favourit_icon=itemView.findViewById(R.id.favourit_icon);

        }
    }

    public void fetchRemove(String id){
        progressDialog = ProgressDialog.show(context, "جاري المسح من المفضلة", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<ResponseBody> call = apiinterface.delete_favourit(headers,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                favourit_fragment.fetchInfo_productsById();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}