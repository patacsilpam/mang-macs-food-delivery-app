package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.OrdersAdapter;
import com.example.mangmacs.R;
import com.google.android.material.tabs.TabLayout;

public class MyOrdersActivity extends AppCompatActivity {
    private TabLayout ordersTablayout;
    private ViewPager2 viewPager;
    private OrdersAdapter ordersAdapter;
    private TextView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ordersTablayout = findViewById(R.id.ordersTabLayout);
        viewPager = findViewById(R.id.viewPager);
        arrowBack = findViewById(R.id.arrow_back);
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
        Back();
    }
    private void Back(){
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccountActivity.class));
            }
        });
    }
}