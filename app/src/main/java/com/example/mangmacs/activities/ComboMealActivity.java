package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.BilaoAdapter;
import com.example.mangmacs.adapter.ComboMealAdapter;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.ComboMealListModel;
import com.example.mangmacs.model.PancitBilaoListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboMealActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ComboMealListModel> comboMealList;
    private ApiInterface apiInterface;
    private ComboMealAdapter comboMealAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_meal);
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
                startActivity(new Intent(ComboMealActivity.this,home_activity.class));
            }
        });
    }

    private void ShowComboMealLists() {
        Call<List<ComboMealListModel>> call= apiInterface.getComboMeal();
        call.enqueue(new Callback<List<ComboMealListModel>>() {
            @Override
            public void onResponse(Call<List<ComboMealListModel>> call, Response<List<ComboMealListModel>> response) {
                comboMealList = response.body();
                comboMealAdapter = new ComboMealAdapter(ComboMealActivity.this,comboMealList);
                recyclerView.setAdapter(comboMealAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<ComboMealListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<ComboMealListModel>> call= apiInterface.getComboMeal();
                call.enqueue(new Callback<List<ComboMealListModel>>() {
                    @Override
                    public void onResponse(Call<List<ComboMealListModel>> call, Response<List<ComboMealListModel>> response) {
                        comboMealList = response.body();
                        comboMealAdapter = new ComboMealAdapter(ComboMealActivity.this,comboMealList);
                        recyclerView.setAdapter(comboMealAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ComboMealListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}