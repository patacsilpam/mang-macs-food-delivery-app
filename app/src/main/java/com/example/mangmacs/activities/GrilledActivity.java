package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.GrilledAdapter;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.GrilledListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrilledActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<GrilledListModel> comboMealList;
    private ApiInterface apiInterface;
    private GrilledAdapter grilledAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grilled);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call combo meal list model
        ShowComboMealLists();
        Back();
    }

    private void Back() {
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GrilledActivity.this,home_activity.class));
            }
        });
    }

    private void ShowComboMealLists() {
        Call<List<GrilledListModel>> call= apiInterface.getComboMeal();
        call.enqueue(new Callback<List<GrilledListModel>>() {
            @Override
            public void onResponse(Call<List<GrilledListModel>> call, Response<List<GrilledListModel>> response) {
                comboMealList = response.body();
                grilledAdapter = new GrilledAdapter(GrilledActivity.this,comboMealList);
                recyclerView.setAdapter(grilledAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<GrilledListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<GrilledListModel>> call= apiInterface.getComboMeal();
                call.enqueue(new Callback<List<GrilledListModel>>() {
                    @Override
                    public void onResponse(Call<List<GrilledListModel>> call, Response<List<GrilledListModel>> response) {
                        comboMealList = response.body();
                        grilledAdapter = new GrilledAdapter(GrilledActivity.this,comboMealList);
                        recyclerView.setAdapter(grilledAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<GrilledListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}