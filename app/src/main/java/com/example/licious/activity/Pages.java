package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.adapter.AddressListAdapter;
import com.example.licious.adapter.OrderHistoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.response.AllAddressListResponse;
import com.example.licious.response.OrderHistoryDataResponse;
import com.example.licious.response.Pages_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pages extends AppCompatActivity {

    ImageView back;
    TextView txt_heading_name,txt_title;
    String pageId,token;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        SharedPreferences loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        token = loginPref.getString("device_id", "");
        pageId = getIntent().getExtras().getString("page_id");

        txt_heading_name = findViewById(R.id.txt_heading_name);
        txt_title = findViewById(R.id.txt_title);
        back = findViewById(R.id.back);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(Pages.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (pageId.equals("1")){
            getOrderHistory("1");
        }
        else if (pageId.equals("2")){
            getOrderHistory("2");
        }
        else if (pageId.equals("3"))
        {
            getOrderHistory("3");
        }
    }

    private void getOrderHistory(String page) {
        progressDialog.show();
        Call<Pages_Response> category_apiCall = ApiService.apiHolders().getPage(page, token);
        category_apiCall.enqueue(new Callback<Pages_Response>() {
            @Override
            public void onResponse(Call<Pages_Response> call, Response<Pages_Response> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    txt_heading_name.setText(response.body().getData().get(0).getTitle());
                    txt_title.setText(response.body().getData().get(0).getDescription());

                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Pages_Response> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}