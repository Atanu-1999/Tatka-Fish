package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.OrderHistoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.RepeatOrderListener;
import com.example.licious.response.OrderHistoryDataResponse;
import com.example.licious.response.RatingResponse;

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
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_tracking);
        rv_orderHistorydata = findViewById(R.id.rv_orderHistorydata);
        back = findViewById(R.id.back);
        dialog = new Dialog(OrderHistoryTracking.this);

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
                    orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(), orderList, new RepeatOrderListener() {
                        @Override
                        public void onItemClickedRepeatOrder(OrderHistoryDataResponse.Datum item, int position, int type) {

                        }

                        @Override
                        public void onItemClickedFeedback(OrderHistoryDataResponse.Datum item, int position, int type) {
                            rating(item);
                        }
                    });
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

    private void rating(OrderHistoryDataResponse.Datum item) {
        dialog.setContentView(R.layout.ratingbar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        EditText et_message = dialog.findViewById(R.id.et_message);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        TextView submit = dialog.findViewById(R.id.submit);
        TextView cancel_text = dialog.findViewById(R.id.cancel_text);


        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String count = String.valueOf(ratingBar.getRating());
                //Toast.makeText(OrderHistoryTracking.this,count,Toast.LENGTH_SHORT).show();
                String desc = et_message.getText().toString();
                String orderId = String.valueOf(item.getOrderId());
                ratingAPi(orderId,count,desc);

                dialog.dismiss();
            }
        });

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Toast.makeText(FireBaseActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void ratingAPi(String orderId, String count, String desc) {
        progressDialog.show();
        Call<RatingResponse> category_apiCall = ApiService.apiHolders().ratingfeedback(id,orderId,count,desc,token);
        category_apiCall.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
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