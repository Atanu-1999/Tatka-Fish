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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.activity.MyCart;
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
import com.google.android.material.snackbar.Snackbar;

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
    TextView txt_noData;
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
    ImageView back;
    String deviceId;
    RelativeLayout rl_subCat;
    String pageType;

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
        back = subCat.findViewById(R.id.back);
        txt_noData = subCat.findViewById(R.id.txt_noData);
        rl_subCat = subCat.findViewById(R.id.rl_subCat);
        deviceId = DeviceUtils.getDeviceId(getContext());

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);


        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Bundle bundle = getArguments();
        if (bundle != null) {
            mcId = bundle.getInt("mcId");
            pageType = bundle.getString("page_type");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(pageType, "home")) {
                    Intent i = new Intent(getContext(),MainActivity.class);
                    startActivity(i);
                }
                else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Categories categories = new Categories();
                    fragmentTransaction.replace(R.id.main_container, categories);
                    //edit_sku_no.getText().clear();
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });

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
                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                    getCategorieesPoduct(mcId, deviceId);//CategoryProduct
                } else {
                    getCategorieesPoduct(mcId, token);
                }
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
                        bundle.putString("page_type", "subCat");
                        bundle.putInt("mcId", mcId);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FreshWaterFragment freshwater = new FreshWaterFragment();
                        freshwater.setArguments(bundle);
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
                ll_items.setVisibility(View.GONE);
                //Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
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
                    txt_noData.setVisibility(View.GONE);
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
                            //   }
                        }

                        @Override
                        public void onItemClickedItem(Category_Response.Datum item, int position, int type) {
                            int id = item.getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("products_id", id);
                            bundle.putString("page_type", "subCat");
                            bundle.putInt("mcId", mcId);
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
                    rv_sub_cat_product.setAdapter(categoryProductAdapter);
                    rv_sub_cat_product.setLayoutManager(new LinearLayoutManager(getContext()));
                }

            }

            @Override
            public void onFailure(Call<Category_Response> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                rv_sub_cat_product.setVisibility(View.GONE);
                txt_noData.setVisibility(View.VISIBLE);
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Add to cart
    private void addToCart(int product_id, String price, String token) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(id, product_id, price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                   // Toast.makeText(getContext(), "" + "Successfully Added", Toast.LENGTH_SHORT).show();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_subCat, "Successfully Added", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();

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
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_subCat, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
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
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_subCat, "WishList Added Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    getCategorieesPoduct(mcId, token);
                }
            }

            @Override
            public void onFailure(Call<AddWishListResponse> call, Throwable t) {
               // Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_subCat, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
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
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_subCat, "WishList Remove Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    // Toast.makeText(getContext(), "WishList Remove Successfully", Toast.LENGTH_SHORT).show();
                    getCategorieesPoduct(mcId, token);
                }
            }

            @Override
            public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_subCat, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
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