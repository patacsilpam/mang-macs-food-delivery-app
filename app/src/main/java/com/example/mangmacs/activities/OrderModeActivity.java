package com.example.mangmacs.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.fragment.orderLater;

public class OrderModeActivity extends AppCompatActivity {
    private CardView dineIn,pickUp,delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_mode);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        //dineIn = findViewById(R.id.dineIn);
        pickUp = findViewById(R.id.pickUp);
        delivery = findViewById(R.id.delivery);
        //DineIn();
        PickUp();
        Delivery();
    }
    /*private void DineIn(){
        dineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderModeActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });
    }*/
    private void PickUp(){
        pickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderModeActivity.this, PickUpActivity.class);
                startActivity(intent);
            }
        });

    }
    private void Delivery(){
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderModeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}