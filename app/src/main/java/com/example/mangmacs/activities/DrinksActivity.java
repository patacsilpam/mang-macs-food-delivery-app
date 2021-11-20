package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.DrinksAdapter;
import com.example.mangmacs.model.DrinksListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DrinksListModel> drinkList;
    private ApiInterface apiInterface;
    private DrinksAdapter drinksAdapter;
    private TextView btnArrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<DrinksListModel>> call= apiInterface.getDrinks();
        call.enqueue(new Callback<List<DrinksListModel>>() {
            @Override
            public void onResponse(Call<List<DrinksListModel>> call, Response<List<DrinksListModel>> response) {
                drinkList = response.body();
                drinksAdapter = new DrinksAdapter(DrinksActivity.this,drinkList);
                recyclerView.setAdapter(drinksAdapter);
            }

            @Override
            public void onFailure(Call<List<DrinksListModel>> call, Throwable t) {

            }
        });
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinksActivity.this,home_activity.class));
            }
        });
    }
}