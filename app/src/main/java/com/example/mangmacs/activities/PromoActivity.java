package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.PopularAdapter;
import com.example.mangmacs.adapter.PromoAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.model.PromoListModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoActivity extends AppCompatActivity {
    private List<PromoListModel> promoListModel;
    private PromoAdapter promoAdapter;
    private RecyclerView recyclerViewPromo;
    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;
    private TextView arrowBack;
    private View emptyPromo;
    private int countPromo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.promo);
        progressBar = findViewById(R.id.spin_kit);
        arrowBack = findViewById(R.id.arrow_back);
        emptyPromo = findViewById(R.id.emptyPromo);
        recyclerViewPromo = findViewById(R.id.recyclerViewPromo);
        recyclerViewPromo.setHasFixedSize(true);
        recyclerViewPromo.setLayoutManager(new LinearLayoutManager(this));
        emptyPromo.setVisibility(View.GONE);
        showPromoProducts();
        Back();
        BottomNav();
    }
    private void showPromoProducts(){
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<PromoListModel>> callPromo = apiInterface.getPromos();
        callPromo.enqueue(new Callback<List<PromoListModel>>() {
            @Override
            public void onResponse(Call<List<PromoListModel>> call, Response<List<PromoListModel>> response) {
                progressBar.setVisibility(View.GONE);
                promoListModel = response.body();
                promoAdapter = new PromoAdapter(PromoActivity.this,promoListModel);
                recyclerViewPromo.setAdapter(promoAdapter);
                countPromo = recyclerViewPromo.getAdapter().getItemCount();
                if (countPromo == 0){
                    emptyPromo.setVisibility(View.VISIBLE);
                } else{
                    emptyPromo.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<PromoListModel>> call, Throwable t) {
            }
        });
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
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.promo:
                        return true;
                }
                return true;
            }
        });
    }
    private void Back(){
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PromoActivity.this,home_activity.class));
            }
        });
    }
}