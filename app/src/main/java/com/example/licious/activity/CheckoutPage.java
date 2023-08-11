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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.CheckOutAdapter;
import com.example.licious.adapter.MyCartAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.DeleteListener;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.CartItemDeleteResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPage extends AppCompatActivity {

    LinearLayout txt_slot;
    private BottomSheetDialog bottomSheetDialog;
    String today, tomorrow;
    ImageView back;
    CardView btn_coupon;
    RecyclerView rv_CheckOutCart;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token,Total;
    int id, coupon_id,totalAmount;
    List<CartDetailsResponse.Datum> cardDetailsResponse;
    CheckOutAdapter checkOutAdapter;
    TextView tv_totalAmount,sub_total,tv_delivery_charge,totalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);
        btn_coupon = findViewById(R.id.btn_coupon);
        rv_CheckOutCart = findViewById(R.id.rv_CheckOutCart);
        tv_totalAmount = findViewById(R.id.tv_totalAmount);
        sub_total = findViewById(R.id.sub_total);
        tv_delivery_charge = findViewById(R.id.tv_delivery_charge);
        totalValue = findViewById(R.id.totalValue);

        /*Current date and tomorrow date pick*/
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrowDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM");
        today = dateFormat.format(currentDate);
        tomorrow = dateFormat.format(tomorrowDate);
        /*initialization*/
        back = findViewById(R.id.back);
        txt_slot = findViewById(R.id.txt_slot);

        //loading
        progressDialog = new ProgressDialog(CheckoutPage.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);


        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            coupon_id = bundle.getInt("coupon_id",0);
            totalAmount = bundle.getInt("total_amount",0);
        }

        //get Data
        getCartDetails();

        txt_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(CheckoutPage.this, R.style.BottomSheetTheme);
                View view1 = LayoutInflater.from(CheckoutPage.this).inflate(R.layout.slot_layout,
                        (LinearLayout) findViewById(R.id.container));
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_tomorrow = view1.findViewById(R.id.txt_tomorrow);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_today = view1.findViewById(R.id.txt_today);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot1 = view1.findViewById(R.id.txt_slot1);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot2 = view1.findViewById(R.id.txt_slot2);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot3 = view1.findViewById(R.id.txt_slot3);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot4 = view1.findViewById(R.id.txt_slot4);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot5 = view1.findViewById(R.id.txt_slot5);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot6 = view1.findViewById(R.id.txt_slot6);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot7 = view1.findViewById(R.id.txt_slot7);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot8 = view1.findViewById(R.id.txt_slot8);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txt_slot9 = view1.findViewById(R.id.txt_slot9);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView btn_cancel = view1.findViewById(R.id.btn_cancel);

                txt_today.setText("Today " + today);
                txt_tomorrow.setText("Tomorrow " + tomorrow);
                txt_today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_tomorrow.setBackground(getDrawable(R.drawable.time_bg));
                        txt_today.setBackground(getDrawable(R.drawable.timeslot_bg));
                        txt_tomorrow.setTextColor(getResources().getColor(R.color.black));
                        txt_today.setTextColor(getResources().getColor(R.color.white));
                    }
                });
                txt_tomorrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_tomorrow.setBackground(getDrawable(R.drawable.timeslot_bg));
                        txt_today.setBackground(getDrawable(R.drawable.time_bg));
                        txt_tomorrow.setTextColor(getResources().getColor(R.color.white));
                        txt_today.setTextColor(getResources().getColor(R.color.black));
                    }
                });
                txt_slot1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.slotselected_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
                    }
                });
                txt_slot9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_slot1.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
                        txt_slot9.setBackground(getDrawable(R.drawable.slotselected_bg));
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("total_amount",totalAmount);
                Intent i = new Intent(CheckoutPage.this,Apply_Coupon.class);
                i.putExtras(bundle);
                startActivity(i);
               // startActivity(new Intent(CheckoutPage.this, Apply_Coupon.class));
            }
        });
    }

    private void getCartDetails() {
        progressDialog.show();
        Call<CartDetailsResponse> addAddress = ApiService.apiHolders().getCartDetails(id, token);
        addAddress.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(Call<CartDetailsResponse> call, Response<CartDetailsResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    cardDetailsResponse = response.body().getData();
                    setTotalAmount(cardDetailsResponse);
                    checkOutAdapter = new CheckOutAdapter(getApplicationContext(), cardDetailsResponse, new DeleteListener() {
                        @Override
                        public void onItemClickedDelete(CartDetailsResponse.Datum item, int position, int type) {
                            int cartId = item.getId();
                            deleteItem(cartId);
                        }

                        @Override
                        public void onItemClickedAdditem(CartDetailsResponse.Datum item, int position, int type) {
                            // No use
                        }

                        @Override
                        public void onItemClickedRemoveItem(CartDetailsResponse.Datum item, int position, int type) {
                            // No use
                        }
                    });
                    rv_CheckOutCart.setAdapter(checkOutAdapter);
                    rv_CheckOutCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<CartDetailsResponse> call, Throwable t) {
                // Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setTotalAmount(List<CartDetailsResponse.Datum> cardDetailsResponse) {
        sub_total.setText("₹" + " " + totalAmount);///subTotal
        int subtotal = totalAmount;
        //String deliveryCharges =tv_delivery_charge.getText().toString();
        int deliveryCharges = 39; // static for testing
        int Total = subtotal + deliveryCharges;
        String T_total = String.valueOf(Total);
        totalValue.setText("₹" + " " + T_total); //set value after delivery charge add
        tv_totalAmount.setText(T_total);//set value final Total
    }

    private void deleteItem(int cartId) {
        progressDialog.show();
        Call<CartItemDeleteResponse> addAddress = ApiService.apiHolders().deleteCartItem(cartId, token);
        addAddress.enqueue(new Callback<CartItemDeleteResponse>() {
            @Override
            public void onResponse(Call<CartItemDeleteResponse> call, Response<CartItemDeleteResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckoutPage.this, "Deleted Item", Toast.LENGTH_SHORT).show();
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