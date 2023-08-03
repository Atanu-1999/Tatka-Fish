package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;

public class MyCart extends AppCompatActivity {

    TextView txt_change,btn_continue;
    CardView btn_proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        btn_continue = findViewById(R.id.btn_continue);
        btn_proceed = findViewById(R.id.btn_proceed);
        txt_change = findViewById(R.id.txt_change);
        txt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, Address.class));
            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyCart.this, "hi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyCart.this, CheckoutPage.class));
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, SearchActivity.class));
            }
        });
    }
}