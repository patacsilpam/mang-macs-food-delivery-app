package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mangmacs.R;

public class ConfirmReservationActivity extends AppCompatActivity {
    private Button btnbackhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);
        btnbackhome = findViewById(R.id.btnBackhome);
        btnbackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmReservationActivity.this, home_activity.class));
            }
        });
    }
}