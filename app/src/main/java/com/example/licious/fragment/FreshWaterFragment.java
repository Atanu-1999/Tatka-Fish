package com.example.licious.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.activity.MyCart;
import com.example.licious.adapter.SubCategoryProductAdapter;
import com.example.licious.adapter.Sub_categoryAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.SubCategoriesItemListener;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.GetSubCategoryResponse;
import com.example.licious.response.RemoveWishListResponse;
import com.example.licious.response.SubCategoryItemResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FreshWaterFragment extends Fragment {
    int cId;
    RecyclerView rv_sub_cat, rv_sub_cat_product;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id;
    List<GetSubCategoryResponse.Datum> subCategories;
    List<SubCategoryItemResponse.Datum> subProductItem;
    Sub_categoryAdapter sub_categoryAdapter;
    LinearLayout ll_items;
    SubCategoryProductAdapter subCategoryProductAdapter;
    TextView tv_totalItem,txt_noData;
    int product_id;
    String BlankId = "";
    int page;
    ImageView back;
    String deviceId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View freshWater = inflater.inflate(R.layout.fragment_fresh_water, container, false);
        rv_sub_cat = freshWater.findViewById(R.id.rv_sub_cat);
        ll_items = freshWater.findViewById(R.id.ll_items);
        rv_sub_cat_product = freshWater.findViewById(R.id.rv_sub_cat_product);
        tv_totalItem = freshWater.findViewById(R.id.tv_totalItem);
        back  = freshWater.findViewById(R.id.back);
        txt_noData = freshWater.findViewById(R.id.txt_noData);

        Bundle bundle =getArguments();
        //Extract the data…
        if (bundle != null) {
            cId = bundle.getInt("cId", 0);
            page = bundle.getInt("page", 0);
        }

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        // /*Device Id Get*
        deviceId = DeviceUtils.getDeviceId(getContext());
        Log.e("Device Id", "" + deviceId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page==1){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Search search = new Search();
                    fragmentTransaction.replace(R.id.main_container, search);
                    //edit_sku_no.getText().clear();
                    fragmentTransaction.addToBackStack(null).commit();
                }
                else if (page==2){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
                    fragmentTransaction.replace(R.id.main_container, subCategoriesFragment);
                    //edit_sku_no.getText().clear();
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });

        ll_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                    getSubCategortItem(cId, deviceId);
                } else {
                    getSubCategortItem(cId, token);
                }
            }
        });

        Log.d("device_id", loginPref.getString("device_id", ""));
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getSubCategory(cId, deviceId);
            getSubCategortItem(cId, deviceId);
        } else {
            getSubCategory(cId, token);
            getSubCategortItem(cId, token);
        }


        return freshWater;
    }

    private void getSubCategory(int cId, String token) {
        progressDialog.show();
        Call<GetSubCategoryResponse> subCategoryData = ApiService.apiHolders().getSubCategory(cId, token);
        subCategoryData.enqueue(new Callback<GetSubCategoryResponse>() {
            @Override
            public void onResponse(Call<GetSubCategoryResponse> call, Response<GetSubCategoryResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subCategories = response.body().getData();
                    sub_categoryAdapter = new Sub_categoryAdapter(getContext(), subCategories, new SubCategoriesItemListener() {
                        @Override
                        public void onItemClickedCategoriesItem(GetSubCategoryResponse.Datum item, int position, int type) {
                            int cId = item.getC_id();
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                getSubCategortItem(cId, deviceId);
                            }else {
                                getSubCategortItem(cId, token);
                            }
                        }
                    });
                    rv_sub_cat.setAdapter(sub_categoryAdapter);
                    // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv_sub_cat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                }

            }

            @Override
            public void onFailure(Call<GetSubCategoryResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                ll_items.setVisibility(View.GONE);
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSubCategortItem(int cId, String token) {
        progressDialog.show();
        Call<SubCategoryItemResponse> subCategoryDataProduct = ApiService.apiHolders().getSubCategoryProduct(cId, token);
        subCategoryDataProduct.enqueue(new Callback<SubCategoryItemResponse>() {
            @Override
            public void onResponse(Call<SubCategoryItemResponse> call, Response<SubCategoryItemResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subProductItem = response.body().getData();
                    subCategoryProductAdapter = new SubCategoryProductAdapter(getContext(), subProductItem, new SubCategoriesProductListener() {
                        @Override
                        public void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type) {
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
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, deviceId);//add to cart API
                            } else {
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, token);//add to cart API
                            }
                          //  }
                        }

                        @Override
                        public void onItemClickedCategoriesProductWishList(SubCategoryItemResponse.Datum item, int position, int type) {
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Account account = new Account();
                                fragmentTransaction.replace(R.id.main_container, account);
                                fragmentTransaction.addToBackStack(null).commit();
                            } else {
                                if (Objects.equals(item.getWishlist_status(), "False")) {
                                    addWishList(item.getId(), "True");
                                } else {
                                    removeWishList(item.getId(), "False");
                                }
                            }
                        }

                        @Override
                        public void onItemClickedItem(SubCategoryItemResponse.Datum item, int position, int type) {
                            int id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("products_id", id);
//                            Intent i = new Intent(getContext(), ProductDetails.class);
//                            i.putExtras(bundle);
//                            startActivity(i);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                            productDetailsFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.main_container, productDetailsFragment);
                            //edit_sku_no.getText().clear();
                            fragmentTransaction.addToBackStack(null).commit();
                        }
                    });
                    rv_sub_cat_product.setAdapter(subCategoryProductAdapter);
                    rv_sub_cat_product.setLayoutManager(new LinearLayoutManager(getContext()));
                }

            }

            @Override
            public void onFailure(Call<SubCategoryItemResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                rv_sub_cat_product.setVisibility(View.GONE);
                txt_noData.setVisibility(View.VISIBLE);
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //WISHlIST
    private void addWishList(Integer prod_id, String status) {
        progressDialog.show();
        Call<AddWishListResponse> addAddress = ApiService.apiHolders().add_wishList(id, prod_id, status, token);
        addAddress.enqueue(new Callback<AddWishListResponse>() {
            @Override
            public void onResponse(Call<AddWishListResponse> call, Response<AddWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    getSubCategortItem(cId,token);
                }
            }

            @Override
            public void onFailure(Call<AddWishListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeWishList(Integer prod_id, String status) {
        progressDialog.show();
        Call<RemoveWishListResponse> addAddress = ApiService.apiHolders().remove_wishList(id, prod_id, status, token);
        addAddress.enqueue(new Callback<RemoveWishListResponse>() {
            @Override
            public void onResponse(Call<RemoveWishListResponse> call, Response<RemoveWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    // Toast.makeText(getContext(), "WishList Remove Successfully", Toast.LENGTH_SHORT).show();
                    getSubCategortItem(cId,token);
                }
            }

            @Override
            public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // add to cart
    private void addToCart(int product_id, String price,String token) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(id, product_id, price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "" + "Successfully Added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("product_id", product_id);
                    Intent i = new Intent(getContext(), MyCart.class);
                    i.putExtras(bundle);
                    startActivity(i);
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