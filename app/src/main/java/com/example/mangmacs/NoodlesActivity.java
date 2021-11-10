package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoodlesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<NoodlesListModel> noodlesList;
    private ApiInterface apiInterface;
    private NoodlesAdapter noodlesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noodles);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<NoodlesListModel>> call= apiInterface.getNoodles();
        call.enqueue(new Callback<List<NoodlesListModel>>() {
            @Override
            public void onResponse(Call<List<NoodlesListModel>> call, Response<List<NoodlesListModel>> response) {
                noodlesList = response.body();
                noodlesAdapter = new NoodlesAdapter(NoodlesActivity.this,noodlesList);
                recyclerView.setAdapter(noodlesAdapter);
            }

            @Override
            public void onFailure(Call<List<NoodlesListModel>> call, Throwable t) {

            }
        });
    }
}