package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.PizzaAdapter;
import com.example.mangmacs.model.PizzaListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PizzaListModel> pizzaList;
    private ApiInterface apiInterface;
    private PizzaAdapter pizzaAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyProduct;
    private int countProduct = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        btnArrowBack = findViewById(R.id.arrow_back);
        emptyProduct = findViewById(R.id.emptyProduct);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowPizzaLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PizzaActivity.this,home_activity.class));
            }
        });
    }

    private void ShowPizzaLists() {
        //call pizza list model
        Call<List<PizzaListModel>> call= apiInterface.getPizza();
        call.enqueue(new Callback<List<PizzaListModel>>() {
            @Override
            public void onResponse(Call<List<PizzaListModel>> call, Response<List<PizzaListModel>> response) {
                pizzaList = response.body();
                pizzaAdapter = new PizzaAdapter(PizzaActivity.this,pizzaList);
                recyclerView.setAdapter(pizzaAdapter);
                countProduct = pizzaAdapter.getItemCount();
                if(countProduct == 0){
                    emptyProduct.setVisibility(View.VISIBLE);
                }
                else{
                    emptyProduct.setVisibility(View.GONE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<PizzaListModel>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<PizzaListModel>> call= apiInterface.getPizza();
                call.enqueue(new Callback<List<PizzaListModel>>() {
                    @Override
                    public void onResponse(Call<List<PizzaListModel>> call, Response<List<PizzaListModel>> response) {
                        pizzaList = response.body();
                        pizzaAdapter = new PizzaAdapter(PizzaActivity.this,pizzaList);
                        recyclerView.setAdapter(pizzaAdapter);
                        countProduct = pizzaAdapter.getItemCount();
                        if(countProduct == 0){
                            emptyProduct.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyProduct.setVisibility(View.GONE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<PizzaListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}