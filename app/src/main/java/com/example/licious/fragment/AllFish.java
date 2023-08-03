package com.example.licious.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.licious.R;
import com.example.licious.activity.ProductDetails;

public class AllFish extends Fragment {

    LinearLayout all_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View all = inflater.inflate(R.layout.fragment_all_fish, container, false);

        all_btn = all.findViewById(R.id.all_btn);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProductDetails.class));
            }
        });


        return all;
    }
}