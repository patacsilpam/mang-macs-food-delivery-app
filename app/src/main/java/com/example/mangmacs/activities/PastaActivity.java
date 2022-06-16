package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.PastaAdapter;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ProductListModel> pastaList;
    private ApiInterface apiInterface;
    private PastaAdapter pastaAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyProduct;
    private int countProduct = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta);
        btnArrowBack = findViewById(R.id.arrow_back);
        emptyProduct = findViewById(R.id.emptyProduct);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowPastaLists();
        Back();
    }

    private void Back() {
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PastaActivity.this,home_activity.class));
            }
        });
    }

    private void ShowPastaLists() {
        //call pasta list model
        Call<List<ProductListModel>> call= apiInterface.getPasta();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                pastaList = response.body();
                pastaAdapter = new PastaAdapter(PastaActivity.this,pastaList);
                recyclerView.setAdapter(pastaAdapter);
                countProduct = pastaAdapter.getItemCount();
                if(countProduct == 0){
                    emptyProduct.setVisibility(View.VISIBLE);
                }
                else{
                    emptyProduct.setVisibility(View.GONE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<ProductListModel>> call= apiInterface.getPasta();
                call.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        pastaList = response.body();
                        pastaAdapter = new PastaAdapter(PastaActivity.this,pastaList);
                        recyclerView.setAdapter(pastaAdapter);
                        countProduct = pastaAdapter.getItemCount();
                        if(countProduct == 0){
                            emptyProduct.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyProduct.setVisibility(View.GONE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}