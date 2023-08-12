package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.licious.R;
import com.example.licious.adapter.Best_Seller_Adapter;
import com.example.licious.adapter.Sub_categoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.SubCategoriesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freshwater extends AppCompatActivity {
    int Sub_Cat_ID;
    RecyclerView rv_sub_cat;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id;
    List<SubCategoriesResponse.Datum> subCategories;
    Sub_categoryAdapter sub_categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freshwater);
        rv_sub_cat = findViewById(R.id.rv_sub_cat);

        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            Sub_Cat_ID = bundle.getInt("sub_cat_id",0);
        }

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(Freshwater.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        getSubCategory();
    }

    private void getSubCategory() {
            progressDialog.show();
            Call<SubCategoriesResponse> subCategoryData = ApiService.apiHolders().getSubCategory(2,token);
            subCategoryData.enqueue(new Callback<SubCategoriesResponse>() {
                @Override
                public void onResponse(Call<SubCategoriesResponse> call, Response<SubCategoriesResponse> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        assert response.body() != null;
                        subCategories = response.body().getData();
                        sub_categoryAdapter = new Sub_categoryAdapter(getApplicationContext(), subCategories);
                        rv_sub_cat.setAdapter(sub_categoryAdapter);
                        // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                        rv_sub_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    }

                }
                @Override
                public void onFailure(Call<SubCategoriesResponse> call, Throwable t) {
                    //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            });
    }
}