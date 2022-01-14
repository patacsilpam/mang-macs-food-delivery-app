package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.PickUpAdapter;
import com.example.mangmacs.adapter.ReservationAdapter;
import com.google.android.material.tabs.TabLayout;

public class PickUpActivity extends AppCompatActivity {
    private TabLayout bookTablayout;
    private ViewPager2 viewPager;
    PickUpAdapter pickUpAdapter;
    private TextView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        bookTablayout = findViewById(R.id.bookTabLayout);
        viewPager = findViewById(R.id.viewPager);
        arrowBack = findViewById(R.id.arrow_back);
        //fragement manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        pickUpAdapter = new PickUpAdapter(fragmentManager,getLifecycle());
        viewPager.setAdapter(pickUpAdapter);
        bookTablayout.addTab(bookTablayout.newTab().setText("Now"));
        bookTablayout.addTab(bookTablayout.newTab().setText("Later"));
        bookTablayout.getTabAt(0).setIcon(R.drawable.order_now);
        bookTablayout.getTabAt(1).setIcon(R.drawable.order_later);
        SelectedTab();
        ShowViewPager();
        SetMargin();
        Back();
    }

    private void Back() {
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickUpActivity.this,OrderModeActivity.class));
            }
        });
    }

    private void SetMargin() {
        for(int i=0; i < bookTablayout.getTabCount() - 1; i++) {
            View tab = ((ViewGroup) bookTablayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
    }

    private void ShowViewPager() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bookTablayout.selectTab(bookTablayout.getTabAt(position));
            }
        });
    }

    private void SelectedTab() {
        bookTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}