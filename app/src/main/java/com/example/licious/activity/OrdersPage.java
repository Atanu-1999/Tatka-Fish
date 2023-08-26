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
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.adapter.OrderHistoryAdapter;
import com.example.licious.adapter.OrderHistoryDataAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.OrderHistoryListener;
import com.example.licious.response.HistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPage extends AppCompatActivity {

    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    List<HistoryResponse.Datum> historyResponse;
    TextView tv_date, tv_orderId, tv_status,txt_noData;
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView rv_oderHistory;
    OrderHistoryDataAdapter orderHistoryDataAdapter;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_page);
        tv_date = findViewById(R.id.tv_date);
        tv_orderId = findViewById(R.id.tv_orderId);
        tv_status = findViewById(R.id.tv_status);
        rv_oderHistory = findViewById(R.id.rv_oderHistory);
        back = findViewById(R.id.back);
        txt_noData = findViewById(R.id.txt_noData);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(OrdersPage.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(OrdersPage.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        getOrderDatahistory();
    }

    private void getOrderDatahistory() {
        progressDialog.show();
        Call<HistoryResponse> category_apiCall = ApiService.apiHolders().getOrderHistory(id, token);
        category_apiCall.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    historyResponse = response.body().getData();
                    progressDialog.dismiss();
                    orderHistoryDataAdapter = new OrderHistoryDataAdapter(getApplicationContext(), historyResponse, new OrderHistoryListener() {
                        @Override
                        public void onItemClickedOrderHistory(HistoryResponse.Datum item, int position, int type) {
                            Bundle bundle = new Bundle();
                            int id = item.getId();
                            bundle.putInt("id", id);
                            Intent intent = new Intent(OrdersPage.this, OrderHistoryTracking.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    rv_oderHistory.setAdapter(orderHistoryDataAdapter);
                    rv_oderHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                txt_noData.setVisibility(View.VISIBLE);
                rv_oderHistory.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(OrdersPage.this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}