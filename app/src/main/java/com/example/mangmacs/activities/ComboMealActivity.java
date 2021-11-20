package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.adapter.ComboMealAdapter;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.ComboMealListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboMealActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ComboMealListModel> comboMealList;
    private ApiInterface apiInterface;
    private ComboMealAdapter comboMealAdapter;
    private TextView btnArrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_meal);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<ComboMealListModel>> call= apiInterface.getComboMeal();
        call.enqueue(new Callback<List<ComboMealListModel>>() {
            @Override
            public void onResponse(Call<List<ComboMealListModel>> call, Response<List<ComboMealListModel>> response) {
                comboMealList = response.body();
                comboMealAdapter = new ComboMealAdapter(ComboMealActivity.this,comboMealList);
                recyclerView.setAdapter(comboMealAdapter);
            }

            @Override
            public void onFailure(Call<List<ComboMealListModel>> call, Throwable t) {

            }
        });
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComboMealActivity.this,home_activity.class));
            }
        });
    }
}