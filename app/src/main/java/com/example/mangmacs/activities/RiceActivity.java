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
import com.example.mangmacs.adapter.RiceAdapter;
import com.example.mangmacs.model.RicecupListModel;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RicecupListModel> ricecupList;
    private ApiInterface apiInterface;
    private RiceAdapter riceAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowRiceLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RiceActivity.this,home_activity.class));
            }
        });
    }

    private void ShowRiceLists() {
        //call rice list
        Call<List<RicecupListModel>> call= apiInterface.getRice();
        call.enqueue(new Callback<List<RicecupListModel>>() {
            @Override
            public void onResponse(Call<List<RicecupListModel>> call, Response<List<RicecupListModel>> response) {
                ricecupList = response.body();
                riceAdapter = new RiceAdapter(RiceActivity.this,ricecupList);
                recyclerView.setAdapter(riceAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<RicecupListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<RicecupListModel>> call= apiInterface.getRice();
                call.enqueue(new Callback<List<RicecupListModel>>() {
                    @Override
                    public void onResponse(Call<List<RicecupListModel>> call, Response<List<RicecupListModel>> response) {
                        ricecupList = response.body();
                        riceAdapter = new RiceAdapter(RiceActivity.this,ricecupList);
                        recyclerView.setAdapter(riceAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<RicecupListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}