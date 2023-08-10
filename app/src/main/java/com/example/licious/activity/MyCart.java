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
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.listener.DeleteListener;
import com.example.licious.R;
import com.example.licious.adapter.MyCartAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.response.AddRemoveResponse;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.CartItemDeleteResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCart extends AppCompatActivity {

    TextView txt_change,btn_continue,tv_add_type,tv_address;
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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        rv_cart = findViewById(R.id.rv_cart);
        btn_proceed = findViewById(R.id.btn_proceed);
        txt_change =findViewById(R.id.txt_change);
        tv_add_type = findViewById(R.id.tv_add_type);
        tv_address = findViewById(R.id.tv_address);
        tv_subtotal = findViewById(R.id.tv_subtotal);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        int add_Id = loginPref.getInt("add_id", 0);
        String adds_one = loginPref.getString("adds_one", null);
        String adds_two = loginPref.getString("adds_two", null);
        String add_type =loginPref.getString("add_type", null);
        String city = loginPref.getString("city", null);

        //for address
        if (add_Id==0) {
            txt_change.setText("Select Address");
        }
        else {
            txt_change.setText("Change");
            tv_add_type.setText(add_type);
            tv_address.setText(adds_one + adds_two + city);
        }



        //loading
        progressDialog = new ProgressDialog(MyCart.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            int product_id = bundle.getInt("product_id");
        }

        txt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, Address.class));
            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, CheckoutPage.class));
            }
        });
//        btn_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MyCart.this, SearchActivity.class));
//            }
//        });

        getCartDetails();
    }

    private void getCartDetails() {
        progressDialog.show();

        Call<CartDetailsResponse> addAddress = ApiService.apiHolders().getCartDetails(id,token);
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
                            Toast.makeText(MyCart.this,"Deleted",Toast.LENGTH_SHORT).show();
                            int cartId= item.getId();
                            deleteItem(cartId);
                        }

                        @Override
                        public void onItemClickedAdditem(CartDetailsResponse.Datum item, int position, int type) {
                           // Toast.makeText(MyCart.this,"ADDED",Toast.LENGTH_SHORT).show();
                            int Id= item.getId();
                            String price = item.getOffer_price();
                            int qty = Integer.parseInt(item.getQty());
                            int qtn = 1+ qty;
                            if (qtn > 1){
                                addOrRemoveItem(Id,price,qtn);
                            }
                            else {
                                Toast.makeText(MyCart.this, "Can not less than 1", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onItemClickedRemoveItem(CartDetailsResponse.Datum item, int position, int type) {
                            //Toast.makeText(MyCart.this,"REMOVE",Toast.LENGTH_SHORT).show();
                            int Id= item.getId();
                            String price = item.getOffer_price();
                            int qty = Integer.parseInt(item.getQty());
                            int qtn = qty - 1;
                            if (qtn >= 1){
                                addOrRemoveItem(Id,price,qtn);
                            }
                            else {
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
            }
        });

    }

    private void totalPrice(List<CartDetailsResponse.Datum> cardDetailsResponse) {
        for (int i =0 ; i< cardDetailsResponse.size();i++){
            tv_subtotal.setText("");
 //           listPrice.add(cardDetailsResponse.get(i).getOffer_price());
            Total_price = Integer.parseInt(cardDetailsResponse.get(i).getPrice());
            tp= tp + Total_price;
        }
 //       return tp;
//        tp= tp + Total_price;
        String tpp = String.valueOf(tp);
        tv_subtotal.setText(tpp);
    }

    private void addOrRemoveItem(int id, String price, int qty) {
        progressDialog.show();
        Call<AddRemoveResponse> addAddress = ApiService.apiHolders().updatedCart(token,id,price,qty);
        addAddress.enqueue(new Callback<AddRemoveResponse>() {
            @Override
            public void onResponse(Call<AddRemoveResponse> call, Response<AddRemoveResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                  //  Toast.makeText(MyCart.this,"Added Item",Toast.LENGTH_SHORT).show();
                    getCartDetails();
                }
            }

            @Override
            public void onFailure(Call<AddRemoveResponse> call, Throwable t) {
               // Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void deleteItem(int cartId) {
        progressDialog.show();
        Call<CartItemDeleteResponse> addAddress = ApiService.apiHolders().deleteCartItem(cartId,token);
        addAddress.enqueue(new Callback<CartItemDeleteResponse>() {
            @Override
            public void onResponse(Call<CartItemDeleteResponse> call, Response<CartItemDeleteResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MyCart.this,"Deleted Item",Toast.LENGTH_SHORT).show();
                    getCartDetails();
                }
            }

            @Override
            public void onFailure(Call<CartItemDeleteResponse> call, Throwable t) {
                //Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}