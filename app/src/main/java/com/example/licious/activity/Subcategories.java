package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.Category_horizental_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.AllFish;
import com.example.licious.fragment.Crab;
import com.example.licious.listener.SubCategoriesListener;
import com.example.licious.response.GetCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Subcategories extends AppCompatActivity {

    LinearLayout btn_all,btn_fresh,btn_crab,btn_sea;
    AllFish all = new AllFish();
    Crab crab = new Crab();

    ImageView product_search;
    RecyclerView rv_category_sub_s;
    List<GetCategoryResponse.Datum> category_response;
    Category_horizental_Adapter category_horizental_adapter;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        product_search = findViewById(R.id.product_search);
        btn_all = findViewById(R.id.btn_all);
        btn_fresh = findViewById(R.id.btn_fresh);
        btn_crab = findViewById(R.id.btn_crab);
        btn_sea = findViewById(R.id.btn_sea);
        rv_category_sub_s= findViewById(R.id.rv_category_sub_s);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
            }
        });
        btn_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subcategories.this,Freshwater.class));
            }
        });
        btn_sea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subcategories.this,Seawater.class));
            }
        });
        btn_crab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,crab).commit();
            }
        });
        product_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subcategories.this,SearchActivity.class));
            }
        });
        getCategory(2);
    }

    private void getCategory(Integer cId) {
        Call<GetCategoryResponse> addAddress = ApiService.apiHolders().getCategory(2, token);
        addAddress.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
//                Toast.makeText(getContext(), "Address Added Successfully", Toast.LENGTH_SHORT).show();
                category_response = response.body().getData();
                category_horizental_adapter = new Category_horizental_Adapter(getApplication(), category_response, new SubCategoriesListener() {
                    @Override
                    public void onItemClickedCategories(GetCategoryResponse.Datum item, int position, int type) {

                    }
                });
                GridLayoutManager layoutManager = new GridLayoutManager(getApplication(), 3);
                rv_category_sub_s.setLayoutManager(layoutManager);
                rv_category_sub_s.setAdapter(category_horizental_adapter);

            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(Subcategories.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}