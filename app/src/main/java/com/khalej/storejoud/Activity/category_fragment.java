package com.khalej.storejoud.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.khalej.storejoud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_category;
import com.khalej.storejoud.model.contact_products;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class category_fragment extends Fragment {
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewCategory;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    RelativeLayout order;
    int x = 0;
    int y = 0;
    ImageView notification;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;
    ProgressBar progressBar;
    private RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    List<contact_category.catrgory> contactslist;
    contact_category contact_category;
    EditText search_text;
    ImageView search;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_category, container, false);
       // id = getArguments().getInt("id");

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        notification=view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),notifications.class));
            }
        });
        progressBar=view.findViewById(R.id.progressBar_subject);
        recyclerviewCategory = (RecyclerView) view.findViewById(R.id.recyclerviewCategory);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        4, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewCategory.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewCategory.setHasFixedSize(true);
        fetchInfo_categorys();
        search=view.findViewById(R.id.search);
        search_text=view.findViewById(R.id.search_text);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search_fragment nextFrag= new Search_fragment();
                Bundle bundle=new Bundle();
                bundle.putString("searchText",search_text.getText().toString());
                nextFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
    public void fetchInfo_categorys() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_category> call = apiinterface.getcontacts_category(sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_category>() {
            @Override
            public void onResponse(Call<contact_category> call, Response<contact_category> response) {
                progressBar.setVisibility(View.GONE);
                contact_category=response.body();
                try {
                    contactslist=contact_category.getPayload();
                    if (contactslist.size()!=0||!(contactslist.isEmpty())) {
                        recyclerAdapter_first_annonce = new RecyclerAdapter_first_annonce(getActivity(), contactslist);
                        recyclerviewCategory.setAdapter(recyclerAdapter_first_annonce);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_category> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
