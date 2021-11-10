package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DimsumActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DimsumListModel> dimsumList;
    private ApiInterface apiInterface;
    private DimsumAdapter dimsumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimsum);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<DimsumListModel>> call= apiInterface.getDimsum();
        call.enqueue(new Callback<List<DimsumListModel>>() {
            @Override
            public void onResponse(Call<List<DimsumListModel>> call, Response<List<DimsumListModel>> response) {
                dimsumList = response.body();
                dimsumAdapter = new DimsumAdapter(DimsumActivity.this,dimsumList);
                recyclerView.setAdapter(dimsumAdapter);
            }

            @Override
            public void onFailure(Call<List<DimsumListModel>> call, Throwable t) {

            }
        });
    }
}