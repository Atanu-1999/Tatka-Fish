package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.CategoryProductAdapter;
import com.example.licious.adapter.Category_horizental_Adapter;
import com.example.licious.adapter.SubCategoryProductAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.AllFish;
import com.example.licious.fragment.Crab;
import com.example.licious.listener.SubCategoriesListener;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.Category_Response;
import com.example.licious.response.GetCategoryResponse;
import com.example.licious.response.SubCategoryItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCatergoriesActivity extends AppCompatActivity {
    LinearLayout btn_all,btn_fresh,btn_crab,btn_sea;
    AllFish all = new AllFish();
    Crab crab = new Crab();

    ImageView product_search;
    RecyclerView rv_category_sub_s,rv_sub_cat_product;
    List<GetCategoryResponse.Datum> category_response;
    List<Category_Response.Datum> category_response_product;
    Category_horizental_Adapter category_horizental_adapter;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id,cId,mcId;
    String token;
    ProgressDialog progressDialog;
    CategoryProductAdapter categoryProductAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catergories);

//        product_search = findViewById(R.id.product_search);
//        btn_all = findViewById(R.id.btn_all);
//        btn_fresh = findViewById(R.id.btn_fresh);
//        btn_crab = findViewById(R.id.btn_crab);
//        btn_sea = findViewById(R.id.btn_sea);
        rv_category_sub_s= findViewById(R.id.rv_category_sub_ss);
        rv_sub_cat_product = findViewById(R.id.rv_sub_cat_product);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            mcId = bundle.getInt("mcId");
        }

//        getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
//        btn_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
//            }
//        });
//        btn_fresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SubCatergoriesActivity.this,Freshwater.class));
//            }
//        });
//        btn_sea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  startActivity(new Intent(Subcategories.this,Seawater.class));
//            }
//        });
//        btn_crab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,crab).commit();
//            }
//        });
//        product_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  startActivity(new Intent(Subcategories.this,SearchActivity.class));
//            }
//        });
       // getCategory(cId);
        getCategory(mcId);
    }

    private void getCategory(Integer mcId) {
        progressDialog.show();
        Call<GetCategoryResponse> addAddress = ApiService.apiHolders().getCategory(mcId, token);
        addAddress.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                progressDialog.dismiss();
//                Toast.makeText(getContext(), "Address Added Successfully", Toast.LENGTH_SHORT).show();
                category_response = response.body().getData();

               // getCategorieesPoduct();

                category_horizental_adapter = new Category_horizental_Adapter(getApplication(), category_response, new SubCategoriesListener() {
                    @Override
                    public void onItemClickedCategories(GetCategoryResponse.Datum item, int position, int type) {
                        int id = item.getId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("cId",id);
                        Intent i = new Intent(SubCatergoriesActivity.this,Freshwater.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                });
//                GridLayoutManager layoutManager = new GridLayoutManager(getApplication(), 3);
//                rv_category_sub_s.setLayoutManager(layoutManager);
//                rv_category_sub_s.setAdapter(category_horizental_adapter);
                rv_category_sub_s.setAdapter(category_horizental_adapter);
                // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_category_sub_s.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
//                Toast.makeText(SubCatergoriesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Toast.makeText(SubCatergoriesActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCategorieesPoduct() {
        progressDialog.show();
        Call<Category_Response> subCategoryDataProduct = ApiService.apiHolders().getCategoryProduct(2,token);
        subCategoryDataProduct.enqueue(new Callback<Category_Response>() {
            @Override
            public void onResponse(Call<Category_Response> call, Response<Category_Response> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    category_response_product = response.body().getData();
                    categoryProductAdapter =  new CategoryProductAdapter(getApplicationContext(), category_response_product);
                    rv_sub_cat_product.setAdapter(categoryProductAdapter);
                    rv_sub_cat_product.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    subCategoryProductAdapter = new SubCategoryProductAdapter(getApplicationContext(), subProductItem, new SubCategoriesProductListener() {
//                        @Override
//                        public void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type) {
//                            product_id = item.getId();
//                            String prices = item.getPrice();
//                          //  addToCart(product_id, prices);//add to cart API
//                        }
//
//                        @Override
//                        public void onItemClickedCategoriesProductWishList(SubCategoryItemResponse.Datum item, int position, int type) {
//                           // addWishList(item.getId(), item.getStatus());
//                        }
//                    });
//                    rv_sub_cat_product.setAdapter(subCategoryProductAdapter);
//                    rv_sub_cat_product.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }

            }
            @Override
            public void onFailure(Call<Category_Response> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}