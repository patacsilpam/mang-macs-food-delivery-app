package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.OrdersAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class Bottom_Order_Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TabLayout ordersTablayout;
    private ViewPager2 viewPager;
    private OrdersAdapter ordersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_order);
        bottomNavigationView =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.order);
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
        BottomNav();
    }
    private void BottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), MenuActivty.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.reservation:
                        startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        return true;
                }
                return true;
            }
        });
    }
}