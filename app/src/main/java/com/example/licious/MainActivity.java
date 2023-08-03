package com.example.licious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.licious.authentication.Check_Internet;
import com.example.licious.fragment.Account;
import com.example.licious.fragment.Categories;
import com.example.licious.fragment.Home;
import com.example.licious.fragment.Search;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    private MeowBottomNavigation navigation;
    private Dialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("ss","ss");
        /*network Connection Check*/
        if(!Check_Internet.isInternetAvailable(MainActivity.this)) {
            noInternetDialog = new Dialog(this);
            noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            noInternetDialog.setContentView(R.layout.no_internet_layout);
            noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            noInternetDialog.setCancelable(false);

            TextView retryButton = noInternetDialog.findViewById(R.id.retry_button);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Check_Internet.isInternetAvailable(MainActivity.this)) {
                        noInternetDialog.dismiss();
                    }
                }
            });
            noInternetDialog.show();
            noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        else {

        }
        navigation = findViewById(R.id.navigation);
        navigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        navigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_category_24));
        navigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_search_24));
        navigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_perm_identity_24));

        navigation.show(1,true);
        replace(new Home());
        navigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new Home());
                        break;
                    case 2:
                        replace(new Categories());
                        break;
                    case 3:
                        replace(new Search());
                        break;
                    case 4:
                        replace(new Account());
                        break;
                }
                return null;
            }
        });
    }
    private void replace(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

}