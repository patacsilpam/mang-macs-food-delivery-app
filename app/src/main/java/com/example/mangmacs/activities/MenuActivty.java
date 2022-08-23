package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mangmacs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivty extends AppCompatActivity {
    private CardView promo,pizza,appetizer,grilled,mealsGood,sizzling,noodles,bilao,pasta,dimsum,soup,drinks,dessert,pulutan,wine;
    private BottomNavigationView bottomNavigationView;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty);
        bottomNavigationView  =  findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu);
        BottomNav();
           //initialize ids
        promo = findViewById(R.id.btnPromo);
        pizza = findViewById(R.id.btnPizza);
        appetizer = findViewById(R.id.appetizer);
        grilled = findViewById(R.id.grilled);
        mealsGood = findViewById(R.id.mealsgood);
        sizzling = findViewById(R.id.sizzling);
        noodles = findViewById(R.id.noodles);
        bilao = findViewById(R.id.bilao);
        pasta = findViewById(R.id.pasta);
        dimsum = findViewById(R.id.dimsum);
        soup = findViewById(R.id.soup);
        drinks = findViewById(R.id.drinks);
        dessert = findViewById(R.id.dessert);
        pulutan = findViewById(R.id.pulutan);
        wine = findViewById(R.id.wine);
               Activites();
    }

    private void Activites() {
           promo.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MenuActivty.this,PromoActivity.class));
               }
           });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, PizzaActivity.class));
            }
        });
        appetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, AppetizerActivity.class));
            }
        });
        grilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, GrilledActivity.class));
            }
        });
        mealsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, MealsGoodActivity.class));
            }
        });
        sizzling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, SizzlingActivity.class));
            }
        });
        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, NoodlesActivity.class));
            }
        });
        bilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, BilaoActivity.class));
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
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, SoupActivity.class));
            }
        });
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, DrinksActivity.class));
            }
        });
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, DessertActivity.class));
            }
        });
        pulutan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, PulutanActivity.class));
            }
        });
        wine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivty.this, WineActivity.class));
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
                    case R.id.menu:
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(), Bottom_Order_Activity.class));
                        overridePendingTransition(0,0);
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