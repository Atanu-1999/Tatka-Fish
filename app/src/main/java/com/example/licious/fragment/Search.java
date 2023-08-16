package com.example.licious.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.activity.Freshwater;
import com.example.licious.activity.SubCatergoriesActivity;
import com.example.licious.adapter.ALl_CategoryAdapter;
import com.example.licious.adapter.Category_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.AllCategoryListener;
import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.GetMasterCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends Fragment {
    RecyclerView rv_all_category;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    List<AllCaterogyResponse.Datum> allCategory;
    ALl_CategoryAdapter aLl_categoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        rv_all_category = v.findViewById(R.id.rv_all_category);
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        get_all_categories();

        return v;
    }

    private void get_all_categories() {
        progressDialog.show();
        Call<AllCaterogyResponse> category_apiCall = ApiService.apiHolders().allCategory(token);
        category_apiCall.enqueue(new Callback<AllCaterogyResponse>() {
            @Override
            public void onResponse(Call<AllCaterogyResponse> call, Response<AllCaterogyResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    String response1 = response.body().toString();
                    allCategory = response.body().getData();
                    aLl_categoryAdapter = new ALl_CategoryAdapter(getContext(), allCategory, new AllCategoryListener() {
                        @Override
                        public void onItemClickedItem(AllCaterogyResponse.Datum item, int position, int type) {
                            int id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("cId",id);
                            Intent i = new Intent(getContext(), Freshwater.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                    rv_all_category.setLayoutManager(layoutManager);
                    rv_all_category.setAdapter(aLl_categoryAdapter);
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<AllCaterogyResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}