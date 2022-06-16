package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AccountActivity extends AppCompatActivity {
    private TextView logout,myACccount,changePassword,savedAddress,myOrders,myBook,initial,customerName,email;
    private int STORAGE_PERMISSION_CODE = 100;
    private AlertDialog.Builder alertDialog;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        logout = findViewById(R.id.logout);
        myACccount = findViewById(R.id.myAccount);
        changePassword = findViewById(R.id.changePassword);
        savedAddress = findViewById(R.id.savedAddress);
        initial = findViewById(R.id.initials);
        customerName = findViewById(R.id.customerName);
        email = findViewById(R.id.email);
        myOrders = findViewById(R.id.myOrders);
        myBook = findViewById(R.id.myBook);
        String fname = SharedPreference.getSharedPreference(this).setFname();
        String lname =  SharedPreference.getSharedPreference(this).setLname();
        String emailAddress =  SharedPreference.getSharedPreference(this).setEmail();
        String first = String.valueOf(fname.charAt(0));
        String last = String.valueOf(lname.charAt(0));
        String initials = first.concat(last);
        String firstLast = fname.concat(" ").concat(lname);
        initial.setText(initials.toUpperCase());
        customerName.setText(firstLast);
        email.setText(emailAddress);
        alertDialog = new AlertDialog.Builder(this);
        bottomNavigationView =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.account);
        BottomNav();
        LogOut();
        MyAccount();
        ChangePassword();
        SavedAddress();
        MyOrders();
        MyBook();
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
                        return true;
                    case R.id.notif:
                        startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
    }

    private void MyBook() {
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MyReservationActivity.class));
            }
        });
    }

    private void MyOrders() {
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MyOrdersActivity.class));
            }
        });
    }

    private void SavedAddress() {
        savedAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MyAddressActivity.class));
            }
        });
    }

    private void ChangePassword() {
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ChangePasswordActivity.class));
            }
        });
    }

    private void MyAccount() {
        myACccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MyAccountActivity.class));
            }
        });
    }

    private void LogOut() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                SharedPreference.getSharedPreference(getApplicationContext()).logout();
                                Intent intent = new Intent(AccountActivity.this,LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = alertDialog.create();
                alert.setTitle("Logout");
                alert.show();
            }
        });
    }
}