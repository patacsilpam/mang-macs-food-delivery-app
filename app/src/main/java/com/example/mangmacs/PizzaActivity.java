package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PizzaListModel> pizzaList;
    private ApiInterface apiInterface;
    private PizzaAdapter pizzaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call pizza list model
        Call<List<PizzaListModel>> call= apiInterface.getPizza();
        call.enqueue(new Callback<List<PizzaListModel>>() {
            @Override
            public void onResponse(Call<List<PizzaListModel>> call, Response<List<PizzaListModel>> response) {
                pizzaList = response.body();
                pizzaAdapter = new PizzaAdapter(PizzaActivity.this,pizzaList);
                recyclerView.setAdapter(pizzaAdapter);
            }

            @Override
            public void onFailure(Call<List<PizzaListModel>> call, Throwable t) {

            }
        });

    }
}