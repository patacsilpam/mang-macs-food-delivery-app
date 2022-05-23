package com.example.mangmacs.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;

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
        dineIn = findViewById(R.id.dineIn);
        pickUp = findViewById(R.id.pickUp);
        delivery = findViewById(R.id.delivery);
        DineIn();
        PickUp();
        Delivery();
    }
    private void DineIn() {
        dineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderModeActivity.this,DineInActivity.class));
            }
        });
    }
    private void PickUp(){
        pickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderModeActivity.this, PickUpActivity.class));
            }
        });

    }
    private void Delivery(){
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderModeActivity.this,OrderActivity.class));
            }
        });
    }
}