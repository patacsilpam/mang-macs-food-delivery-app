package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.DimsumAdapter;
import com.example.mangmacs.model.DimsumListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DimsumActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DimsumListModel> dimsumList;
    private ApiInterface apiInterface;
    private DimsumAdapter dimsumAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimsum);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowDimsumLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DimsumActivity.this,home_activity.class));
            }
        });
    }

    private void ShowDimsumLists() {
        Call<List<DimsumListModel>> call= apiInterface.getDimsum();
        call.enqueue(new Callback<List<DimsumListModel>>() {
            @Override
            public void onResponse(Call<List<DimsumListModel>> call, Response<List<DimsumListModel>> response) {
                dimsumList = response.body();
                dimsumAdapter = new DimsumAdapter(DimsumActivity.this,dimsumList);
                recyclerView.setAdapter(dimsumAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<DimsumListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<DimsumListModel>> call= apiInterface.getDimsum();
                call.enqueue(new Callback<List<DimsumListModel>>() {
                    @Override
                    public void onResponse(Call<List<DimsumListModel>> call, Response<List<DimsumListModel>> response) {
                        dimsumList = response.body();
                        dimsumAdapter = new DimsumAdapter(DimsumActivity.this,dimsumList);
                        recyclerView.setAdapter(dimsumAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<DimsumListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}