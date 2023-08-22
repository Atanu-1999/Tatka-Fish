package com.example.licious.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.activity.SubCatergoriesActivity;
import com.example.licious.activity.Subcategories;
import com.example.licious.adapter.Category_Adapter;
import com.example.licious.adapter.Category_horizental_Adapter;
import com.example.licious.adapter.Main_screen_Category_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.SubCategoriesListener;
import com.example.licious.response.Category_Response;
import com.example.licious.response.GetCategoryResponse;
import com.example.licious.response.GetMasterCategoryResponse;
import com.example.licious.response.Master_Category_Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categories extends Fragment {
    LinearLayout type_one;
    ImageView open, close;
    HorizontalScrollView layout_view;
    RecyclerView rv_category, rv_category_sub;
    String token;

    Category_Adapter category_adapter;
    List<GetMasterCategoryResponse.Datum> master_category_responses;
    List<GetCategoryResponse.Datum> category_response;
    Category_horizental_Adapter category_horizental_adapter;

    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    ProgressDialog progressDialog;
    String BlankId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View category = inflater.inflate(R.layout.fragment_categories, container, false);

        type_one = category.findViewById(R.id.type_one);
        open = category.findViewById(R.id.open);
        close = category.findViewById(R.id.close);
        layout_view = category.findViewById(R.id.layout_view);
        rv_category = category.findViewById(R.id.rv_category);
        rv_category_sub = category.findViewById(R.id.rv_category_sub);
        String deviceId = DeviceUtils.getDeviceId(getContext());

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

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
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            All_Categories(deviceId);
        } else {
            All_Categories(token);
        }

        return category;
    }

    private void All_Categories(String token) {
        progressDialog.show();
        Call<GetMasterCategoryResponse> login_apiCall = ApiService.apiHolders().category(token);
        login_apiCall.enqueue(new Callback<GetMasterCategoryResponse>() {
            @Override
            public void onResponse(Call<GetMasterCategoryResponse> call, Response<GetMasterCategoryResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    String response1 = response.body().toString();
                    master_category_responses = response.body().getData();
                    category_adapter = new Category_Adapter(getContext(), master_category_responses, new Category_Adapter.OnItemClickListener() {
                        @Override
                        public void onItemClickCategory(GetMasterCategoryResponse.Datum item, int position, int type) {
                            Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
                            int Id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("mcId", Id);
//                            Intent intent = new Intent(getContext(), SubCatergoriesActivity.class);
//                            intent.putExtras(bundle);
//                            startActivity(intent);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
                            subCategoriesFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.main_container, subCategoriesFragment);
                            //edit_sku_no.getText().clear();
                            fragmentTransaction.addToBackStack(null).commit();
                        }
                    });
                    rv_category.setAdapter(category_adapter);
                    rv_category.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetMasterCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void getCategory(Integer cId) {
        Call<GetCategoryResponse> addAddress = ApiService.apiHolders().getCategory(2, token);
        addAddress.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
//                Toast.makeText(getContext(), "Address Added Successfully", Toast.LENGTH_SHORT).show();
                category_response = response.body().getData();
                category_horizental_adapter = new Category_horizental_Adapter(getContext(), category_response, new SubCategoriesListener() {
                    @Override
                    public void onItemClickedCategories(GetCategoryResponse.Datum item, int position, int type) {

                    }
                });
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                rv_category_sub.setLayoutManager(layoutManager);
                rv_category_sub.setAdapter(category_horizental_adapter);

            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}