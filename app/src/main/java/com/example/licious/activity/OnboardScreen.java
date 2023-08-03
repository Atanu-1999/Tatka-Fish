package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.adapter.OnboardAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.logging.Logger;

public class OnboardScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tvSkip, tvNext;
    private OnboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_screen);

        viewPager = findViewById(R.id.pagerIntroSlider);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tvSkip = findViewById(R.id.tvSkip);
        tvNext = findViewById(R.id.tvNext);
        // init slider pager adapter
        adapter = new OnboardAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // set adapter
        viewPager.setAdapter(adapter);

        // set dot indicators
        tabLayout.setupWithViewPager(viewPager);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((viewPager.getCurrentItem() + 1) < adapter.getCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(OnboardScreen.this, LoginPage.class));
                    finish();
                }
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnboardScreen.this, LoginPage.class));
                finish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == adapter.getCount() - 1) {
                    tvNext.setText(R.string.get_started);
                } else {
                    tvNext.setText(R.string.next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}