package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.adapter.FAQAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.response.FaqResponse;
import com.example.licious.response.NotificationListResponse;
import com.example.licious.response.Pages_Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQ extends AppCompatActivity {

    ImageView back;
    RecyclerView rv_faq;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    String BlankId = "";
    String deviceId;
    FAQAdapter faqAdapter;
    List<FaqResponse.Datum> faqResponse;
    TextView txt_noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        back = findViewById(R.id.back);
        rv_faq = findViewById(R.id.rv_faq);
        txt_noData = findViewById(R.id.txt_noData);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);
        deviceId = DeviceUtils.getDeviceId(FAQ.this);

        //loading
        progressDialog = new ProgressDialog(FAQ.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getFaqList(deviceId);
        }
        else {
            getFaqList(token);
        }
    }

    private void getFaqList(String deviceId) {
        progressDialog.show();
        Call<FaqResponse> category_apiCall = ApiService.apiHolders().getFaq(deviceId);
        category_apiCall.enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    faqResponse = response.body().getData();
                    faqAdapter = new FAQAdapter(getApplicationContext(),faqResponse);
                    rv_faq.setAdapter(faqAdapter);
                    rv_faq.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                progressDialog.dismiss();
                txt_noData.setVisibility(View.VISIBLE);
                rv_faq.setVisibility(View.GONE);
            }
        });
    }
}