package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MyReservationActivity extends AppCompatActivity {
    private TabLayout bookTablayout;
    private ViewPager2 viewPager;
    ReservationAdapter reservationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        bookTablayout = findViewById(R.id.bookTabLayout);
        viewPager = findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        reservationAdapter = new ReservationAdapter(fragmentManager,getLifecycle());
        viewPager.setAdapter(reservationAdapter);
        bookTablayout.addTab(bookTablayout.newTab().setText("Current Booking"));
        bookTablayout.addTab(bookTablayout.newTab().setText("Previous Booking"));
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
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bookTablayout.selectTab(bookTablayout.getTabAt(position));
            }
        });
    }
}