package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.WishListAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.response.AllWishListResponse;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
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
        Call<AllWishListResponse> getAllAddress = ApiService.apiHolders().getAllWishList(id,token);
        getAllAddress.enqueue(new Callback<AllWishListResponse>() {
            @Override
            public void onResponse(Call<AllWishListResponse> call, Response<AllWishListResponse> response) {
                Toast.makeText(Wishlist.this, "Successfully", Toast.LENGTH_SHORT).show();
                allWishList = response.body().getData();
                wishListAdapter = new WishListAdapter(getApplication(),allWishList);
                rv_all_wishList.setAdapter(wishListAdapter);
                rv_all_wishList.setLayoutManager(new LinearLayoutManager(getApplication()));

            }
            @Override
            public void onFailure(Call<AllWishListResponse> call, Throwable t) {
                Toast.makeText(Wishlist.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
}