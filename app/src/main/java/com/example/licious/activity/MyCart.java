package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.licious.response.CartDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCart extends AppCompatActivity {

    TextView txt_change,btn_continue;
    CardView btn_proceed;
    RecyclerView rv_cart;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;
    int id;
    List<CartDetailsResponse.Datum> cardDetailsResponse;
    MyCartAdapter myCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        rv_cart = findViewById(R.id.rv_cart);
        btn_proceed = findViewById(R.id.btn_proceed);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            int product_id = bundle.getInt("product_id");
        }

//        txt_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MyCart.this, Address.class));
//            }
//        });
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

        Call<CartDetailsResponse> addAddress = ApiService.apiHolders().getCartDetails(id,token);
        addAddress.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(Call<CartDetailsResponse> call, Response<CartDetailsResponse> response) {
                if (response.isSuccessful()) {
                    myCartAdapter = new MyCartAdapter(getApplicationContext(), cardDetailsResponse, new DeleteListener() {
                        @Override
                        public void onItemClickedDelete(CartDetailsResponse.Datum item, int position, int type) {
                            Toast.makeText(MyCart.this,"Deleted",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onItemClickedAdditem(CartDetailsResponse.Datum item, int position, int type) {
                            Toast.makeText(MyCart.this,"ADDED",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onItemClickedRemoveItem(CartDetailsResponse.Datum item, int position, int type) {
                            Toast.makeText(MyCart.this,"REMOVE",Toast.LENGTH_SHORT).show();
                        }
                    });
                    rv_cart.setAdapter(myCartAdapter);
                    rv_cart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<CartDetailsResponse> call, Throwable t) {
                Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}