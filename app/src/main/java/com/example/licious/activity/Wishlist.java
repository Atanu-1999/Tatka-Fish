package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.licious.R;

public class Wishlist extends AppCompatActivity {

    LinearLayout btn_addTocart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        btn_addTocart = findViewById(R.id.btn_addTocart);
        btn_addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Wishlist.this, MyCart.class));
            }
        });
    }
}