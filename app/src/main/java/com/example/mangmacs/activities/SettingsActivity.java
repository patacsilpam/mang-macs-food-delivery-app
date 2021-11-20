package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mangmacs.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void btnMyAccount(View view) {
        startActivity(new Intent(SettingsActivity.this, MyAccountActivity.class));
    }

    public void btnAddress(View view) {
        startActivity(new Intent(SettingsActivity.this, MyAddressActivity.class));
    }
}