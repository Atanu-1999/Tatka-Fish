package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.licious.R;

public class Apply_Coupon extends AppCompatActivity {

    TextView txt_view,txt_hide;
    LinearLayout hide_layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_coupon);
        hide_layout = findViewById(R.id.hide_layout);
        txt_view = findViewById(R.id.txt_view);
        txt_hide = findViewById(R.id.txt_hide);

        txt_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_layout.setVisibility(View.GONE);
                txt_view.setVisibility(View.VISIBLE);
            }
        });
        txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_layout.setVisibility(View.VISIBLE);
                txt_view.setVisibility(View.GONE);
            }
        });

    }
}