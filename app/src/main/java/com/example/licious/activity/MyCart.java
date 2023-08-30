package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.listener.DeleteListener;
import com.example.licious.R;
import com.example.licious.adapter.MyCartAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.response.AddRemoveResponse;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.CartItemDeleteResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCart extends AppCompatActivity {

    TextView txt_change, btn_continue, tv_add_type, tv_address;
    CardView btn_proceed;
    RecyclerView rv_cart;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id;
    List<CartDetailsResponse.Datum> cardDetailsResponse;
    MyCartAdapter myCartAdapter;
    ProgressDialog progressDialog;
    ArrayList<String> listPrice = new ArrayList<>();
    TextView tv_subtotal;
    int Total_price;
    int tp = 0;
    String tpp;
    LinearLayout ll_amount_view;
    RelativeLayout rl_noData,rl_cart;
    CardView checkout;
    ImageView back;
    String BlankId = "";
    String deviceId;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        rv_cart = findViewById(R.id.rv_cart);
        btn_proceed = findViewById(R.id.btn_proceed);
        txt_change = findViewById(R.id.txt_change);
        tv_add_type = findViewById(R.id.tv_add_type);
        tv_address = findViewById(R.id.tv_address);
        tv_subtotal = findViewById(R.id.tv_subtotal);
        ll_amount_view = findViewById(R.id.ll_amount_view);
        rl_noData = findViewById(R.id.rl_noData);
        checkout = findViewById(R.id.checkout);
        back = findViewById(R.id.back);
        rl_cart = findViewById(R.id.rl_cart);

        deviceId = DeviceUtils.getDeviceId(MyCart.this);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        int add_Id = loginPref.getInt("add_id", 0);
        String adds_one = loginPref.getString("adds_one", null);
        String adds_two = loginPref.getString("adds_two", null);
        String add_type = loginPref.getString("add_type", null);
        String city = loginPref.getString("city", null);

        //for address
        if (add_Id == 0) {
            txt_change.setText("Select Address");
        } else {
            txt_change.setText("Change");
            tv_add_type.setText(add_type);
            tv_address.setText(adds_one + adds_two + city);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCart.this,MainActivity.class);
                startActivity(intent);
            }
        });


        //loading
        progressDialog = new ProgressDialog(MyCart.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int product_id = bundle.getInt("product_id");
        }

        txt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("addr_type", 1);
                Intent i = new Intent(MyCart.this, Address.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("total_amount", Integer.parseInt(tpp));
                    Intent i = new Intent(MyCart.this, CheckoutPage.class);
                    i.putExtras(bundle);
                    startActivity(i);
                    //  startActivity(new Intent(MyCart.this, CheckoutPage.class));
                }
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, MainActivity.class));
            }
        });
