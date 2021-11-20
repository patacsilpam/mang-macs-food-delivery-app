package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.MealsGoodAdapter;
import com.example.mangmacs.model.MealsGoodListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsGoodActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MealsGoodListModel> mealsGoodList;
    private ApiInterface apiInterface;
    private MealsGoodAdapter mealsGoodAdapter;
    private TextView btnArrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_good);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call meals good model
        Call<List<MealsGoodListModel>> call= apiInterface.getMealsGood();
        call.enqueue(new Callback<List<MealsGoodListModel>>() {
            @Override
            public void onResponse(Call<List<MealsGoodListModel>> call, Response<List<MealsGoodListModel>> response) {
                mealsGoodList = response.body();
                mealsGoodAdapter = new MealsGoodAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerView.setAdapter(mealsGoodAdapter);
            }

            @Override
            public void onFailure(Call<List<MealsGoodListModel>> call, Throwable t) {

            }
        });
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealsGoodActivity.this,home_activity.class));
            }
        });
    }
}