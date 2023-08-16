package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.ProductListAdapter;
import com.example.licious.adapter.SubCategoryProductAdapter;
import com.example.licious.adapter.Top_Rated_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.listener.TopSellerListener;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.Category_Response;
import com.example.licious.response.ProductResponse;
import com.example.licious.response.SubCategoryItemResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {

    LinearLayout btn_cart;
    ImageView back;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id, product_id;
    ProgressDialog progressDialog;
    List<ProductResponse.Datum> productResponse;
    ImageView Iv_bg;
    TextView tv_tittle_product, product_type, tv_weight, tv_pieces, tv_serves, tv_descrp,tv_product_price;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    Top_Rated_Adapter top_rated_adapter;
    RecyclerView rv_category_product;
    ProductListAdapter productListAdapter;
    List<SubCategoryItemResponse.Datum> subProductItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product_id = bundle.getInt("products_id");
        }
        initi();
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(ProductDetails.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        btn_cart = findViewById(R.id.btn_cart);
        back = findViewById(R.id.back);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(product_id,tv_product_price.getText().toString());
               // startActivity(new Intent(ProductDetails.this, MyCart.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getProductDetails(product_id);
    }

    private void initi() {
        Iv_bg = findViewById(R.id.Iv_bg);
        tv_tittle_product = findViewById(R.id.tv_tittle_product);
        product_type = findViewById(R.id.product_type);
        tv_serves = findViewById(R.id.tv_serves);
        tv_descrp = findViewById(R.id.tv_descrp);
        tv_weight = findViewById(R.id.tv_weight);
        tv_pieces = findViewById(R.id.tv_pieces);
        rv_category_product = findViewById(R.id.rv_category_product);
        tv_product_price = findViewById(R.id.tv_product_price);

    }

    private void getProductDetails(int product_id) {
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
                    getProductList(Id);
                    //top_rated_adapter = new Top_Rated_Adapter(getApplicationContext(),productResponse)
                    Toast.makeText(ProductDetails.this, "Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductDetails.this, "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductList(int id) {
        progressDialog.show();
        Call<SubCategoryItemResponse> subCategoryDataProduct = ApiService.apiHolders().getSubCategoryProduct(2, token);
        subCategoryDataProduct.enqueue(new Callback<SubCategoryItemResponse>() {
            @Override
            public void onResponse(Call<SubCategoryItemResponse> call, Response<SubCategoryItemResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    subProductItem = response.body().getData();
                    productListAdapter = new ProductListAdapter(getApplicationContext(), subProductItem, new SubCategoriesProductListener() {
                        @Override
                        public void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type) {
                            product_id = item.getId();
                            String prices = item.getPrice();
                            addToCart(product_id, prices);//add to cart API
                        }

                        @Override
                        public void onItemClickedCategoriesProductWishList(SubCategoryItemResponse.Datum item, int position, int type) {
                            addWishList(item.getId(), item.getStatus());
                        }
                    });
                    rv_category_product.setAdapter(productListAdapter);
                    rv_category_product.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
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
        Picasso.with(getApplicationContext())
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
    private void addToCart(int product_id, String price) {
        progressDialog.show();
        Call<AddToCartResponse> addAddress = ApiService.apiHolders().add_to_cart(id, product_id,price, token);
        addAddress.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(ProductDetails.this, "" + "Successfully Added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("product_id", product_id);
                    Intent i = new Intent(ProductDetails.this, MyCart.class);
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
        Call<AddWishListResponse> addAddress = ApiService.apiHolders().add_wishList(id, prod_id, "True", token);
        addAddress.enqueue(new Callback<AddWishListResponse>() {
            @Override
            public void onResponse(Call<AddWishListResponse> call, Response<AddWishListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    String status = response.body().getStatus();
                    if (Objects.equals(status, "true")) {
                        // wishlist_status = true;
                        Toast.makeText(ProductDetails.this, "WishList Added Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // wishlist_status = false;
                        Toast.makeText(ProductDetails.this, "WishList Removed Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddWishListResponse> call, Throwable t) {
                Toast.makeText(ProductDetails.this, "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductDetails(product_id);
    }
}