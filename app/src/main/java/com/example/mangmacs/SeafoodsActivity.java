package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeafoodsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SeafoodsListModel> seafoodsList;
    private ApiInterface apiInterface;
    private SeafoodsAdapter seafoodsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seafoods);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<SeafoodsListModel>> call= apiInterface.getSeafoods();
        call.enqueue(new Callback<List<SeafoodsListModel>>() {
            @Override
            public void onResponse(Call<List<SeafoodsListModel>> call, Response<List<SeafoodsListModel>> response) {
                seafoodsList = response.body();
                seafoodsAdapter = new SeafoodsAdapter(SeafoodsActivity.this,seafoodsList);
                recyclerView.setAdapter(seafoodsAdapter);
            }

            @Override
            public void onFailure(Call<List<SeafoodsListModel>> call, Throwable t) {

            }
        });
    }
}