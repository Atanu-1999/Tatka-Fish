package com.example.licious.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.adapter.SubCategoryProductAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.RemoveWishListResponse;
import com.example.licious.response.SubCategoryItemResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryTypeProductFragment extends Fragment {
    RecyclerView rv_sub_cat_product_list;
    int cId, mcId, scId, id;
    String page_type;
    String deviceId;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token, sub_sub_cat,cat_title,master_title;
    String BlankId = "";
    List<SubCategoryItemResponse.Datum> subProductItem;
    TextView tv_totalItem;
    int product_id;
    SubCategoryProductAdapter subCategoryProductAdapter;
    RelativeLayout rl_freshwater;
    ImageView back;
    TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View subCatProd = inflater.inflate(R.layout.fragment_sub_category_type_product, container, false);
        rv_sub_cat_product_list = subCatProd.findViewById(R.id.rv_sub_cat_product_list);
        rl_freshwater = subCatProd.findViewById(R.id.rl_freshwater);
        back = subCatProd.findViewById(R.id.back);
        title = subCatProd.findViewById(R.id.title);

        tv_totalItem = subCatProd.findViewById(R.id.tv_totalItem);
        // /*Device Id Get*
        deviceId = DeviceUtils.getDeviceId(getContext());
        Log.e("Device Id", "" + deviceId);
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Bundle bundle = getArguments();
        //Extract the data…
        if (bundle != null) {
            cId = bundle.getInt("cId", 0);
            scId = bundle.getInt("scId", 0);
            page_type = bundle.getString("page_type", null);
            mcId = bundle.getInt("mcId", 0);
            sub_sub_cat = bundle.getString("sub_sub_cat");
            cat_title = bundle.getString("cat_title");
            master_title = bundle.getString("master_title");
        }
        title.setText(sub_sub_cat);

        //call API
        Log.d("device_id", loginPref.getString("device_id", ""));
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            callSubCatProd(scId, deviceId);
        } else {
            callSubCatProd(scId, token);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (page_type.equals("frehwater")){
                Bundle bundle = new Bundle();
                bundle.putInt("mcId", mcId);
                bundle.putInt("cId", cId);
                bundle.putString("cat_title", cat_title);
                bundle.putString("master_title",master_title);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FreshWaterFragment subCategoriesFragment = new FreshWaterFragment();
                subCategoriesFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.main_container, subCategoriesFragment);
                //edit_sku_no.getText().clear();
                fragmentTransaction.addToBackStack(null).commit();
//                }
            }
        });


        return subCatProd;
    }

    private void callSubCatProd(int scId, String token) {
        progressDialog.show();
        Call<SubCategoryItemResponse> subCategoryDataProduct = ApiService.apiHolders().getSubCategoryProductList(scId, token);
        subCategoryDataProduct.enqueue(new Callback<SubCategoryItemResponse>() {
            @Override
            public void onResponse(Call<SubCategoryItemResponse> call, Response<SubCategoryItemResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subProductItem = response.body().getData();
                    String count = String.valueOf(subProductItem.size());
                    tv_totalItem.setText(count + " " + "Items");

                    subCategoryProductAdapter = new SubCategoryProductAdapter(getContext(), subProductItem, new SubCategoriesProductListener() {
                        @Override
                        public void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type) {
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, deviceId);//add to cart API
                            } else {
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, token);//add to cart API
                            }
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

                        }
                    });
                    rv_sub_cat_product_list.setAdapter(subCategoryProductAdapter);
                    rv_sub_cat_product_list.setLayoutManager(new LinearLayoutManager(getContext()));


                }

            }

            @Override
            public void onFailure(Call<SubCategoryItemResponse> call, Throwable t) {
                progressDialog.dismiss();
//                rv_sub_cat_product.setVisibility(View.GONE);
//                txt_noData.setVisibility(View.VISIBLE);
            }
        });
    }

    // add to cart
    private void addToCart(int product_id, String price, String token) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(product_id, price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_freshwater, "Added Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("product_id", product_id);
//                    Intent i = new Intent(getContext(), MyCart.class);
//                    i.putExtras(bundle);
//                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_freshwater, "failed", Snackbar.LENGTH_LONG);
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
                    errorBar = Snackbar.make(rl_freshwater, "WishList Added Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    callSubCatProd(scId, token);
                }
            }

            @Override
            public void onFailure(Call<AddWishListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_freshwater, "failed", Snackbar.LENGTH_LONG);
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
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_freshwater, "WishList Remove Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    // Toast.makeText(getContext(), "WishList Remove Successfully", Toast.LENGTH_SHORT).show();
                    callSubCatProd(scId, token);
                }
            }

            @Override
            public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_freshwater, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }
}