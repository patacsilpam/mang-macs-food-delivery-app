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
import com.example.mangmacs.adapter.AppetizerAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppetizerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ProductListModel> riceMealList;
    private ApiInterface apiInterface;
    private AppetizerAdapter appetizerAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyProduct;
    private int countProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetizer);
        btnArrowBack = findViewById(R.id.arrow_back);
        emptyProduct = findViewById(R.id.emptyProduct);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowRiceMealLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppetizerActivity.this, home_activity.class));
            }
        });
    }

    private void ShowRiceMealLists() {
        Call<List<ProductListModel>> call= apiInterface.getAppetizer();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                riceMealList = response.body();
                appetizerAdapter = new AppetizerAdapter(AppetizerActivity.this,riceMealList);
                recyclerView.setAdapter(appetizerAdapter);
                countProduct = appetizerAdapter.getItemCount();
                if(countProduct == 0){
                    emptyProduct.setVisibility(View.VISIBLE);
                }
                else{
                    emptyProduct.setVisibility(View.GONE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });

    }
    public void refresh(){
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<ProductListModel>> call= apiInterface.getAppetizer();
                call.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        riceMealList = response.body();
                        appetizerAdapter = new AppetizerAdapter(AppetizerActivity.this,riceMealList);
                        recyclerView.setAdapter(appetizerAdapter);
                        countProduct = appetizerAdapter.getItemCount();
                        if(countProduct == 0){
                            emptyProduct.setVisibility(View.VISIBLE);
                        }
                        else{
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