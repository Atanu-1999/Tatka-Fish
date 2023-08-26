package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.licious.MainActivity;
import com.example.licious.R;

public class SplashScreen extends AppCompatActivity {
    private TextView myTextView;
    private Handler handler;
    private int charIndex;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(),R.color.red));

        myTextView = findViewById(R.id.myTextView);
        handler = new Handler();
        charIndex = 0;

        animateText();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token==null){
                    startActivity(new Intent(SplashScreen.this, OnboardScreen.class));
                }
                else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }

            }
        },3000);
    }
    private void animateText() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String fullText = "TATKA FISH";
                String partialText = fullText.substring(0, charIndex);
                myTextView.setText(partialText);

                if (charIndex < fullText.length()) {
                    charIndex++;
                    animateText();
                }
            }
        }, 100);
    }
}