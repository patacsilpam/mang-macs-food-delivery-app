package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.PancitAdapter;
import com.example.mangmacs.model.PancitListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PancitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PancitListModel> pancitList;
    private ApiInterface apiInterface;
    private PancitAdapter pancitAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pancit);
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
                startActivity(new Intent(PancitActivity.this,home_activity.class));
            }
        });
    }

    private void ShowPancitLists() {
        //call pizza list model
        Call<List<PancitListModel>> call= apiInterface.getPancit();
        call.enqueue(new Callback<List<PancitListModel>>() {
            @Override
            public void onResponse(Call<List<PancitListModel>> call, Response<List<PancitListModel>> response) {
                pancitList = response.body();
                pancitAdapter = new PancitAdapter(PancitActivity.this,pancitList);
                recyclerView.setAdapter(pancitAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<PancitListModel>> call, Throwable t) {

            }
        });
    }

    public void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<PancitListModel>> call= apiInterface.getPancit();
                call.enqueue(new Callback<List<PancitListModel>>() {
                    @Override
                    public void onResponse(Call<List<PancitListModel>> call, Response<List<PancitListModel>> response) {
                        pancitList = response.body();
                        pancitAdapter = new PancitAdapter(PancitActivity.this,pancitList);
                        recyclerView.setAdapter(pancitAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<PancitListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}