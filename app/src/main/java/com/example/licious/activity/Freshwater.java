package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.SubCategoryProductAdapter;
import com.example.licious.adapter.Sub_categoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.SubCategoriesItemListener;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.GetSubCategoryResponse;
import com.example.licious.response.RemoveWishListResponse;
import com.example.licious.response.SubCategoryItemResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freshwater extends AppCompatActivity {
    int cId;
    RecyclerView rv_sub_cat, rv_sub_cat_product;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id;
    List<GetSubCategoryResponse.Datum> subCategories;
    List<SubCategoryItemResponse.Datum> subProductItem;
    Sub_categoryAdapter sub_categoryAdapter;
    LinearLayout ll_items;
    SubCategoryProductAdapter subCategoryProductAdapter;
    TextView tv_totalItem;
    int product_id;
    String BlankId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freshwater);
        rv_sub_cat = findViewById(R.id.rv_sub_cat);
        ll_items = findViewById(R.id.ll_items);
        rv_sub_cat_product = findViewById(R.id.rv_sub_cat_product);
        tv_totalItem = findViewById(R.id.tv_totalItem);

        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            cId = bundle.getInt("cId", 0);
        }

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(Freshwater.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        // /*Device Id Get*/
        String deviceId = DeviceUtils.getDeviceId(getApplicationContext());
        Log.e("Device Id", "" + deviceId);

        ll_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                    getSubCategortItem(cId, deviceId);
                } else {
                    getSubCategortItem(cId, token);
                }
            }
        });

        Log.d("device_id", loginPref.getString("device_id", ""));
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getSubCategory(cId, deviceId);
            getSubCategortItem(cId, deviceId);
        } else {
            getSubCategory(cId, token);
            getSubCategortItem(cId, token);
        }

    }

    private void getSubCategory(int cId, String token) {
        progressDialog.show();
        Call<GetSubCategoryResponse> subCategoryData = ApiService.apiHolders().getSubCategory(2, token);
        subCategoryData.enqueue(new Callback<GetSubCategoryResponse>() {
            @Override
            public void onResponse(Call<GetSubCategoryResponse> call, Response<GetSubCategoryResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subCategories = response.body().getData();
                    sub_categoryAdapter = new Sub_categoryAdapter(getApplicationContext(), subCategories, new SubCategoriesItemListener() {
                        @Override
                        public void onItemClickedCategoriesItem(GetSubCategoryResponse.Datum item, int position, int type) {
                            int cId = item.getC_id();
                            getSubCategortItem(cId,token);
                        }
                    });
                    rv_sub_cat.setAdapter(sub_categoryAdapter);
                    // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv_sub_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                }

            }

            @Override
            public void onFailure(Call<GetSubCategoryResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSubCategortItem(int cId, String token) {
        progressDialog.show();
        Call<SubCategoryItemResponse> subCategoryDataProduct = ApiService.apiHolders().getSubCategoryProduct(2, token);
        subCategoryDataProduct.enqueue(new Callback<SubCategoryItemResponse>() {
            @Override
            public void onResponse(Call<SubCategoryItemResponse> call, Response<SubCategoryItemResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subProductItem = response.body().getData();
                    subCategoryProductAdapter = new SubCategoryProductAdapter(getApplicationContext(), subProductItem, new SubCategoriesProductListener() {
                        @Override
                        public void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type) {
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                                FragmentManager fragmentManager = getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Account account = new Account();
//                                fragmentTransaction.replace(R.id.main_container, account);
//                                fragmentTransaction.addToBackStack(null).commit();
                            } else {
                                product_id = item.getId();
                                String prices = item.getPrice();
                                addToCart(product_id, prices);//add to cart API
                            }
                        }

                        @Override
                        public void onItemClickedCategoriesProductWishList(SubCategoryItemResponse.Datum item, int position, int type) {
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                                FragmentManager fragmentManager = getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Account account = new Account();
//                                fragmentTransaction.replace(R.id.main_container, account);
//                                fragmentTransaction.addToBackStack(null).commit();


                            } else {
                                if (Objects.equals(item.getWishlist_status(), "False")) {
                                    addWishList(item.getId(), "True");
                                } else {
                                    removeWishList(item.getId(), "False");
                                }
                            }
                        }

                        @Override
                        public void onItemClickedItem(SubCategoryItemResponse.Datum item, int position, int type) {
                            int id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("products_id", id);
                            Intent i = new Intent(Freshwater.this, ProductDetails.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    rv_sub_cat_product.setAdapter(subCategoryProductAdapter);
                    rv_sub_cat_product.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }

            }

            @Override
            public void onFailure(Call<SubCategoryItemResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //WISHlIST
    private void addWishList(Integer prod_id, String status) {
        progressDialog.show();
        Call<AddWishListResponse> addAddress = ApiService.apiHolders().add_wishList(id, prod_id, status, token);
        addAddress.enqueue(new Callback<AddWishListResponse>() {
            @Override
            public void onResponse(Call<AddWishListResponse> call, Response<AddWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    getSubCategortItem(cId,token);
                }
            }

            @Override
            public void onFailure(Call<AddWishListResponse> call, Throwable t) {
                Toast.makeText(Freshwater.this, "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeWishList(Integer prod_id, String status) {
        progressDialog.show();
        Call<RemoveWishListResponse> addAddress = ApiService.apiHolders().remove_wishList(id, prod_id, status, token);
        addAddress.enqueue(new Callback<RemoveWishListResponse>() {
            @Override
            public void onResponse(Call<RemoveWishListResponse> call, Response<RemoveWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    // Toast.makeText(getContext(), "WishList Remove Successfully", Toast.LENGTH_SHORT).show();
                    getSubCategortItem(cId,token);
                }
            }

            @Override
            public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {
                Toast.makeText(Freshwater.this, "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // add to cart
    private void addToCart(int product_id, String price) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(id, product_id, price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Freshwater.this, "" + "Successfully Added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("product_id", product_id);
                    Intent i = new Intent(Freshwater.this, MyCart.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}