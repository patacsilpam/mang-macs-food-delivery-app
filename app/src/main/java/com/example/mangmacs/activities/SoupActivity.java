package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.SoupAdapter;
import com.example.mangmacs.model.SoupListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoupActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SoupListModel> pancitList;
    private ApiInterface apiInterface;
    private SoupAdapter soupAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soup);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowPancitLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SoupActivity.this,home_activity.class));
            }
        });
    }

    private void ShowPancitLists() {
        //call pizza list model
        Call<List<SoupListModel>> call= apiInterface.getPancit();
        call.enqueue(new Callback<List<SoupListModel>>() {
            @Override
            public void onResponse(Call<List<SoupListModel>> call, Response<List<SoupListModel>> response) {
                pancitList = response.body();
                soupAdapter = new SoupAdapter(SoupActivity.this,pancitList);
                recyclerView.setAdapter(soupAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<SoupListModel>> call, Throwable t) {

            }
        });
    }

    public void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<SoupListModel>> call= apiInterface.getPancit();
                call.enqueue(new Callback<List<SoupListModel>>() {
                    @Override
                    public void onResponse(Call<List<SoupListModel>> call, Response<List<SoupListModel>> response) {
                        pancitList = response.body();
                        soupAdapter = new SoupAdapter(SoupActivity.this,pancitList);
                        recyclerView.setAdapter(soupAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<SoupListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}