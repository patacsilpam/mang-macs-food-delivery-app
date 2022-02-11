package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mangmacs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivty extends AppCompatActivity {
    private CardView pizza,riceMeal,comboBudget,mealsGood,seafoods,soup,rice,pancit,bilao,noodles,pasta,dimsum,drinks;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty);
        bottomNavigationView  =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu);
        BottomNav();
        //initialize pizza
        pizza = findViewById(R.id.btnPizza);
        riceMeal = findViewById(R.id.ricemeal);
        comboBudget = findViewById(R.id.comboBudget);
        mealsGood = findViewById(R.id.mealsgood);
        seafoods = findViewById(R.id.seafoods);
        soup = findViewById(R.id.soup);
        rice = findViewById(R.id.rice);
        pancit = findViewById(R.id.pancit);
        bilao = findViewById(R.id.bilao);
        noodles = findViewById(R.id.noodles);
        pasta = findViewById(R.id.pasta);
        dimsum = findViewById(R.id.dimsum);
        drinks = findViewById(R.id.drinks);
        Activites();
    }

    private void Activites() {
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, PizzaActivity.class));
            }
        });
        riceMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, RiceMealActivity.class));
            }
        });
        comboBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, ComboMealActivity.class));
            }
        });
        mealsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, MealsGoodActivity.class));
            }
        });
        seafoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, SeafoodsActivity.class));
            }
        });
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, SoupActivity.class));
            }
        });

        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, RiceActivity.class));
            }
        });

        pancit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, PancitActivity.class));
            }
        });
        bilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, BilaoActivity.class));
            }
        });
        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, NoodlesActivity.class));
            }
        });
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, PastaActivity.class));
            }
        });
        dimsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, DimsumActivity.class));
            }
        });
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, DrinksActivity.class));
            }
        });
    }

    private void BottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservation:
                        startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return true;
            }
        });
    }
}