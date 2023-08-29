package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.example.licious.R;
import com.example.licious.api.ApiService;
import com.example.licious.response.RatingResponse;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendFeedbackActivity extends AppCompatActivity {
    int orderId;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    RatingBar ratingBar;
    EditText et_message;
    LinearLayout btn_submit;
    RelativeLayout rl_view;
    ImageView back;
    int order_id,id_his;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        ratingBar = findViewById(R.id.ratingBar);
        et_message = findViewById(R.id.et_message);
        btn_submit = findViewById(R.id.btn_submit);
        rl_view =findViewById(R.id.rl_view);
        back = findViewById(R.id.back);
        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(SendFeedbackActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        Bundle bundle =getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            orderId = bundle.getInt("order_id", 0);
            id_his= bundle.getInt("order_id", 0);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id_his);
                Intent i = new Intent(SendFeedbackActivity.this,OrderHistoryTracking.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = String.valueOf(ratingBar.getRating());
                //Toast.makeText(OrderHistoryTracking.this,count,Toast.LENGTH_SHORT).show();
                String desc = et_message.getText().toString();

                ratingAPi(orderId,count,desc);
            }
        });


    }
    private void ratingAPi(int orderId, String count, String desc) {
        progressDialog.show();
        Call<RatingResponse> category_apiCall = ApiService.apiHolders().ratingfeedback(id,orderId,count,desc,token);
        category_apiCall.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_view, "Successfully submit", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    finish();

                } else {
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_view, "failed", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                }
            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_view, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putInt("id", order_id);
        Intent i = new Intent(SendFeedbackActivity.this,OrderHistoryTracking.class);
        i.putExtras(bundle);
        startActivity(i);
    }
}