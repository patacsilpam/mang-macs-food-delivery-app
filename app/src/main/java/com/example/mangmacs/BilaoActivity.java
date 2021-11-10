package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilaoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PancitBilaoListModel> pancitBilaoList;
    private ApiInterface apiInterface;
    private BilaoAdapter bilaoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilao);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<PancitBilaoListModel>> call= apiInterface.getPancitBilao();
        call.enqueue(new Callback<List<PancitBilaoListModel>>() {
            @Override
            public void onResponse(Call<List<PancitBilaoListModel>> call, Response<List<PancitBilaoListModel>> response) {
                pancitBilaoList = response.body();
                bilaoAdapter = new BilaoAdapter(BilaoActivity.this,pancitBilaoList);
                recyclerView.setAdapter(bilaoAdapter);
            }

            @Override
            public void onFailure(Call<List<PancitBilaoListModel>> call, Throwable t) {

            }
        });
    }
}