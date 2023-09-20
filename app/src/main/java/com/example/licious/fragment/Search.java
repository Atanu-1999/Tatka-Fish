package com.example.licious.fragment;

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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.activity.MyCart;
import com.example.licious.adapter.ALl_CategoryAdapter;
import com.example.licious.adapter.SeacrhItemListAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.AllCategoryListener;
import com.example.licious.listener.SearchItemClickListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.SearchResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends Fragment {
    RecyclerView rv_all_category,rv_Search_all_category;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    List<AllCaterogyResponse.Datum> allCategory;
    ALl_CategoryAdapter aLl_categoryAdapter;
    SearchView sv_search;
    String cName;
    SeacrhItemListAdapter seacrhItemListAdapter;
    List<SearchResponse.Datum> searchListresponse;
    int product_id;
    String deviceId,BlankId="";
    RelativeLayout rl_view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        rv_all_category = v.findViewById(R.id.rv_all_category);
        sv_search = v.findViewById(R.id.sv_search);
        rv_Search_all_category = v.findViewById(R.id.rv_Search_all_category);
        rl_view = v.findViewById(R.id.rl_view);

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        deviceId = DeviceUtils.getDeviceId(getContext()); //getDeviceID

        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            get_all_categories(deviceId);
        }
        else {
            get_all_categories(token);
        }

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cName = s;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cName = newText;
                searchSku(newText);
                return false;
            }
        });

        return v;
    }

    private void searchSku(String newText) {
        if (newText.isEmpty()) {
           // Toast.makeText(getContext(), "Please Enter Product Name", Toast.LENGTH_SHORT).show();
            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                get_all_categories(deviceId);
            }
            else {
                get_all_categories(token);
            }
        } else {
            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                search_api(0,deviceId);
            }else {
                search_api(id,token);
            }
        }
    }

    private void search_api(int id,String token) {
       // progressDialog.show();
        Call<SearchResponse> category_apiCall = ApiService.apiHolders().getSearch(id,cName, token);
        category_apiCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                   // progressDialog.dismiss();
                    searchListresponse = response.body().getData();
                    rv_Search_all_category.setVisibility(View.VISIBLE);
                    rv_all_category.setVisibility(View.GONE);
                  // Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
                    seacrhItemListAdapter = new SeacrhItemListAdapter(getContext(), searchListresponse, new SearchItemClickListener() {
                        @Override
                        public void onItemClickedSearchItem(SearchResponse.Datum item, int position, int type) {
//                            product_id = item.getId();
//                            String prices = item.getPrice();
//                            addToCart(product_id, prices);//add to cart API
//                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Account account = new Account();
//                                fragmentTransaction.replace(R.id.main_container, account);
//                                fragmentTransaction.addToBackStack(null).commit();
//                            } else {
//                                product_id = item.getId();
//                                String prices = item.getPrice();
//                                addToCart(product_id, prices);//add to cart API
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                                product_id = item.getId();
//                                String price = item.getPrice();
//                                addToCart(product_id, price, deviceId);//add to cart API
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Account account = new Account();
                                fragmentTransaction.replace(R.id.main_container, account);
                                fragmentTransaction.addToBackStack(null).commit();
                            } else {
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, token);//add to cart API
                            }
                           // }
                        }
                    });
                    rv_Search_all_category.setAdapter(seacrhItemListAdapter);
                    rv_Search_all_category.setLayoutManager(new LinearLayoutManager(getContext()));

                } else {
//                    Snackbar errorBar;
//                    errorBar = Snackbar.make(rl_view, "No item Found", Snackbar.LENGTH_LONG);
//                    errorBar.setTextColor(getResources().getColor(R.color.white));
//                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
//                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
//                    errorBar.show();
                   // Toast.makeText(getContext(),"Somethings went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
               // progressDialog.dismiss();
               // Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
//                Snackbar errorBar;
//                errorBar = Snackbar.make(rl_view, "No item Found", Snackbar.LENGTH_LONG);
//                errorBar.setTextColor(getResources().getColor(R.color.white));
//                errorBar.setActionTextColor(getResources().getColor(R.color.white));
//                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
//                errorBar.show();
            }
        });

    }

    private void get_all_categories(String token) {
        progressDialog.show();
        Call<AllCaterogyResponse> category_apiCall = ApiService.apiHolders().allCategory(token);
        category_apiCall.enqueue(new Callback<AllCaterogyResponse>() {
            @Override
            public void onResponse(Call<AllCaterogyResponse> call, Response<AllCaterogyResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    rv_Search_all_category.setVisibility(View.GONE);
                    rv_all_category.setVisibility(View.VISIBLE);
                    String response1 = response.body().toString();
                    allCategory = response.body().getData();
                    aLl_categoryAdapter = new ALl_CategoryAdapter(getContext(), allCategory, new AllCategoryListener() {
                        @Override
                        public void onItemClickedItem(AllCaterogyResponse.Datum item, int position, int type) {
//                            int id = item.getId();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("cId", id);
//                            Intent i = new Intent(getContext(), Freshwater.class);
//                            i.putExtras(bundle);
//                            startActivity(i);
                            int id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("cId", id);
                            bundle.putString("page_type", "search");
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            FreshWaterFragment freshwater = new FreshWaterFragment();
                            freshwater.setArguments(bundle);
                            fragmentTransaction.replace(R.id.main_container, freshwater);
                            fragmentTransaction.addToBackStack(null).commit();
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                    rv_all_category.setLayoutManager(layoutManager);
                    rv_all_category.setAdapter(aLl_categoryAdapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<AllCaterogyResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void addToCart(int product_id, String price,String token) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(id,product_id, price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
//                    Toast.makeText(getContext(), "" + "Successfully Added", Toast.LENGTH_SHORT).show();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("product_id", product_id);
//                    Intent i = new Intent(getContext(), MyCart.class);
//                    i.putExtras(bundle);
//                    startActivity(i);

                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_view, "Successfully Added", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                }

            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}