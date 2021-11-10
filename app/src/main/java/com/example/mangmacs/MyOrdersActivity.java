package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MyOrdersActivity extends AppCompatActivity {
    private TabLayout ordersTablayout;
    private ViewPager2 viewPager;
    OrdersAdapter ordersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ordersTablayout = findViewById(R.id.ordersTabLayout);
        viewPager = findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ordersAdapter = new OrdersAdapter(fragmentManager,getLifecycle());
        viewPager.setAdapter(ordersAdapter);
        ordersTablayout.addTab(ordersTablayout.newTab().setText("Current Orders"));
        ordersTablayout.addTab(ordersTablayout.newTab().setText("Previous Orders"));
        ordersTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                ordersTablayout.selectTab(ordersTablayout.getTabAt(position));
            }
        });
    }
}