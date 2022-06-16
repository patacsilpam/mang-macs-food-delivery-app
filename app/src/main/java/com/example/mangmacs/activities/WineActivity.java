package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.BeerBucketAdapter;
import com.example.mangmacs.adapter.BeveragesAdapter;
import com.example.mangmacs.adapter.WineAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WineActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBeerBucket,recyclerViewBeverages,recyclerViewWine;
    private LinearLayout beerBucketLayout, beveragesLayout, wineLayout;
    private List<ProductListModel> wineListModel;
    private ApiInterface apiInterface;
    private BeerBucketAdapter beerBucketAdapter;
    private BeveragesAdapter beveragesAdapter;
    private WineAdapter wineAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int countBeerBucket = 0;
    private int countBeverages = 0;
    private int countWine = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_actiivity);
        btnArrowBack = findViewById(R.id.arrow_back);
        beerBucketLayout = findViewById(R.id.beerBucketLayout);
        beveragesLayout = findViewById(R.id.beveragesLayout);
        wineLayout = findViewById(R.id.wineLayout);
        recyclerViewBeerBucket = findViewById(R.id.recyclerViewBeerBucket);
        recyclerViewBeverages = findViewById(R.id.recyclerViewBeverages);
        recyclerViewWine= findViewById(R.id.recyclerViewWine);
        recyclerViewBeerBucket.setHasFixedSize(true);
        recyclerViewBeverages.setHasFixedSize(true);
        recyclerViewWine.setHasFixedSize(true);
        recyclerViewBeerBucket.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBeverages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWine.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowBeerBucketLists();
        ShowBeveragesLists();
        ShowWineLists();
        Back();
    }
    private void Back() {
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WineActivity.this, home_activity.class));
            }
        });
    }
    private void ShowBeerBucketLists(){
        Call<List<ProductListModel>> call = apiInterface.getBeerBucket();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                wineListModel = response.body();
                beerBucketAdapter = new BeerBucketAdapter(WineActivity.this, wineListModel);
                recyclerViewBeerBucket.setAdapter(beerBucketAdapter);
                countBeerBucket = beerBucketAdapter.getItemCount();
                if(countBeerBucket == 0){
                    beerBucketLayout.setVisibility(View.GONE);
                }
                else{
                    beerBucketLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowBeveragesLists() {
        Call<List<ProductListModel>> call = apiInterface.getBeverages();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                wineListModel = response.body();
                beveragesAdapter = new BeveragesAdapter(WineActivity.this, wineListModel);
                recyclerViewBeverages.setAdapter(beveragesAdapter);
                countBeverages = beveragesAdapter.getItemCount();
                if(countBeverages == 0){
                    beveragesLayout.setVisibility(View.GONE);
                }
                else{
                    beveragesLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowWineLists() {
        Call<List<ProductListModel>> call = apiInterface.getWine();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                wineListModel = response.body();
                wineAdapter = new WineAdapter(WineActivity.this, wineListModel);
                recyclerViewWine.setAdapter(wineAdapter);
                countWine = wineAdapter.getItemCount();
                if(countWine == 0){
                    wineLayout.setVisibility(View.GONE);
                }
                else{
                    wineLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<ProductListModel>> callBeerBucket = apiInterface.getBeerBucket();
                callBeerBucket.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        wineListModel = response.body();
                        beerBucketAdapter = new BeerBucketAdapter(WineActivity.this, wineListModel);
                        recyclerViewBeerBucket.setAdapter(beerBucketAdapter);
                        countBeerBucket = beerBucketAdapter.getItemCount();
                        if(countBeerBucket == 0){
                            beerBucketLayout.setVisibility(View.GONE);
                        }
                        else{
                            beerBucketLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //
                Call<List<ProductListModel>> callBeverages = apiInterface.getBeverages();
                callBeverages.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        wineListModel = response.body();
                        beveragesAdapter = new BeveragesAdapter(WineActivity.this, wineListModel);
                        recyclerViewBeverages.setAdapter(beveragesAdapter);
                        countBeverages = beveragesAdapter.getItemCount();
                        if(countBeverages == 0){
                            beveragesLayout.setVisibility(View.GONE);
                        }
                        else{
                            beveragesLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //
                Call<List<ProductListModel>> callWine = apiInterface.getWine();
                callWine.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        wineListModel = response.body();
                        wineAdapter = new WineAdapter(WineActivity.this, wineListModel);
                        recyclerViewWine.setAdapter(wineAdapter);
                        countWine = wineAdapter.getItemCount();
                        if(countWine == 0){
                            wineLayout.setVisibility(View.GONE);
                        }
                        else{
                            wineLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
            }
        });
    }

}