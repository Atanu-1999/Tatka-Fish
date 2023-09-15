package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.adapter.CheckOutAdapter;
import com.example.licious.adapter.SlotAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.DeleteListener;
import com.example.licious.listener.SlotListener;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.CartItemDeleteResponse;
import com.example.licious.response.CheckOutProccedResponse;
import com.example.licious.response.SlotResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    String token, Total;
    int id, coupon_id, totalAmount;
    List<CartDetailsResponse.Datum> cardDetailsResponse;
    List<SlotResponse.Datum> slotResponse;
    List<SlotResponse.Datum> slotData;
    CheckOutAdapter checkOutAdapter;
    TextView tv_totalAmount, sub_total, tv_delivery_charge, totalValue;
    SlotAdapter slotAdapter;
    RecyclerView rv_slot;
    Boolean flag = false;
    TextView tv_slotTime;
    String SlotTime, delivery_date="";
    int delivery_charge, SlotId=0;
    LinearLayout btn_cart;
    int add_Id;
    int Totals;
    Dialog dialog;
    int coupon_amount,coupon_off_amount;
    TextView tv_coupoun_charge;
    RelativeLayout rl_view;
    String pageType;

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
        tv_slotTime = findViewById(R.id.tv_slotTime);
        btn_cart = findViewById(R.id.btn_cart);
        tv_coupoun_charge = findViewById(R.id.tv_coupoun_charge);
        rl_view = findViewById(R.id.rl_view);

        dialog = new Dialog(CheckoutPage.this);

        /*Current date and tomorrow date pick*/
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrowDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        //address Id
        add_Id = loginPref.getInt("add_id", 0);


        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            coupon_id = bundle.getInt("coupon_id", 0);
            totalAmount = bundle.getInt("total_amount", 0);
            coupon_off_amount = bundle.getInt("coupon_off_amount",0);
            coupon_amount = bundle.getInt("coupon_amount",0);
            pageType = bundle.getString("pageType",null);
        }



        //get Data
        getCartDetails();
        //get slot time
        // getSlotDetails();

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    placedOrder(id, delivery_date, delivery_charge, totalAmount, Totals, SlotId, add_Id,coupon_id, token);
                }
            }
        });


        txt_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(CheckoutPage.this, R.style.BottomSheetTheme);
                View view1 = LayoutInflater.from(CheckoutPage.this).inflate(R.layout.slot_layout,
                        (LinearLayout) findViewById(R.id.container));
                TextView txt_tomorrow = view1.findViewById(R.id.txt_tomorrow);
                TextView txt_today = view1.findViewById(R.id.txt_today);
                rv_slot = view1.findViewById(R.id.rv_slot);
                ImageView btn_cancel = view1.findViewById(R.id.btn_cancel);

                progressDialog.show();
                Call<SlotResponse> slot = ApiService.apiHolders().getTimeSlot(token);
                slot.enqueue(new Callback<SlotResponse>() {
                    @Override
                    public void onResponse(Call<SlotResponse> call, Response<SlotResponse> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            assert response.body() != null;
                            slotResponse = response.body().getData();
                            setTotalAmount();

                            slotAdapter = new SlotAdapter(getApplicationContext(), slotResponse, flag, new SlotListener() {
                                @Override
                                public void onItemClickedSlot(SlotResponse.Datum item, int position, Boolean flag) {
                                    flag = flag;
                                   // Toast.makeText(getApplicationContext(), item.getSlot_name(), Toast.LENGTH_SHORT).show();
                                    Snackbar errorBar;
                                    errorBar = Snackbar.make(rl_view, "Your select slot is "+item.getSlot_name(), Snackbar.LENGTH_LONG);
                                    errorBar.setTextColor(getResources().getColor(R.color.white));
                                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                                    errorBar.show();

                                    SlotTime = item.getSlot_name();
                                    SlotId = item.getId();
                                    delivery_charge = item.getDelivery_charge();
                                    String dc=String.valueOf(delivery_charge);//delivery charge
                                    tv_delivery_charge.setText("₹" + " " +dc);
                                    tv_slotTime.setText(SlotTime);
                                    setTotalAmount();
                                    bottomSheetDialog.dismiss();
                                }
                            });
                            GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                            rv_slot.setLayoutManager(layoutManager);
                            rv_slot.setAdapter(slotAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<SlotResponse> call, Throwable t) {
                        // Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });


//                slotAdapter = new SlotAdapter(getApplicationContext(), slotResponse);
//                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
//                rv_slot.setLayoutManager(layoutManager);
//                rv_slot.setAdapter(slotAdapter);


//                txt_today.setText("Today " + today);
//                txt_tomorrow.setText("Tomorrow " + tomorrow);
                txt_today.setText(today);
                txt_tomorrow.setText(tomorrow);
                txt_today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_tomorrow.setBackground(getDrawable(R.drawable.time_bg));
                        txt_today.setBackground(getDrawable(R.drawable.timeslot_bg));
                        txt_tomorrow.setTextColor(getResources().getColor(R.color.black));
                        txt_today.setTextColor(getResources().getColor(R.color.white));
                        delivery_date = today;
                    }
                });
                txt_tomorrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_tomorrow.setBackground(getDrawable(R.drawable.timeslot_bg));
                        txt_today.setBackground(getDrawable(R.drawable.time_bg));
                        txt_tomorrow.setTextColor(getResources().getColor(R.color.white));
                        txt_today.setTextColor(getResources().getColor(R.color.black));
                        delivery_date = tomorrow;
                    }
                });
