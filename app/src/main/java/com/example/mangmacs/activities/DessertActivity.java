package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.DessertAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DessertActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ProductListModel> soupList;
    private ApiInterface apiInterface;
    private DessertAdapter dessertAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyProduct;
    private int countProduct = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);
        btnArrowBack = findViewById(R.id.arrow_back);
        emptyProduct = findViewById(R.id.emptyProduct);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowSoupLists();
        Back();
    }
    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DessertActivity.this,home_activity.class));
            }
        });
    }
    private void ShowSoupLists() {
        Call<List<ProductListModel>> call= apiInterface.getDessert();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                soupList = response.body();
                dessertAdapter = new DessertAdapter(DessertActivity.this,soupList);
                recyclerView.setAdapter(dessertAdapter);
                countProduct = dessertAdapter.getItemCount();
                if(countProduct == 0){
                    emptyProduct.setVisibility(View.VISIBLE);
                } else{
                    emptyProduct.setVisibility(View.GONE);
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
                Call<List<ProductListModel>> call= apiInterface.getDessert();
                call.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        soupList = response.body();
                        dessertAdapter = new DessertAdapter(DessertActivity.this,soupList);
                        recyclerView.setAdapter(dessertAdapter);
                        countProduct = dessertAdapter.getItemCount();
                        if(countProduct == 0){
                            emptyProduct.setVisibility(View.VISIBLE);
                        } else{
                            emptyProduct.setVisibility(View.GONE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}