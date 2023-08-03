package com.example.licious.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.licious.R;
import com.google.android.material.snackbar.Snackbar;

public class Location_Set extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_set);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(),R.color.red));
       /* *//*Initialize*//*
        txt_phone = findViewById(R.id.txt_phone);
        myTextView = findViewById(R.id.myTextView);
        txt_add = findViewById(R.id.txt_add);
        txt_Skip = findViewById(R.id.txt_Skip);
        login_layout = findViewById(R.id.login_layout);
        myTextView = findViewById(R.id.myTextView);
        *//*Onclick Function*//*
       *//* txt_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Location_Set.this, MainActivity.class));
            }
        });
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(Location_Set.this,R.style.BottomSheetTheme);
                View view1 = LayoutInflater.from(Location_Set.this).inflate(R.layout.address_layout,
                        (LinearLayout) findViewById(R.id.container));
                TextView btn_save = (TextView)view1.findViewById(R.id.btn_save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Location_Set.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
            }
        });*//*
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_phone.getText().toString().trim().isEmpty()){
                    Snackbar errorBar;
                    errorBar = Snackbar.make(login_layout, "Please Enter Valid Number!", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    txt_phone.requestFocus();
                }
                else {
                    startActivity(new Intent(Location_Set.this, OTP_Verify.class));
                }
            }
        });*/

    }
}