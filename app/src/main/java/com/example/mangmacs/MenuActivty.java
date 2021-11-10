package com.example.mangmacs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivty extends AppCompatActivity {
    CardView pizza,riceMeal,comboBudget,mealsGood,seafoods,soup,rice,pancit,bilao,noodles,pasta,dimsum,drinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty);
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservation:
                        startActivity(new Intent(getApplicationContext(),ReservationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return true;
            }
        });
        //initialize pizza
        pizza = findViewById(R.id.btnPizza);
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,PizzaActivity.class));
            }
        });
        //initialize rice meal
        riceMeal = findViewById(R.id.ricemeal);
        riceMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,RiceMealActivity.class));
            }
        });
        //initialize combo budget meal
        comboBudget = findViewById(R.id.comboBudget);
        comboBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,ComboMealActivity.class));
            }
        });
        mealsGood = findViewById(R.id.mealsgood);
        mealsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,MealsGoodActivity.class));
            }
        });
        seafoods = findViewById(R.id.seafoods);
        seafoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,SeafoodsActivity.class));
            }
        });
        soup = findViewById(R.id.soup);
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,SoupActivity.class));
            }
        });
        rice = findViewById(R.id.rice);
        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,RiceActivity.class));
            }
        });
        pancit = findViewById(R.id.pancit);
        pancit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,PancitActivity.class));
            }
        });
        bilao = findViewById(R.id.bilao);
        bilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,BilaoActivity.class));
            }
        });
        noodles = findViewById(R.id.noodles);
        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,NoodlesActivity.class));
            }
        });
        pasta = findViewById(R.id.pasta);
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,PastaActivity.class));
            }
        });
        dimsum = findViewById(R.id.dimsum);
        dimsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,DimsumActivity.class));
            }
        });
        drinks = findViewById(R.id.drinks);
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this,DrinksActivity.class));
            }
        });
    }
}