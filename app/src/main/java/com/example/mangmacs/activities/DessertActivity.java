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
import com.example.mangmacs.model.DessertListModel;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DessertActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DessertListModel> soupList;
    private ApiInterface apiInterface;
    private DessertAdapter dessertAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);
        btnArrowBack = findViewById(R.id.arrow_back);
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
        Call<List<DessertListModel>> call= apiInterface.getSoup();
        call.enqueue(new Callback<List<DessertListModel>>() {
            @Override
            public void onResponse(Call<List<DessertListModel>> call, Response<List<DessertListModel>> response) {
                soupList = response.body();
                dessertAdapter = new DessertAdapter(DessertActivity.this,soupList);
                recyclerView.setAdapter(dessertAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<DessertListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<DessertListModel>> call= apiInterface.getSoup();
                call.enqueue(new Callback<List<DessertListModel>>() {
                    @Override
                    public void onResponse(Call<List<DessertListModel>> call, Response<List<DessertListModel>> response) {
                        soupList = response.body();
                        dessertAdapter = new DessertAdapter(DessertActivity.this,soupList);
                        recyclerView.setAdapter(dessertAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<DessertListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}