package com.khalej.storejoud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.storejoud.Adapter.RecyclerAdapter_class;
import com.khalej.storejoud.Adapter.RecyclerAdapter_colors;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_product_banner;
import com.khalej.storejoud.Adapter.RecyclerAdapter_first_products;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_products;
import com.khalej.storejoud.model.contact_single_product;
import com.khalej.storejoud.model.contact_single_product;

import java.util.HashMap;
import java.util.List;

public class CategoryDetails extends AppCompatActivity {
    ImageView back;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView  recyclerviewbanner,recyclerviewColor,recyclerviewClass,recyclerViewProduct;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> contactList_annonce ;
    private contact_single_product.product product;
    private List<contact_products.product> othet_product;
    private com.khalej.storejoud.model.contact_single_product contact_single_product;
    private RecyclerAdapter_first_product_banner recyclerAdapter_annonce;
    private List<contact_single_product.colors> colorsList;
    RecyclerAdapter_colors recyclerAdapter_colors;
    RecyclerAdapter_class recyclerAdapter_class;
    private List<contact_single_product.classifications> classificationsList;
    private List<contact_products.product>productList;
    RecyclerAdapter_first_products recyclerAdapter_first_products;
    AppCompatButton appCompatButtonRegister;
    ProgressBar progressBar;
    private apiinterface_home apiinterface;
    TextView productName,price,desc,alsoLike;
    Intent intent;
    RatingBar ratingBar;
    ProgressDialog progressDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_category_details);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        intent=getIntent();
        progressBar=findViewById(R.id.progressBar_subject);
        appCompatButtonRegister=findViewById(R.id.appCompatButtonRegister);
        back=findViewById(R.id.back);
        productName=findViewById(R.id.productName);
        ratingBar=findViewById(R.id.rate);
        price=findViewById(R.id.price);
        alsoLike=findViewById(R.id.alsoLike);
        desc=findViewById(R.id.desc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerviewbanner = (RecyclerView) findViewById(R.id.recyclerviewbanner);
        layoutManager = new GridLayoutManager(CategoryDetails.this, 1);
        recyclerviewbanner.setLayoutManager(new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.HORIZONTAL, true));
        recyclerviewbanner.setHasFixedSize(true);
        recyclerviewColor = (RecyclerView) findViewById(R.id.recyclerviewColor);
        layoutManager = new GridLayoutManager(CategoryDetails.this, 4);
        recyclerviewColor.setLayoutManager(new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.HORIZONTAL, true));
        recyclerviewColor.setHasFixedSize(true);
        recyclerviewClass = (RecyclerView) findViewById(R.id.recyclerviewClass);
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recyclerviewFlash);
        layoutManager = new GridLayoutManager(CategoryDetails.this, 3);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.HORIZONTAL, true));
        recyclerViewProduct.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerviewClass.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewClass.setHasFixedSize(true);
        fetchInfo_annonce();
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchAddToCard();
            }
        });
    }
    public void fetchInfo_annonce() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_single_product> call = apiinterface.getcontacts_productDetailsById(intent.getStringExtra("id"));
        call.enqueue(new Callback<contact_single_product>() {
            @Override
            public void onResponse(Call<contact_single_product> call, Response<contact_single_product> response) {
                progressBar.setVisibility(View.GONE);
                contact_single_product=response.body();
                try {
                    product=contact_single_product.getPayload().getProduct();
                    othet_product=contact_single_product.getPayload().getOther_products();
                    if(sharedpref.getString("language","").trim().equals("ar")){
                    productName.setText(product.getAr_name());
                    desc.setText(Html.fromHtml(product.getAr_overview()));
                    }
                    else{
                        productName.setText(product.getEn_name());
                        desc.setText(Html.fromHtml(product.getEn_overview()));
                    }
                    price.setText(product.getPrice_after_discount()+"$");

                    contactList_annonce=product.getMedia_links();
                    if (contactList_annonce.size()!=0||!(contactList_annonce.isEmpty())) {
                        recyclerAdapter_annonce = new RecyclerAdapter_first_product_banner(CategoryDetails.this, contactList_annonce, recyclerviewbanner);
                        recyclerviewbanner.setAdapter(recyclerAdapter_annonce);
                    }
                    colorsList=product.getColors();
                    if (colorsList.size()!=0||!(colorsList.isEmpty())) {
                        recyclerAdapter_colors = new RecyclerAdapter_colors(CategoryDetails.this, colorsList);
                        recyclerviewColor.setAdapter(recyclerAdapter_colors);
                    }
                    classificationsList=product.getClassifications();
                    if (classificationsList.size()!=0||!(classificationsList.isEmpty())) {
                        recyclerAdapter_class = new RecyclerAdapter_class(CategoryDetails.this, classificationsList);
                        recyclerviewClass.setAdapter(recyclerAdapter_class);
                    }
                    productList=othet_product;
                    if (productList.size()!=0||!(productList.isEmpty())) {
                        recyclerAdapter_first_products = new RecyclerAdapter_first_products(CategoryDetails.this, productList);
                        recyclerViewProduct.setAdapter(recyclerAdapter_first_products);
                    }
                    else{alsoLike.setVisibility(View.GONE);
                    recyclerViewProduct.setVisibility(View.GONE);}
                    id=product.getId();
                    //ratingBar.setRating(Float.parseFloat(product.getAverage_rate()));
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<contact_single_product> call, Throwable t) {progressBar.setVisibility(View.GONE);}
        });
    }
    public void fetchAddToCard(){
        progressDialog = ProgressDialog.show(CategoryDetails.this,"جاري أضافة الطلب الى السلة","Please wait...",false,false);
        progressDialog.show();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        Call<ResponseBody> call= apiinterface.getcontacts_addtoCart(headers,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                if(response.isSuccessful()){


                    try {
                        progressDialog.dismiss();


                        Dialog dialog1;
                        dialog1 = new Dialog(CategoryDetails.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        TextView message=dialog1.findViewById(R.id.message);
                        message.setText("تم أضافة الطلب الى السلة بنجاح");
                        dialog1.show();
                    }
                    catch (Exception e){

                        progressDialog.dismiss();
                        Toast.makeText(CategoryDetails.this,"هناك خطأ في أضافة هذا المنتج الى سلة المشتريات",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(CategoryDetails.this,"هناك خطأ في أضافة هذا المنتج الى سلة المشتريات",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
    }
}
