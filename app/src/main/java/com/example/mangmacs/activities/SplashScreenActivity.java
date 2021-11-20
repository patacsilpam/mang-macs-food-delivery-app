package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mangmacs.ForgotPasswordActivity;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreference.getSharedPreference(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(SplashScreenActivity.this, ForgotPasswordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else{
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
    }
}