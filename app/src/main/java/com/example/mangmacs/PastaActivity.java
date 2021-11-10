package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PastaListModel> pastaList;
    private ApiInterface apiInterface;
    private PastaAdapter pastaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<PastaListModel>> call= apiInterface.getPasta();
        call.enqueue(new Callback<List<PastaListModel>>() {
            @Override
            public void onResponse(Call<List<PastaListModel>> call, Response<List<PastaListModel>> response) {
                pastaList = response.body();
                pastaAdapter = new PastaAdapter(PastaActivity.this,pastaList);
                recyclerView.setAdapter(pastaAdapter);
            }

            @Override
            public void onFailure(Call<List<PastaListModel>> call, Throwable t) {

            }
        });
    }
}