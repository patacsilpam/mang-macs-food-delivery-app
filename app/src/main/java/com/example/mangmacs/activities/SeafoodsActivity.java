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
import com.example.mangmacs.adapter.SeafoodsAdapter;
import com.example.mangmacs.model.SeafoodsListModel;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeafoodsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SeafoodsListModel> seafoodsList;
    private ApiInterface apiInterface;
    private SeafoodsAdapter seafoodsAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seafoods);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        //call seafoods list
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<SeafoodsListModel>> call= apiInterface.getSeafoods();
        call.enqueue(new Callback<List<SeafoodsListModel>>() {
            @Override
            public void onResponse(Call<List<SeafoodsListModel>> call, Response<List<SeafoodsListModel>> response) {
                seafoodsList = response.body();
                seafoodsAdapter = new SeafoodsAdapter(SeafoodsActivity.this,seafoodsList);
                recyclerView.setAdapter(seafoodsAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<SeafoodsListModel>> call, Throwable t) {

            }
        });
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SeafoodsActivity.this,home_activity.class));
            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<SeafoodsListModel>> call= apiInterface.getSeafoods();
                call.enqueue(new Callback<List<SeafoodsListModel>>() {
                    @Override
                    public void onResponse(Call<List<SeafoodsListModel>> call, Response<List<SeafoodsListModel>> response) {
                        seafoodsList = response.body();
                        seafoodsAdapter = new SeafoodsAdapter(SeafoodsActivity.this,seafoodsList);
                        recyclerView.setAdapter(seafoodsAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<SeafoodsListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}