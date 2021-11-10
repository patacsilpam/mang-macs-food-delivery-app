package com.example.mangmacs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_activity extends AppCompatActivity {
    TextView textName;
    Button btnSeeAll;
    CardView pizza,riceMeal,comboBudget,mealsGood,seafoods,soup,rice,pancit,bilao,noodles,pasta,dimsum,drinks;
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private List<PopularListModel> popularList;
    private ApiInterface apiInterface;
    private PopularAdatper popularAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textName = findViewById(R.id.textName);
        String loggedInName = SharedPreference.getSharedPreference(this).LoggedInUser();
        textName.setText(String.format("%s", loggedInName));
       /*if(!SharedPreference.getSharedPreference(this).isLoggedIn()){
            startActivity(new Intent(this,sign_up_activity.class));
            finish();
        }*/

        //bottom navigation
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.reservation:
                        startActivity(new Intent(getApplicationContext(),ReservationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(),MenuActivty.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
        btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,MenuActivty.class));
            }
        });
        //initialize pizza
        pizza = findViewById(R.id.pizza);
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,PizzaActivity.class));
            }
        });
        //initialize rice meal
        riceMeal = findViewById(R.id.ricemeal);
        riceMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,RiceMealActivity.class));
            }
        });
        //initialize combo budget meal
        comboBudget = findViewById(R.id.combo);
        comboBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,ComboMealActivity.class));
            }
        });
        mealsGood = findViewById(R.id.mealsgood);
        mealsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,MealsGoodActivity.class));
            }
        });
        seafoods = findViewById(R.id.seafoods);
        seafoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,SeafoodsActivity.class));
            }
        });
        soup = findViewById(R.id.soup);
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,SoupActivity.class));
            }
        });
        rice = findViewById(R.id.rice);
        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,RiceActivity.class));
            }
        });
        pancit = findViewById(R.id.pancit);
        pancit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,PancitActivity.class));
            }
        });
        bilao = findViewById(R.id.bilao);
        bilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,BilaoActivity.class));
            }
        });
        noodles = findViewById(R.id.noodles);
        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,NoodlesActivity.class));
            }
        });
        pasta = findViewById(R.id.pasta);
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,PastaActivity.class));
            }
        });
        dimsum = findViewById(R.id.dimsum);
        dimsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,DimsumActivity.class));
            }
        });
        drinks = findViewById(R.id.drinks);
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,DrinksActivity.class));
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call popular list model
        Call<List<PopularListModel>> call= apiInterface.getPopular();
        call.enqueue(new Callback<List<PopularListModel>>() {
            @Override
            public void onResponse(Call<List<PopularListModel>> call, Response<List<PopularListModel>> response) {
                popularList = response.body();
                popularAdatper = new PopularAdatper(home_activity.this,popularList);
                recyclerView.setAdapter(popularAdatper);
            }

            @Override
            public void onFailure(Call<List<PopularListModel>> call, Throwable t) {

            }
        });
        //cart button
        floatingActionButton = findViewById(R.id.iconCart);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,CartActivity.class));
            }
        });
    }
}