package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsGoodActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MealsGoodListModel> mealsGoodList;
    private ApiInterface apiInterface;
    private MealsGoodAdapter mealsGoodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_good);
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

    }
}