package com.khalej.storejoud.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.storejoud.Activity.EmailUpdate;
import com.khalej.storejoud.Activity.payType;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_address;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_address_order extends RecyclerView.Adapter<RecyclerAdapter_address_order.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_address.address> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;
    String promoCode;
    public RecyclerAdapter_address_order(Context context, List<contact_address.address> contactslist,String promoCode){
        this.contactslist=contactslist;
        this.context=context;
        this.promoCode=promoCode;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        try {
            holder.name.setText(contactslist.get(position).getName());
            holder.address.setText(contactslist.get(position).getStreet_name());
            holder.phone.setText(contactslist.get(position).getPhone());


        }
        catch (Exception e){}

        holder.delete_icon.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, payType.class);
                i.putExtra("address_id",contactslist.get(position).getId());
                i.putExtra("promoCode",promoCode);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });
    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView delete_icon;
        TextView name, address, phone;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            delete_icon = itemView.findViewById(R.id.delete_icon);


        }


    }

}
