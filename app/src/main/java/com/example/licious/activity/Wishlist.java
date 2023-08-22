package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.WishListAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.FavoriteListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AllWishListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wishlist extends AppCompatActivity {

    LinearLayout btn_addTocart;
    RecyclerView rv_all_wishList;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token,phoneNum;
    int id;
    List<AllWishListResponse.Datum> allWishList;
    WishListAdapter wishListAdapter;
    ProgressDialog progressDialog;
    int product_id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        //loading
        progressDialog = new ProgressDialog(Wishlist.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        initi();
        getWishListData();

//        btn_addTocart = findViewById(R.id.btn_addTocart);
//        btn_addTocart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Wishlist.this, MyCart.class));
//            }
//        });


    }

    private void getWishListData() {
        progressDialog.show();
        Call<AllWishListResponse> getAllAddress = ApiService.apiHolders().getAllWishList(id,token);
        getAllAddress.enqueue(new Callback<AllWishListResponse>() {
            @Override
            public void onResponse(Call<AllWishListResponse> call, Response<AllWishListResponse> response) {
                progressDialog.dismiss();
               // Toast.makeText(Wishlist.this, "Successfully", Toast.LENGTH_SHORT).show();
                allWishList = response.body().getData();
                wishListAdapter = new WishListAdapter(getApplication(), allWishList, new FavoriteListener() {
                    @Override
                    public void onItemClickedAdd(AllWishListResponse.Datum item, int position, int type) {
                        String pD_Id = item.getProduct_id();
                        product_id = Integer.parseInt(pD_Id);
                        String prices = item.getPrice();
                        addToCart(product_id, prices);//add to cart API
                    }
                });
                rv_all_wishList.setAdapter(wishListAdapter);
                rv_all_wishList.setLayoutManager(new LinearLayoutManager(getApplication()));

            }
            @Override
            public void onFailure(Call<AllWishListResponse> call, Throwable t) {
               // Toast.makeText(Wishlist.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Wishlist.this, "failed" , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    // add to cart
    private void addToCart(int product_id, String price) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(id, product_id,price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    getWishListData();
                    Toast.makeText(Wishlist.this, "" + "Successfully Added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("product_id", product_id);
                    Intent i = new Intent(Wishlist.this, MyCart.class);
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


    private void initi() {
        rv_all_wishList = findViewById(R.id.rv_all_wishList);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWishListData();
    }
}