package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.response.Otp_verify_Response;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_Verify extends AppCompatActivity {
    TextView btn_continue,count_time,btn_resend,YourOtp;
    LinearLayout timer_layout;
    String timeStr = "10",otp,OTP,phone,device_id;
    EditText otp1,otp2,otp3,otp4,otp5;
    RelativeLayout OTP_layout;
    SharedPreferences loginPref;

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
        //sharedPref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);

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
                    }
                    public void onFinish() {
                        btn_resend.setVisibility(View.VISIBLE);
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
        Call<Otp_verify_Response> otp_verify = ApiService.apiHolders().otp_verify(phone,device_id,otp);
        otp_verify.enqueue(new Callback<Otp_verify_Response>() {
            @Override
            public void onResponse(Call<Otp_verify_Response> call, Response<Otp_verify_Response> response) {
               if (response.body().getVerify()==true){
                   Toast.makeText(OTP_Verify.this, "Success"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                   Toast.makeText(OTP_Verify.this, "Fail"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
            @Override
            public void onFailure(Call<Otp_verify_Response> call, Throwable t) {
                //Toast.makeText(OTP_Verify.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(OTP_Verify.this, R.string.otp_verify, Toast.LENGTH_SHORT).show();
            }
        });
    }
}