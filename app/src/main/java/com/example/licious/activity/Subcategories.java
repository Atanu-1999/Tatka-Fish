package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.licious.R;
import com.example.licious.fragment.AllFish;
import com.example.licious.fragment.Crab;
import com.google.android.material.tabs.TabLayout;

public class Subcategories extends AppCompatActivity {

    LinearLayout btn_all,btn_fresh,btn_crab,btn_sea;
    AllFish all = new AllFish();
    Crab crab = new Crab();

    ImageView product_search;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        product_search = findViewById(R.id.product_search);
        btn_all = findViewById(R.id.btn_all);
        btn_fresh = findViewById(R.id.btn_fresh);
        btn_crab = findViewById(R.id.btn_crab);
        btn_sea = findViewById(R.id.btn_sea);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
            }
        });
        btn_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subcategories.this,Freshwater.class));
            }
        });
        btn_sea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subcategories.this,Seawater.class));
            }
        });
        btn_crab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,crab).commit();
            }
        });
        product_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Subcategories.this,SearchActivity.class));
            }
        });
    }

}