package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.adapter.NotificationAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.NotificationListener;
import com.example.licious.response.NotificationListResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FireBaseActivity extends AppCompatActivity {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    RecyclerView rv_notificationList;
    ImageView back;
    TextView txt_noData;
    List<NotificationListResponse.Datum> notificationResponse;
    NotificationAdapter notificationAdapter;
    private BottomSheetDialog bottomSheetDialog;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);

        rv_notificationList = findViewById(R.id.rv_notificationList);
        back = findViewById(R.id.back);
        txt_noData = findViewById(R.id.txt_noData);
        dialog = new Dialog(FireBaseActivity.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(FireBaseActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        getNotificationList();
    }

    private void getNotificationList() {
        progressDialog.show();
        Call<NotificationListResponse> category_apiCall = ApiService.apiHolders().getNotification(id, token);
        category_apiCall.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    notificationResponse = response.body().getData();
                    progressDialog.dismiss();
                    notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationResponse, new NotificationListener() {
                        @Override
                        public void onItemClicked(NotificationListResponse.Datum item, int position, int type) {
                            popUp(item);
                        }
                    });
                    rv_notificationList.setAdapter(notificationAdapter);
                    rv_notificationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    progressDialog.dismiss();
                    txt_noData.setVisibility(View.VISIBLE);
                    rv_notificationList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                progressDialog.dismiss();
                txt_noData.setVisibility(View.VISIBLE);
                rv_notificationList.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void popUp(NotificationListResponse.Datum item) {

//        bottomSheetDialog = new BottomSheetDialog(FireBaseActivity.this, R.style.BottomSheetTheme);
//        View view1 = LayoutInflater.from(FireBaseActivity.this).inflate(R.layout.pop_disc,
//                (LinearLayout) findViewById(R.id.container));

        dialog.setContentView(R.layout.pop_disc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        TextView tv_descrp = dialog.findViewById(R.id.tv_descrp);
        TextView okay_text = dialog.findViewById(R.id.okay_text);
        TextView cancel_text = dialog.findViewById(R.id.cancel_text);

        tv_descrp.setText(item.getNotiDesc());


        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               // Toast.makeText(FireBaseActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
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
}