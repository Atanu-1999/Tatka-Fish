package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.adapter.ALl_CategoryAdapter;
import com.example.licious.adapter.OrderHistoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.AllCategoryListener;
import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.OrderHistoryResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    List<OrderHistoryResponse.Order> orderList;
    List<OrderHistoryResponse.Orderdetail> orderDetailList;
    TextView tv_date,tv_orderId,tv_status;
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView rv_oderHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_page);
        tv_date = findViewById(R.id.tv_date);
        tv_orderId = findViewById(R.id.tv_orderId);
        tv_status = findViewById(R.id.tv_status);
        rv_oderHistory = findViewById(R.id.rv_oderHistory);

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
                    assert response.body() != null;
                    orderList = response.body().getData().getOrder();
                    orderDetailList = response.body().getData().getOrderdetails();
                    progressDialog.dismiss();
                    setData(orderList);
                    orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(),orderDetailList);
                    rv_oderHistory.setAdapter(orderHistoryAdapter);
                    rv_oderHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    private void setData(List<OrderHistoryResponse.Order> orderList) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm:ss a");
//        LocalDateTime dateTime = LocalDateTime.parse(orderList.get(0).getOrderDate(), formatter);
//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy");

        tv_date.setText(orderList.get(0).getOrderDate());
        tv_orderId.setText(orderList.get(0).getOrderNo());
        tv_status.setText(orderList.get(0).getStatus());

    }
}