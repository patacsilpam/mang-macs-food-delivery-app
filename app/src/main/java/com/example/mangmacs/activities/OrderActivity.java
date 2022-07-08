package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.OrderNowLaterAdapter;
import com.example.mangmacs.adapter.PickUpAdapter;
import com.example.mangmacs.fragment.orderLater;
import com.google.android.material.tabs.TabLayout;

public class OrderActivity extends AppCompatActivity {
    private TabLayout bookTablayout;
    private ViewPager2 viewPager;
    OrderNowLaterAdapter orderNowLaterAdapter;
    private TextView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        bookTablayout = findViewById(R.id.bookTabLayout);
        viewPager = findViewById(R.id.viewPager);
        arrowBack = findViewById(R.id.arrow_back);
        //fragement manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        orderNowLaterAdapter = new OrderNowLaterAdapter(fragmentManager,getLifecycle());
        viewPager.setAdapter(orderNowLaterAdapter);
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
        //arrow back button
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderActivity.this,OrderModeActivity.class));
            }
        });
    }
    private void ShowViewPager() {
        bookTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
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

    private void SelectedTab() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bookTablayout.selectTab(bookTablayout.getTabAt(position));
            }
        });
    }
    private void SetMargin() {
        for (int i = 0; i < bookTablayout.getTabCount() - 1; i++) {
            View tab = ((ViewGroup) bookTablayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
    }
}