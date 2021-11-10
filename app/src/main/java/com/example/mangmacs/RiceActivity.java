package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RicecupListModel> ricecupList;
    private ApiInterface apiInterface;
    private RiceAdapter riceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<RicecupListModel>> call= apiInterface.getRice();
        call.enqueue(new Callback<List<RicecupListModel>>() {
            @Override
            public void onResponse(Call<List<RicecupListModel>> call, Response<List<RicecupListModel>> response) {
                ricecupList = response.body();
                riceAdapter = new RiceAdapter(RiceActivity.this,ricecupList);
                recyclerView.setAdapter(riceAdapter);
            }

            @Override
            public void onFailure(Call<List<RicecupListModel>> call, Throwable t) {

            }
        });
    }
}