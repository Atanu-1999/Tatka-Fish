package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.licious.MainActivity;
import com.example.licious.R;

public class LoginPage extends AppCompatActivity {

    TextView btn_otp,myTextView,btn_register,btn_submit;
    private Handler handler;
    private int charIndex;
    EditText txt_phone;
    RelativeLayout login_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(),R.color.red));

       /* *//*Initialize*//*
        txt_phone = findViewById(R.id.txt_phone);
        myTextView = findViewById(R.id.myTextView);
        btn_submit = findViewById(R.id.btn_submit);
        login_layout = findViewById(R.id.login_layout);
        myTextView = findViewById(R.id.myTextView);
        btn_register = findViewById(R.id.btn_register);
        handler = new Handler();
        *//*Function Call*//*
        animateText();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, Registerpage.class));
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, MainActivity.class));
            }
        });*/
    }

  /*  private void animateText() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String fullText = "WELCOME TO TATKA FISH";
                String partialText = fullText.substring(0, charIndex);
                myTextView.setText(partialText);

                if (charIndex < fullText.length()) {
                    charIndex++;
                    animateText();
                }
            }
        }, 100);
    }*/
}