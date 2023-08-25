package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.licious.R;
import com.example.licious.adapter.OrderHistoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.response.OrderHistoryDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryTracking extends AppCompatActivity {
    int order_id;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    List<OrderHistoryDataResponse.Datum> orderList;
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView rv_orderHistorydata;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_tracking);
        rv_orderHistorydata = findViewById(R.id.rv_orderHistorydata);
        back = findViewById(R.id.back);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(OrderHistoryTracking.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getInt("id");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHistoryTracking.this, OrdersPage.class);
                startActivity(intent);
                finish();
            }
        });

        getOrderHistory();
    }

    private void getOrderHistory() {
        progressDialog.show();
        Call<OrderHistoryDataResponse> category_apiCall = ApiService.apiHolders().getOrderHistoryData(order_id, token);
        category_apiCall.enqueue(new Callback<OrderHistoryDataResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryDataResponse> call, Response<OrderHistoryDataResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    orderList = response.body().getData();
                    progressDialog.dismiss();
                    orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(), orderList);
                    rv_orderHistorydata.setAdapter(orderHistoryAdapter);
                    rv_orderHistorydata.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryDataResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderHistoryTracking.this, OrdersPage.class);
        startActivity(intent);
        finish();
    }
}