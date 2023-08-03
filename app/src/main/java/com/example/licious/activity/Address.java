package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Address extends AppCompatActivity {

    ImageView back;
    TextView txt_add;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        txt_add = findViewById(R.id.txt_add);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(Address.this,R.style.BottomSheetTheme);
                View view1 = LayoutInflater.from(Address.this).inflate(R.layout.address_layout,
                        (LinearLayout) findViewById(R.id.container));
                TextView btn_save = (TextView)view1.findViewById(R.id.btn_save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
            }
        });
    }
}