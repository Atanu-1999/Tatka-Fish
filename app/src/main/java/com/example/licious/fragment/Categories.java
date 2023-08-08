package com.example.licious.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.licious.R;
import com.example.licious.activity.Subcategories;
import com.example.licious.adapter.Category_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.response.Master_Category_Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categories extends Fragment {
    LinearLayout type_one;
    ImageView open,close;
    HorizontalScrollView layout_view;
    RecyclerView rv_category;
    String token;

    Category_Adapter category_adapter;
    List<Master_Category_Response.Datum> master_category_responses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View category = inflater.inflate(R.layout.fragment_categories, container, false);

        type_one =category.findViewById(R.id.type_one);
        open = category.findViewById(R.id.open);
        close = category.findViewById(R.id.close);
        layout_view = category.findViewById(R.id.layout_view);
        rv_category = category.findViewById(R.id.rv_category);





        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_view.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);
                open.setVisibility(View.GONE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_view.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                open.setVisibility(View.VISIBLE);
            }
        });
        type_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Subcategories.class));
            }
        });

        All_Categories("abcdefgh12345");

        return category;
    }

    private void All_Categories(String token) {
        Call<Master_Category_Response> login_apiCall = ApiService.apiHolders().category(token);
        login_apiCall.enqueue(new Callback<Master_Category_Response>() {
            @Override
            public void onResponse(Call<Master_Category_Response> call, Response<Master_Category_Response> response) {
                if (response.isSuccessful()){
                    String response1 = response.body().toString();
                    master_category_responses = response.body().getData();
                    category_adapter = new Category_Adapter(getContext(),master_category_responses);
                    rv_category.setAdapter(category_adapter);
                    rv_category.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Master_Category_Response> call, Throwable t) {

            }
        });
    }
}