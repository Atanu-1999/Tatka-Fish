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
import com.example.licious.activity.ProductDetails;
import com.example.licious.adapter.ProductListAdapter;
import com.example.licious.adapter.Top_Rated_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.ProductResponse;
import com.example.licious.response.RemoveWishListResponse;
import com.example.licious.response.SubCategoryItemResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {

    LinearLayout btn_cart;
    ImageView back;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token, page_type;
    int id, product_id;
    ProgressDialog progressDialog;
    List<ProductResponse.Datum> productResponse;
    ImageView Iv_bg;
    TextView tv_tittle_product, product_type, tv_weight, tv_pieces, tv_serves, tv_descrp, tv_product_price;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    Top_Rated_Adapter top_rated_adapter;
    RecyclerView rv_category_product;
    ProductListAdapter productListAdapter;
    List<SubCategoryItemResponse.Datum> subProductItem;
    String BlankId = "";
    String deviceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ProductDetails = inflater.inflate(R.layout.fragment_product_details, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            product_id = bundle.getInt("products_id");
            page_type = bundle.getString("page_type", null);
        }
        initi();
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        btn_cart = ProductDetails.findViewById(R.id.btn_cart);
        back = ProductDetails.findViewById(R.id.back);

        Iv_bg = ProductDetails.findViewById(R.id.Iv_bg);
        tv_tittle_product = ProductDetails.findViewById(R.id.tv_tittle_product);
        product_type = ProductDetails.findViewById(R.id.product_type);
        tv_serves = ProductDetails.findViewById(R.id.tv_serves);
        tv_descrp = ProductDetails.findViewById(R.id.tv_descrp);
        tv_weight = ProductDetails.findViewById(R.id.tv_weight);
        tv_pieces = ProductDetails.findViewById(R.id.tv_pieces);
        rv_category_product = ProductDetails.findViewById(R.id.rv_category_product);
        tv_product_price = ProductDetails.findViewById(R.id.tv_product_price);

        // /*Device Id Get*/
        deviceId = DeviceUtils.getDeviceId(getContext());
        Log.e("Device Id", "" + deviceId);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(ProductDetails.this, MyCart.class));
//                if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Account account = new Account();
//                    fragmentTransaction.replace(R.id.main_container, account);
//                    fragmentTransaction.addToBackStack(null).commit();
//                } else {
                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                    addToCart(product_id, tv_product_price.getText().toString(), deviceId);
                }else {
                    addToCart(product_id, tv_product_price.getText().toString(), token);
                }
                //  }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               getActivity().finish();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Home home = new Home();
//                fragmentTransaction.replace(R.id.main_container, home);
//                fragmentTransaction.addToBackStack(null).commit();

            }
        });
        Log.d("device_id", loginPref.getString("device_id", ""));
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getProductDetails(product_id, deviceId);
        } else {
            getProductDetails(product_id, token);
        }

        return ProductDetails;
    }

    private void initi() {
//        Iv_bg = ProductDetails.findViewById(R.id.Iv_bg);
//        tv_tittle_product = ProductDetails.findViewById(R.id.tv_tittle_product);
//        product_type = findViewById(R.id.product_type);
//        tv_serves = findViewById(R.id.tv_serves);
//        tv_descrp = findViewById(R.id.tv_descrp);
//        tv_weight = findViewById(R.id.tv_weight);
//        tv_pieces = findViewById(R.id.tv_pieces);
//        rv_category_product = findViewById(R.id.rv_category_product);
//        tv_product_price = findViewById(R.id.tv_product_price);

    }

    private void getProductDetails(int product_id, String token) {
        progressDialog.show();
        Call<ProductResponse> productDetails = ApiService.apiHolders().getProductDetails(product_id, token);
        productDetails.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    productResponse = response.body().getData();
                    setProductData(productResponse);//set data
                    String s_cId = productResponse.get(0).getSc_id();
                    int Id = Integer.parseInt(s_cId);
                    if (BlankId.equals(loginPref.getString("device_id", ""))) {
                        getProductList(Id, deviceId);
                    } else {
                        getProductList(Id, token);
                    }

                    //top_rated_adapter = new Top_Rated_Adapter(getApplicationContext(),productResponse)
                    // Toast.makeText(ProductDetails.this, "Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductList(int id, String token) {
        progressDialog.show();
        Call<SubCategoryItemResponse> subCategoryDataProduct = ApiService.apiHolders().getSubCategoryProduct(id, token);
        subCategoryDataProduct.enqueue(new Callback<SubCategoryItemResponse>() {
            @Override
            public void onResponse(Call<SubCategoryItemResponse> call, Response<SubCategoryItemResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subProductItem = response.body().getData();
                    productListAdapter = new ProductListAdapter(getContext(), subProductItem, new SubCategoriesProductListener() {
                        @Override
                        public void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type) {
//                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Account account = new Account();
//                                fragmentTransaction.replace(R.id.main_container, account);
//                                fragmentTransaction.addToBackStack(null).commit();
//                            } else {
//                            product_id = item.getId();
//                            String prices = item.getPrice();
//                            addToCart(product_id, prices);//add to cart API

                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, deviceId);//add to cart API
                            } else {
                                product_id = item.getId();
                                String price = item.getPrice();
                                addToCart(product_id, price, token);//add to cart API
                            }

                            //}
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
                    rv_category_product.setAdapter(productListAdapter);
                    rv_category_product.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                }

            }

            @Override
            public void onFailure(Call<SubCategoryItemResponse> call, Throwable t) {
                //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setProductData(List<ProductResponse.Datum> productResponse) {
        Picasso.with(getContext())
                .load(image_url + productResponse.get(0).getProduct_image())
                .into(Iv_bg);
        tv_tittle_product.setText(productResponse.get(0).getProduct_title());
        tv_serves.setText(productResponse.get(0).getServes());
        tv_descrp.setText(productResponse.get(0).getDescription());
        tv_weight.setText(productResponse.get(0).getWeight() + productResponse.get(0).getWeight_type());
        tv_pieces.setText(productResponse.get(0).getPieces());
        tv_product_price.setText(productResponse.get(0).getPrice());
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

    //WISHlIST
    private void addWishList(Integer prod_id, String status) {
        progressDialog.show();
        Call<AddWishListResponse> addAddress = ApiService.apiHolders().add_wishList(id, prod_id, status, token);
        addAddress.enqueue(new Callback<AddWishListResponse>() {
            @Override
            public void onResponse(Call<AddWishListResponse> call, Response<AddWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "WishList Added Successfully", Toast.LENGTH_SHORT).show();
                    // getProductList(id, deviceId);
                    if (BlankId.equals(loginPref.getString("device_id", ""))) {
                        getProductList(id, deviceId);
                    } else {
                        getProductList(id, token);
                    }
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
                    Toast.makeText(getContext(), "WishList Remove Successfully", Toast.LENGTH_SHORT).show();
                    // getProductList(id);
                    if (BlankId.equals(loginPref.getString("device_id", ""))) {
                        getProductList(id, deviceId);
                    } else {
                        getProductList(id, token);
                    }
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //getProductDetails(product_id);
//        if (BlankId.equals(loginPref.getString("device_id", ""))) {
//            getProductList(id, deviceId);
//        } else {
//            getProductList(id, token);
//        }
//    }

}