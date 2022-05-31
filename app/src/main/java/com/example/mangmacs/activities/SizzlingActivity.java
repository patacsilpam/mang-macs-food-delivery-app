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
import com.example.mangmacs.adapter.SizzlingAdapter;
import com.example.mangmacs.model.SizzlingListModel;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SizzlingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SizzlingListModel> seafoodsList;
    private ApiInterface apiInterface;
    private SizzlingAdapter sizzlingAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sizzling);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowSeafoodsLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SizzlingActivity.this,home_activity.class));
            }
        });
    }

    private void ShowSeafoodsLists() {
        Call<List<SizzlingListModel>> call= apiInterface.getSeafoods();
        call.enqueue(new Callback<List<SizzlingListModel>>() {
            @Override
            public void onResponse(Call<List<SizzlingListModel>> call, Response<List<SizzlingListModel>> response) {
                seafoodsList = response.body();
                sizzlingAdapter = new SizzlingAdapter(SizzlingActivity.this,seafoodsList);
                recyclerView.setAdapter(sizzlingAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<SizzlingListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<SizzlingListModel>> call= apiInterface.getSeafoods();
                call.enqueue(new Callback<List<SizzlingListModel>>() {
                    @Override
                    public void onResponse(Call<List<SizzlingListModel>> call, Response<List<SizzlingListModel>> response) {
                        seafoodsList = response.body();
                        sizzlingAdapter = new SizzlingAdapter(SizzlingActivity.this,seafoodsList);
                        recyclerView.setAdapter(sizzlingAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<SizzlingListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}