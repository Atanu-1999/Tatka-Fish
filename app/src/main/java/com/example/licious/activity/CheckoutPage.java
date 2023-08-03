package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckoutPage extends AppCompatActivity {

    LinearLayout txt_slot;
    private BottomSheetDialog bottomSheetDialog;
    String today,tomorrow;
    ImageView back;
    CardView btn_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);
        btn_coupon = findViewById(R.id.btn_coupon);
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
        txt_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(CheckoutPage.this,R.style.BottomSheetTheme);
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
                startActivity(new Intent(CheckoutPage.this, Apply_Coupon.class));
            }
        });
    }
}