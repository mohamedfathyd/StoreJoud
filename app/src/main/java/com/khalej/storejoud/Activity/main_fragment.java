package com.khalej.storejoud.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.TextView;


import com.khalej.storejoud.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products_inside;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_category;
import com.khalej.storejoud.model.contact_general;
import com.khalej.storejoud.model.contact_products;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class main_fragment extends Fragment {
    private apiinterface_home apiinterface;
    private RecyclerView recyclerviewFlash, recyclerviewbanner, recyclerviewCategory,recyclerviewFashion,recyclerviewFavourit;
    private RecyclerView.LayoutManager layoutManager;
    private List<contact_general.media> contactList_annonce ;
    private com.khalej.storejoud.model.contact_general contact_general;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    private RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    private RecyclerAdapter_first_products recyclerAdapter_first_products;
    private RecyclerAdapter_first_products_inside recyclerAdapter_first_products_inside;
    contact_products contact_products;
    List<contact_products.product> productList;
    List<contact_category.catrgory> contactslist;
    contact_category contact_category;
    CountDownTimer countDownTimer;
    RelativeLayout order;
    int x = 0;
    int y = 0;
   ImageView notification,favourit;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;
    ProgressBar progressBar;
    TextView moreCategory,moreFlash,moreFashion;
    EditText search_text;
    ImageView search;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
       // id = getArguments().getInt("id");
        progressBar=view.findViewById(R.id.progressBar_subject);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        notification=view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),notifications.class));
            }
        });
        favourit=view.findViewById(R.id.favourit);
        favourit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favourit_fragment nextFrag= new Favourit_fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        moreCategory=view.findViewById(R.id.moreCategory);
        moreFlash=view.findViewById(R.id.moreFlash);
        moreFashion=view.findViewById(R.id.morefashion);
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
        moreCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_fragment nextFrag= new category_fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        moreFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_fragment nextFrag= new Product_fragment();
                Bundle bundle=new Bundle();
                bundle.putString("name","flash");
                nextFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        moreFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_fragment nextFrag= new Product_fragment();
                Bundle bundle=new Bundle();
                bundle.putString("name","fashion");
                nextFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerviewbanner = (RecyclerView) view.findViewById(R.id.recyclerviewbanner);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerviewbanner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerviewbanner.setHasFixedSize(true);
        recyclerviewCategory = (RecyclerView) view.findViewById(R.id.recyclerviewCategory);
        layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerviewCategory.setHasFixedSize(true);
        recyclerviewFlash = (RecyclerView) view.findViewById(R.id.recyclerviewFlash);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerviewFlash.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerviewFlash.setHasFixedSize(true);
        recyclerviewFashion = (RecyclerView) view.findViewById(R.id.recyclerviewFashion);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerviewFashion.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerviewFashion.setHasFixedSize(true);
        recyclerviewFavourit = (RecyclerView) view.findViewById(R.id.recyclerviewfavourit);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewFavourit.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewFavourit.setHasFixedSize(true);
        fetchInfo_categorys();
        fetchInfo_annonce();
        return view;
    }
    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general> call = apiinterface.getcontacts_generalData();
        call.enqueue(new Callback<contact_general>() {
            @Override
            public void onResponse(Call<contact_general> call, Response<contact_general> response) {
                contact_general=response.body();
                try {
                    contactList_annonce=contact_general.getPayload();
                    if (contactList_annonce.size()!=0||!(contactList_annonce.isEmpty())) {
                        recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_banner(getActivity(), contactList_annonce, recyclerviewbanner);
                        recyclerviewbanner.setAdapter(recyclerAdapter_annonce);

                    }
                  } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_general> call, Throwable t) {}
        });
    }
    public void fetchInfo_categorys() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_category> call = apiinterface.getcontacts_category(sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_category>() {
            @Override
            public void onResponse(Call<contact_category> call, Response<contact_category> response) {
                progressBar.setVisibility(View.GONE);
                fetchInfo_products();
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
    public void fetchInfo_products() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_products> call = apiinterface.getcontacts_products_top(sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_products>() {
            @Override
            public void onResponse(Call<contact_products> call, Response<contact_products> response) {
                progressBar.setVisibility(View.GONE);
                contact_products=response.body();
                try {
                    productList=contact_products.getPayload();
                    if (productList.size()!=0||!(productList.isEmpty())) {
                        recyclerAdapter_first_products = new RecyclerAdapter_first_products(getActivity(), productList);
                        recyclerviewFlash.setAdapter(recyclerAdapter_first_products);
                    }
                    fetchInfo_products_rated();
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_products> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void fetchInfo_products_rated() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_products> call = apiinterface.getcontacts_products_rated(sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_products>() {
            @Override
            public void onResponse(Call<contact_products> call, Response<contact_products> response) {
                contact_products=response.body();
                try {
                    productList=contact_products.getPayload();
                    if (productList.size()!=0||!(productList.isEmpty())) {
                        recyclerAdapter_first_products = new RecyclerAdapter_first_products(getActivity(), productList);
                        recyclerviewFashion.setAdapter(recyclerAdapter_first_products);

                    }
                    fetchInfo_products_favourit();
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_products> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void fetchInfo_products_favourit() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_products> call = apiinterface.getcontacts_products_favorites(headers,sharedpref.getString("lang","ar"));
        call.enqueue(new Callback<contact_products>() {
            @Override
            public void onResponse(Call<contact_products> call, Response<contact_products> response) {
                contact_products=response.body();
                try {
                    productList=contact_products.getPayload();
                    if (productList.size()!=0||!(productList.isEmpty())) {
                        recyclerAdapter_first_products_inside = new RecyclerAdapter_first_products_inside(getActivity(), productList);
                        recyclerviewFavourit.setAdapter(recyclerAdapter_first_products_inside);

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<contact_products> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
