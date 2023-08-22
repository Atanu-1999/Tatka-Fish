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
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.activity.Freshwater;
import com.example.licious.activity.MyCart;
import com.example.licious.activity.ProductDetails;
import com.example.licious.activity.SubCatergoriesActivity;
import com.example.licious.adapter.CategoryProductAdapter;
import com.example.licious.adapter.Category_horizental_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.MasterCategoryprouduct;
import com.example.licious.listener.SubCategoriesListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.Category_Response;
import com.example.licious.response.GetCategoryResponse;
import com.example.licious.response.RemoveWishListResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoriesFragment extends Fragment {
    LinearLayout btn_all, btn_fresh, btn_crab, btn_sea;
    AllFish all = new AllFish();
    Crab crab = new Crab();

    ImageView product_search;
    RecyclerView rv_category_sub_s, rv_sub_cat_product;
    List<GetCategoryResponse.Datum> category_response;
    List<Category_Response.Datum> category_response_product;
    Category_horizental_Adapter category_horizental_adapter;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id, cId, mcId;
    String token;
    ProgressDialog progressDialog;
    CategoryProductAdapter categoryProductAdapter;
    int product_id;
    LinearLayout ll_items;
    String BlankId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View subCat = inflater.inflate(R.layout.fragment_sub_categories, container, false);
        rv_category_sub_s = subCat.findViewById(R.id.rv_category_sub_ss);
        rv_sub_cat_product = subCat.findViewById(R.id.rv_sub_cat_product);
        ll_items = subCat.findViewById(R.id.ll_items);

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        // /*Device Id Get*/
        String deviceId = DeviceUtils.getDeviceId(getContext());
        Log.e("Device Id", "" + deviceId);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Bundle bundle = getArguments();
        if (bundle != null) {
            mcId = bundle.getInt("mcId");
        }

//        getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
//        btn_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,all).commit();
//            }
//        });
//        btn_fresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SubCatergoriesActivity.this,Freshwater.class));
//            }
//        });
//        btn_sea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  startActivity(new Intent(Subcategories.this,Seawater.class));
//            }
//        });
//        btn_crab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.contenair,crab).commit();
//            }
//        });
//        product_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  startActivity(new Intent(Subcategories.this,SearchActivity.class));
//            }
//        });
        // getCategory(cId);
        Log.d("device_id", loginPref.getString("device_id", ""));
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getCategory(mcId, deviceId);//Category
            getCategorieesPoduct(mcId, deviceId);//CategoryProduct
        } else {
            getCategory(mcId, token);//Category
            getCategorieesPoduct(mcId, token);//CategoryProduct
        }
        ll_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategorieesPoduct(mcId, token);
            }
        });
        return subCat;
    }


    private void getCategory(Integer mcId, String token) {
        progressDialog.show();
        Call<GetCategoryResponse> addAddress = ApiService.apiHolders().getCategory(mcId, token);
        addAddress.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                progressDialog.dismiss();
//                Toast.makeText(getContext(), "Address Added Successfully", Toast.LENGTH_SHORT).show();
                category_response = response.body().getData();

                // getCategorieesPoduct();

                category_horizental_adapter = new Category_horizental_Adapter(getContext(), category_response, new SubCategoriesListener() {
                    @Override
                    public void onItemClickedCategories(GetCategoryResponse.Datum item, int position, int type) {
                        int id = item.getId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("cId", id);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FreshWaterFragment freshwater = new FreshWaterFragment();
                        fragmentTransaction.replace(R.id.main_container, freshwater);
                        fragmentTransaction.addToBackStack(null).commit();
//                        Intent i = new Intent(getContext(), Freshwater.class);
//                        i.putExtras(bundle);
//                        startActivity(i);
                    }
                });
//                GridLayoutManager layoutManager = new GridLayoutManager(getApplication(), 3);
//                rv_category_sub_s.setLayoutManager(layoutManager);
//                rv_category_sub_s.setAdapter(category_horizental_adapter);
                rv_category_sub_s.setAdapter(category_horizental_adapter);
                // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_category_sub_s.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
//                Toast.makeText(SubCatergoriesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCategorieesPoduct(int mcId, String token) {
        progressDialog.show();
        Call<Category_Response> subCategoryDataProduct = ApiService.apiHolders().getCategoryProduct(mcId, token);
        subCategoryDataProduct.enqueue(new Callback<Category_Response>() {
            @Override
            public void onResponse(Call<Category_Response> call, Response<Category_Response> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    category_response_product = response.body().getData();
                    categoryProductAdapter = new CategoryProductAdapter(getContext(), category_response_product, new MasterCategoryprouduct() {
                        @Override
                        public void onItemClickedMasterCategoriesProductWishList(Category_Response.Datum item, int position, int type) {
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
                        public void onItemClickedMasterCategoriesProductADDcart(Category_Response.Datum item, int position, int type) {
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Account account = new Account();
                                fragmentTransaction.replace(R.id.main_container, account);
                                fragmentTransaction.addToBackStack(null).commit();
                            } else {
                                product_id = item.getId();
                                String prices = item.getPrice();
                                addToCart(product_id, prices);//add to cart API
                            }
                        }

                        @Override
                        public void onItemClickedItem(Category_Response.Datum item, int position, int type) {
                            int id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("products_id", id);
                            Intent i = new Intent(getContext(), ProductDetails.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    rv_sub_cat_product.setAdapter(categoryProductAdapter);
                    rv_sub_cat_product.setLayoutManager(new LinearLayoutManager(getContext()));
                }

            }

            @Override
            public void onFailure(Call<Category_Response> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Add to cart
    private void addToCart(int product_id, String price) {
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

    //WISHlIST
    private void addWishList(Integer prod_id, String status) {
        progressDialog.show();
        Call<AddWishListResponse> addAddress = ApiService.apiHolders().add_wishList(id, prod_id, status, token);
        addAddress.enqueue(new Callback<AddWishListResponse>() {
            @Override
            public void onResponse(Call<AddWishListResponse> call, Response<AddWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    getCategorieesPoduct(mcId, token);
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
                    String status = response.body().getStatus();
                    // Toast.makeText(getContext(), "WishList Remove Successfully", Toast.LENGTH_SHORT).show();
                    getCategorieesPoduct(mcId, token);
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

    @Override
    public void onResume() {
        super.onResume();
        getCategorieesPoduct(mcId, token);//CategoryProduct
    }
}