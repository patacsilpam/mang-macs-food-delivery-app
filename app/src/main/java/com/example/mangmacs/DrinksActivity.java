package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DrinksListModel> drinkList;
    private ApiInterface apiInterface;
    private DrinksAdapter drinksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
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
    }
}