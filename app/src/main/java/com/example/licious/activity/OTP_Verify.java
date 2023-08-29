package com.example.licious.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.api.ApiService;
import com.example.licious.response.Otp_verify_Response;
import com.example.licious.response.RepeatResponse;
import com.example.licious.response.SendOtp_Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_Verify extends AppCompatActivity {
    TextView btn_continue,count_time,btn_resend,YourOtp;
    LinearLayout timer_layout;
    String timeStr = "30",otp,OTP,phone,device_id;
    EditText otp1,otp2,otp3,otp4,otp5;
    RelativeLayout OTP_layout;
    SharedPreferences loginPref;
    String fb_token;
    TextView forgot_pass;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        OTP = String.valueOf(getIntent().getExtras().get("OTP"));
        /*Initialize*/
        YourOtp = findViewById(R.id.YourOtp);
        OTP_layout = findViewById(R.id.OTP_layout);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        timer_layout = findViewById(R.id.timer_layout);
        btn_resend = findViewById(R.id.btn_resend);
        count_time = findViewById(R.id.count_time);
        btn_continue = findViewById(R.id.btn_continue);
        forgot_pass =findViewById(R.id.forgot_pass);
        //sharedPref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);

//        //loading
//        progressDialog = new ProgressDialog(OTP_Verify.this);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Loading...");


        // Check if notification permission is granted
        if (!isNotificationPermissionGranted()) {
            // Request notification permission
            requestNotificationPermission();
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        fb_token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.next, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(OTP_Verify.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(timeStr) * 1000;
                new CountDownTimer(time, 1000) {
                    public void onTick(long millisUntilFinished) {
                        btn_resend.setVisibility(View.GONE);
                        timer_layout.setVisibility(View.VISIBLE);
                        long seconds = millisUntilFinished / 1000;
                        String SetTime = String.valueOf(seconds);
                        count_time.setText(SetTime);
                        ResendOTP(phone);
                    }
                    public void onFinish() {
                        btn_resend.setVisibility(View.VISIBLE);
                        forgot_pass.setVisibility(View.VISIBLE);
                        timer_layout.setVisibility(View.GONE);
                    }
                }.start();
                Snackbar errorBar;
                errorBar = Snackbar.make(OTP_layout , "Please Enter Valid Number!", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
        otp_timer();
        otp_move();
        YourOtp.setText("Your OTP Is " + OTP);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               otp_verify();
            }
        });
    }

    private boolean isNotificationPermissionGranted() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        return notificationManager.areNotificationsEnabled();
    }

    private void requestNotificationPermission() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            // Check if the user granted notification permission
            if (isNotificationPermissionGranted()) {
                // Permission granted, proceed with your app logic
            } else {
                // Permission not granted, handle accordingly
            }
        }
    }

    private void otp_move() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp1.getText().toString().length() == 1) {
                    otp1.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                } else {
                    otp1.clearFocus();
                }
            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp2.getText().toString().length() == 1) {
                    otp2.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                } else {
                    otp2.clearFocus();
                    otp1.requestFocus();
                    otp1.setCursorVisible(true);
                }
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp3.getText().toString().length() == 1) {
                    otp3.clearFocus();
                    otp4.requestFocus();
                    otp4.setCursorVisible(true);
                } else {
                    otp3.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                }
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp4.getText().toString().length() == 1) {
                    otp4.clearFocus();
                    otp5.requestFocus();
                    otp5.setCursorVisible(true);
                } else {
                    otp4.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                }
            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp5.getText().toString().length() == 1) {
                    otp5.requestFocus();
                    otp5.setCursorVisible(true);
                    btn_continue.setVisibility(View.VISIBLE);
                } else {
                    otp5.clearFocus();
                    otp4.requestFocus();
                    otp4.setCursorVisible(true);
                    btn_continue.setVisibility(View.GONE);
                }
            }
        });
    }
    private void otp_timer() {
        int time = Integer.parseInt(timeStr) * 1000;
        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String SetTime = String.valueOf(seconds);
                count_time.setText(SetTime);
            }
            public void onFinish() {
                btn_resend.setVisibility(View.VISIBLE);
                timer_layout.setVisibility(View.GONE);
            }
        }.start();
    }
    private void otp_verify() {
        phone = String.valueOf(getIntent().getExtras().get("NUMBER"));
        device_id = String.valueOf(getIntent().getExtras().get("DEVICE_ID"));
        otp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString()+otp5.getText().toString();
        Call<Otp_verify_Response> otp_verify = ApiService.apiHolders().otp_verify(phone,device_id,otp,fb_token);
        otp_verify.enqueue(new Callback<Otp_verify_Response>() {
            @Override
            public void onResponse(Call<Otp_verify_Response> call, Response<Otp_verify_Response> response) {
               if (response.body().getVerify()==true){
                   //Toast.makeText(OTP_Verify.this, "Success"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   Snackbar errorBar;
                   errorBar = Snackbar.make(OTP_layout , "Successfully Login", Snackbar.LENGTH_LONG);
                   errorBar.setTextColor(getResources().getColor(R.color.white));
                   errorBar.setActionTextColor(getResources().getColor(R.color.white));
                   errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                   errorBar.show();
                   Intent i = new Intent(OTP_Verify.this,MainActivity.class);
                   SharedPreferences.Editor editor = loginPref.edit();
                   String deviceId = response.body().getData().get(0).getDeviceId();
                   String phoneNumber = response.body().getData().get(0).getPhone();
                   int userId = response.body().getData().get(0).getId();
                   editor.putString("device_id",deviceId);
                   editor.putString("phone",phoneNumber);
                   editor.putInt("userId",userId);
                   editor.commit();
                   startActivity(i);
               }
               else {
                  // Toast.makeText(OTP_Verify.this, "Fail"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   Snackbar errorBar;
                   errorBar = Snackbar.make(OTP_layout , "Fail", Snackbar.LENGTH_LONG);
                   errorBar.setTextColor(getResources().getColor(R.color.white));
                   errorBar.setActionTextColor(getResources().getColor(R.color.white));
                   errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                   errorBar.show();

               }
            }
            @Override
            public void onFailure(Call<Otp_verify_Response> call, Throwable t) {
                //Toast.makeText(OTP_Verify.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(OTP_Verify.this, R.string.otp_verify, Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(OTP_layout ,  R.string.otp_verify, Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }

    private void ResendOTP(String phone) {
        Call<RepeatResponse> login_apiCall = ApiService.apiHolders().re_sent_otp(phone, device_id);
        login_apiCall.enqueue(new Callback<RepeatResponse>() {
            @Override
            public void onResponse(Call<RepeatResponse> call, Response<RepeatResponse> response) {
                if (response.isSuccessful()) {
                    String response1 = response.body().toString();
                   // Toast.makeText(OTP_Verify.this, "Success"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(OTP_layout , "Successfully Login", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    Intent i = new Intent(OTP_Verify.this,MainActivity.class);
                    SharedPreferences.Editor editor = loginPref.edit();
                    String deviceId = response.body().getData().get(0).getDeviceId();
                    String phoneNumber = response.body().getData().get(0).getPhone();
                    int userId = response.body().getData().get(0).getId();
                    editor.putString("device_id",deviceId);
                    editor.putString("phone",phoneNumber);
                    editor.putInt("userId",userId);
                    editor.commit();
                    startActivity(i);
                } else {
                    //Toast.makeText(OTP_Verify.this, "Fail"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(OTP_layout , "Fail", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                }
            }

            @Override
            public void onFailure(Call<RepeatResponse> call, Throwable t) {
                //Toast.makeText(OTP_Verify.this, R.string.otp_verify, Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(OTP_layout ,  R.string.otp_verify, Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }
}