//                txt_slot1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txt_slot1.setBackground(getDrawable(R.drawable.slotselected_bg));
//                        txt_slot2.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot3.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot4.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot5.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot6.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot7.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot8.setBackground(getDrawable(R.drawable.time_bg));
//                        txt_slot9.setBackground(getDrawable(R.drawable.time_bg));
//                    }
//                });
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
//                if (pageType.equals("coupon")){
//
//                }else {
                    Intent i = new Intent(CheckoutPage.this, MyCart.class);
                    startActivity(i);
              //  }
            }
        });
        btn_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("total_amount", totalAmount);
                bundle.putInt("delivery_charge",delivery_charge);
                Intent i = new Intent(CheckoutPage.this, Apply_Coupon.class);
                i.putExtras(bundle);
                startActivity(i);
                // startActivity(new Intent(CheckoutPage.this, Apply_Coupon.class));
            }
        });
    }

    private boolean validation() {
        if (delivery_date.equals("")) {
            Toast.makeText(this, "Please select delivery date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (SlotId == 0){
            Toast.makeText(this, "Please select time slot", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void placedOrder(int id, String delivery_date, int delivery_charge, int totalAmount, int Totals, int slotTime, int add_id,int coupon_id, String token) {
        progressDialog.show();
        Call<CheckOutProccedResponse> slot = ApiService.apiHolders().procedToCheckOut(id, totalAmount, delivery_charge, Totals, delivery_date, slotTime, add_id,coupon_id, token);
        slot.enqueue(new Callback<CheckOutProccedResponse>() {
            @Override
            public void onResponse(Call<CheckOutProccedResponse> call, Response<CheckOutProccedResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    //Toast.makeText(CheckoutPage.this, "Success", Toast.LENGTH_SHORT).show();
                    openDailouge("true");
                } else {
                    progressDialog.dismiss();
                    //Toast.makeText(CheckoutPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    openDailouge("false");
                }
            }

            @Override
            public void onFailure(Call<CheckOutProccedResponse> call, Throwable t) {
                //Toast.makeText(CheckoutPage.this, "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                openDailouge("false");
            }
        });
    }

    private void openDailouge(String status) {
//        dialog.setContentView(R.layout.payment_dialouge);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // dialog.setCancelable(false);

        bottomSheetDialog = new BottomSheetDialog(CheckoutPage.this, R.style.BottomSheetTheme);
        View view1 = LayoutInflater.from(CheckoutPage.this).inflate(R.layout.payment_dialouge,
                (LinearLayout) findViewById(R.id.container));

        ImageView iv_tick = view1.findViewById(R.id.iv_tick);
        ImageView iv_msg = view1.findViewById(R.id.iv_msg);
        TextView tv_back = view1.findViewById(R.id.tv_back);

        if (Objects.equals(status, "true")){
            iv_tick.setImageResource(R.drawable.success_tick);
            iv_msg.setImageResource(R.drawable.sucess_message);
        }
        else {
            iv_tick.setImageResource(R.drawable.failtrick);
            iv_msg.setImageResource(R.drawable.failmsg);
        }

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(CheckoutPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(false);
    }

    private void getSlotDetails() {
        progressDialog.show();
        Call<SlotResponse> slot = ApiService.apiHolders().getTimeSlot(token);
        slot.enqueue(new Callback<SlotResponse>() {
            @Override
            public void onResponse(Call<SlotResponse> call, Response<SlotResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    slotResponse = response.body().getData();

//                    slotAdapter = new SlotAdapter(getApplicationContext(), slotResponse);
//                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
//                    rv_slot.setLayoutManager(layoutManager);
//                    rv_slot.setAdapter(slotAdapter);
                }
            }

            @Override
            public void onFailure(Call<SlotResponse> call, Throwable t) {
                // Toast.makeText(MyCart.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void getCartDetails() {
        progressDialog.show();
        Call<CartDetailsResponse> addAddress = ApiService.apiHolders().getCartDetails(token);
        addAddress.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(Call<CartDetailsResponse> call, Response<CartDetailsResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    cardDetailsResponse = response.body().getData();
                    setTotalAmount();
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

    private void setTotalAmount() {
        sub_total.setText("₹" + " " + totalAmount);///subTotal
        int subtotal = totalAmount;
        int deliveryCharges = 0;
        if (delivery_charge != 0) {
            String t_dc = String.valueOf(delivery_charge);
            tv_delivery_charge.setText("₹" + " " + t_dc);// delivery charge
            deliveryCharges = delivery_charge;
        } else {
            tv_delivery_charge.setText("₹" + " " + "0");
        }

        Totals = subtotal + deliveryCharges;
        String T_total = String.valueOf(Totals);

        if (coupon_amount!= 0) {
            if (coupon_amount > subtotal) {
                totalValue.setText("₹" + " " + T_total); //set value after delivery charge add
                tv_totalAmount.setText(T_total);//set value final Total
                tv_coupoun_charge.setText("₹" + " " + "0");
               // Toast.makeText(getApplicationContext(),"less",Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_view, "This coupon is not valid for this product", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            } else {
                int c_amnt = Totals - coupon_off_amount;
                String ff_total = String.valueOf(c_amnt);
                totalValue.setText("₹" + " " + ff_total); //set value after delivery charge add
                tv_totalAmount.setText(ff_total);//set value final Total
                tv_coupoun_charge.setText(String.valueOf("₹" + " " + coupon_off_amount));
                //Toast.makeText(getApplicationContext(),"Greater",Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_view, "Coupon Applied", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        }else {
            totalValue.setText("₹" + " " + T_total); //set value after delivery charge add
            tv_totalAmount.setText(T_total);//set value final Total
            tv_coupoun_charge.setText("₹" + " " + "0");
           // Toast.makeText(getApplicationContext(),"no coupoun",Toast.LENGTH_SHORT).show();
        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (pageType.equals("coupon")){
//
//        }else {
            Intent i = new Intent(CheckoutPage.this, MyCart.class);
            startActivity(i);
       // }
    }
}