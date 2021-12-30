package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.NoodlesAdapter;
import com.example.mangmacs.model.NoodlesListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoodlesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<NoodlesListModel> noodlesList;
    private ApiInterface apiInterface;
    private NoodlesAdapter noodlesAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noodles);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call noodles list model
        Call<List<NoodlesListModel>> call= apiInterface.getNoodles();
        call.enqueue(new Callback<List<NoodlesListModel>>() {
            @Override
            public void onResponse(Call<List<NoodlesListModel>> call, Response<List<NoodlesListModel>> response) {
                noodlesList = response.body();
                noodlesAdapter = new NoodlesAdapter(NoodlesActivity.this,noodlesList);
                recyclerView.setAdapter(noodlesAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<NoodlesListModel>> call, Throwable t) {

            }
        });
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoodlesActivity.this,home_activity.class));
            }
        });
    }
    public void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<NoodlesListModel>> call= apiInterface.getNoodles();
                call.enqueue(new Callback<List<NoodlesListModel>>() {
                    @Override
                    public void onResponse(Call<List<NoodlesListModel>> call, Response<List<NoodlesListModel>> response) {
                        noodlesList = response.body();
                        noodlesAdapter = new NoodlesAdapter(NoodlesActivity.this,noodlesList);
                        recyclerView.setAdapter(noodlesAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<NoodlesListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}