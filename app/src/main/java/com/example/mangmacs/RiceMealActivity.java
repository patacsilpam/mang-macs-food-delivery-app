package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiceMealActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RiceListModel> riceMealList;
    private ApiInterface apiInterface;
    private RiceMealAdapter riceMealAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_meal);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<RiceListModel>> call= apiInterface.getRiceMeal();
        call.enqueue(new Callback<List<RiceListModel>>() {
            @Override
            public void onResponse(Call<List<RiceListModel>> call, Response<List<RiceListModel>> response) {
                riceMealList = response.body();
                riceMealAdapter = new RiceMealAdapter(RiceMealActivity.this,riceMealList);
                recyclerView.setAdapter(riceMealAdapter);
            }

            @Override
            public void onFailure(Call<List<RiceListModel>> call, Throwable t) {

            }
        });
    }
}