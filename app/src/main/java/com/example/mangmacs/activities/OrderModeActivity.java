package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.R;

public class OrderModeActivity extends AppCompatActivity {
    private CardView dineIn,pickUp,delivery;
    private TextView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_mode);
        dineIn = findViewById(R.id.dineIn);
        pickUp = findViewById(R.id.pickUp);
        delivery = findViewById(R.id.delivery);
        arrowBack = findViewById(R.id.arrow_back1);
        DineIn();
        PickUp();
        Delivery();
        Back();
    }

    private void Back() {
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderModeActivity.this,CartActivity.class));
            }
        });

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