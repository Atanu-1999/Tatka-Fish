package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.CheckOutAdapter;
import com.example.licious.adapter.CouponAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.CartItemDeleteResponse;
import com.example.licious.response.CouponsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply_Coupon extends AppCompatActivity {

    TextView txt_view, txt_hide;
    LinearLayout hide_layout;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id;
    List<CouponsResponse.Datum> couponsResponse;
    CouponAdapter couponAdapter;
    RecyclerView rv_coupons;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_coupon);
        rv_coupons= findViewById(R.id.rv_coupons);

        //loading
        progressDialog = new ProgressDialog(Apply_Coupon.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

//        hide_layout = findViewById(R.id.hide_layout);
//        txt_view = findViewById(R.id.txt_view);
//        txt_hide = findViewById(R.id.txt_hide);

//        txt_hide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hide_layout.setVisibility(View.GONE);
//                txt_view.setVisibility(View.VISIBLE);
//            }
//        });
//        txt_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hide_layout.setVisibility(View.VISIBLE);
//                txt_view.setVisibility(View.GONE);
//            }
//        });
        getCoupons();

    }

    private void getCoupons() {
        progressDialog.show();
        Call<CouponsResponse> coupons = ApiService.apiHolders().getCoupons(token);
        coupons.enqueue(new Callback<CouponsResponse>() {
            @Override
            public void onResponse(Call<CouponsResponse> call, Response<CouponsResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    couponsResponse = response.body().getData();
                    couponAdapter = new CouponAdapter(getApplicationContext(), couponsResponse);
                    rv_coupons.setAdapter(couponAdapter);
                    rv_coupons.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    //Toast.makeText(Apply_Coupon.this, "Apply Coupon Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CouponsResponse> call, Throwable t) {
                //Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}