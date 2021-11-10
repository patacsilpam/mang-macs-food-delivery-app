package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PancitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PancitListModel> pancitList;
    private ApiInterface apiInterface;
    private PancitAdapter pancitAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pancit);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<PancitListModel>> call= apiInterface.getPancit();
        call.enqueue(new Callback<List<PancitListModel>>() {
            @Override
            public void onResponse(Call<List<PancitListModel>> call, Response<List<PancitListModel>> response) {
                pancitList = response.body();
                pancitAdapter = new PancitAdapter(PancitActivity.this,pancitList);
                recyclerView.setAdapter(pancitAdapter);
            }

            @Override
            public void onFailure(Call<List<PancitListModel>> call, Throwable t) {

            }
        });
    }
}