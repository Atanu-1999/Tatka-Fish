package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.licious.R;
import com.example.licious.adapter.ALl_CategoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.AllCategoryListener;
import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.OrderHistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPage extends AppCompatActivity {

    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_page);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(OrdersPage.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        getOrderHistory();
    }

    private void getOrderHistory() {
        progressDialog.show();
        Call<OrderHistoryResponse> category_apiCall = ApiService.apiHolders().getOrderHistory(id,token);
        category_apiCall.enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                }
                else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}