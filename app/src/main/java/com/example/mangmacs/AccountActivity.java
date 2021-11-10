package com.example.mangmacs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AccountActivity extends AppCompatActivity {
    private TextView logout,myACccount,changePassword,savedAddress,myOrders,myBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        logout = findViewById(R.id.logout);
        myACccount = findViewById(R.id.myAccount);
        changePassword = findViewById(R.id.changePassword);
        savedAddress = findViewById(R.id.savedAddress);
        myOrders = findViewById(R.id.myOrders);
        myBook = findViewById(R.id.myBook);
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservation:
                        startActivity(new Intent(getApplicationContext(),ReservationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(),MenuActivty.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        return true;
                }
                return true;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPreference.getSharedPreference(getApplicationContext()).logout();
            }
        });
        myACccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,MyAccountActivity.class));
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,ChangePasswordActivity.class));
            }
        });
        savedAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,MyAddressActivity.class));
            }
        });
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,MyOrdersActivity.class));
            }
        });
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,MyReservationActivity.class));
            }
        });
    }

}