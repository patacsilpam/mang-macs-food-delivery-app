package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboMealActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ComboMealListModel> comboMealList;
    private ApiInterface apiInterface;
    private ComboMealAdapter comboMealAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_meal);
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
    }
}