package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
   @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreference.getSharedPreference(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(SplashScreenActivity.this,home_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            progressDialog.dismiss();
        }
        else{
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
    }
}