package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.api.ApiService;
import com.example.licious.response.Pages_Response;
import com.example.licious.response.SendOtp_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pages extends AppCompatActivity {

    ImageView back;
    TextView txt_heading_name,txt_title;
    String pageId,token;

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*pages_response(token,pageId);*/
    }

    /*private void pages_response(String token, String pageId) {
        Call<Pages_Response> pages_api = ApiService.apiHolders().pages(pageId, token);
        pages_api.enqueue(new Callback<Pages_Response>() {
            @Override
            public void onResponse(Call<Pages_Response> call, Response<Pages_Response> response) {
                if (response.isSuccessful()){
                   *//* txt_heading_name.setText(String.valueOf(response.body().getData().get(0).getTitle()));
                    txt_title.setText(String.valueOf(response.body().getData().get(0).getDescription()));*//*
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Pages_Response> call, Throwable t) {

            }
        });
    }*/
}