//        btn_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MyCart.this, SearchActivity.class));
//            }
//        });
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getCartDetails(deviceId);
        }else {
            getCartDetails(token);
        }
    }

    private boolean validation() {
        if (tv_add_type.getText().toString().equals("")) {
            Toast.makeText(this, "Please select address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getCartDetails(String token) {
        progressDialog.show();

        Call<CartDetailsResponse> addAddress = ApiService.apiHolders().getCartDetails(token);
        addAddress.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(Call<CartDetailsResponse> call, Response<CartDetailsResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    cardDetailsResponse = response.body().getData();
                    totalPrice(cardDetailsResponse);
                    myCartAdapter = new MyCartAdapter(getApplicationContext(), cardDetailsResponse, new DeleteListener() {
                        @Override
                        public void onItemClickedDelete(CartDetailsResponse.Datum item, int position, int type) {
                            //Toast.makeText(MyCart.this,"Deleted",Toast.LENGTH_SHORT).show();
                            int cartId = item.getId();
                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                deleteItem(cartId,deviceId);
                            }else {
                                deleteItem(cartId,token);
                            }
                        }

                        @Override
                        public void onItemClickedAdditem(CartDetailsResponse.Datum item, int position, int type) {
                            // Toast.makeText(MyCart.this,"ADDED",Toast.LENGTH_SHORT).show();
                            int Id = item.getId();
                            String price = item.getOffer_price();

                            int qty = Integer.parseInt(item.getQty());
                            int qtn = 1 + qty;
                            if (qtn > 1) {
                                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                    addOrRemoveItem(deviceId,Id, price, qtn);
                                }else {
                                    addOrRemoveItem(token,Id, price, qtn);
                                }
                            } else {
                                Toast.makeText(MyCart.this, "Can not less than 1", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onItemClickedRemoveItem(CartDetailsResponse.Datum item, int position, int type) {
                            //Toast.makeText(MyCart.this,"REMOVE",Toast.LENGTH_SHORT).show();
                            int Id = item.getId();
                            String price = item.getOffer_price();
                            int qty = Integer.parseInt(item.getQty());
                            int qtn = qty - 1;
                            if (qtn >= 1) {
                                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                                    addOrRemoveItem(deviceId,Id, price, qtn);
                                }else {
                                    addOrRemoveItem(token,Id, price, qtn);
                                }
                            } else {
                                Toast.makeText(MyCart.this, "Can not less than 1", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    rv_cart.setAdapter(myCartAdapter);
                    rv_cart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<CartDetailsResponse> call, Throwable t) {
                // Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                rl_noData.setVisibility(View.VISIBLE);
                ll_amount_view.setVisibility(View.GONE);
            }
        });

    }

    private void totalPrice(List<CartDetailsResponse.Datum> cardDetailsResponse) {
        Total_price = 0;
        for (int i = 0; i < cardDetailsResponse.size(); i++) {
            Total_price += Integer.parseInt(cardDetailsResponse.get(i).getPrice());
        }
        tpp = String.valueOf(Total_price);
        tv_subtotal.setText(tpp);
    }

    private void addOrRemoveItem(String token,int id, String price, int qty) {
        progressDialog.show();
        Call<AddRemoveResponse> addAddress = ApiService.apiHolders().updatedCart(token, id, price, qty);
        addAddress.enqueue(new Callback<AddRemoveResponse>() {
            @Override
            public void onResponse(Call<AddRemoveResponse> call, Response<AddRemoveResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    //  Toast.makeText(MyCart.this,"Added Item",Toast.LENGTH_SHORT).show();
//                    Snackbar errorBar;
//                    errorBar = Snackbar.make(rl_cart, "Added", Snackbar.LENGTH_LONG);
//                    errorBar.setTextColor(getResources().getColor(R.color.white));
//                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
//                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
//                    errorBar.show();
                    if (BlankId.equals(loginPref.getString("device_id", ""))) {
                        getCartDetails(deviceId);
                    }else {
                        getCartDetails(token);
                    }

                }
            }

            @Override
            public void onFailure(Call<AddRemoveResponse> call, Throwable t) {
                // Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_cart, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }

    private void deleteItem(int cartId,String token) {
        progressDialog.show();
        Call<CartItemDeleteResponse> addAddress = ApiService.apiHolders().deleteCartItem(cartId, token);
        addAddress.enqueue(new Callback<CartItemDeleteResponse>() {
            @Override
            public void onResponse(Call<CartItemDeleteResponse> call, Response<CartItemDeleteResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                   // Toast.makeText(MyCart.this, "Deleted Item", Toast.LENGTH_SHORT).show();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_cart, "Deleted Item", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                    if (BlankId.equals(loginPref.getString("device_id", ""))) {
                        getCartDetails(deviceId);
                    }else {
                        getCartDetails(token);
                    }
                }
            }

            @Override
            public void onFailure(Call<CartItemDeleteResponse> call, Throwable t) {
                //Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_cart, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            getCartDetails(deviceId);
        }else {
            getCartDetails(token);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent intent = new Intent(MyCart.this,MainActivity.class);
       startActivity(intent);

    }
}