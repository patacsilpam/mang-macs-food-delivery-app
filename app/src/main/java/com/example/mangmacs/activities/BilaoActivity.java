package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.adapter.BilaoAdapter;
import com.example.mangmacs.model.PancitBilaoListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilaoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PancitBilaoListModel> pancitBilaoList;
    private ApiInterface apiInterface;
    private BilaoAdapter bilaoAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyProduct;
    private int countProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilao);
        btnArrowBack = findViewById(R.id.arrow_back);
        emptyProduct = findViewById(R.id.emptyProduct);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowBilaoLists();
        Back();
    }

    private void ShowBilaoLists() {
        emptyProduct.setVisibility(View.GONE);
        Call<List<PancitBilaoListModel>> call= apiInterface.getPancitBilao();
        call.enqueue(new Callback<List<PancitBilaoListModel>>() {
            @Override
            public void onResponse(Call<List<PancitBilaoListModel>> call, Response<List<PancitBilaoListModel>> response) {
                pancitBilaoList = response.body();
                bilaoAdapter = new BilaoAdapter(BilaoActivity.this,pancitBilaoList);
                recyclerView.setAdapter(bilaoAdapter);
                countProduct = bilaoAdapter.getItemCount();
                if(countProduct == 0){
                    emptyProduct.setVisibility(View.VISIBLE);
                }
                else{
                    emptyProduct.setVisibility(View.GONE);
                }
                refresh();
            }
            @Override
            public void onFailure(Call<List<PancitBilaoListModel>> call, Throwable t) {
            }
        });
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BilaoActivity.this,home_activity.class));
            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<PancitBilaoListModel>> call= apiInterface.getPancitBilao();
                call.enqueue(new Callback<List<PancitBilaoListModel>>() {
                    @Override
                    public void onResponse(Call<List<PancitBilaoListModel>> call, Response<List<PancitBilaoListModel>> response) {
                        pancitBilaoList = response.body();
                        bilaoAdapter = new BilaoAdapter(BilaoActivity.this,pancitBilaoList);
                        recyclerView.setAdapter(bilaoAdapter);
                        countProduct = bilaoAdapter.getItemCount();
                        if(countProduct == 0){
                            emptyProduct.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyProduct.setVisibility(View.GONE);
                        }
                        refresh();
                    }
                    @Override
                    public void onFailure(Call<List<PancitBilaoListModel>> call, Throwable t) {
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}