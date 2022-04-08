package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.adapter.CartAdapter;
import com.example.mangmacs.adapter.PopularAdapter;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CustomerLoginModel;
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_activity extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerViewCart;
    private List<PopularListModel> popularList;
    private List<CartModel> cartList;
    private ApiInterface apiInterface;
    private PopularAdapter popularAdapter;
    private CartAdapter cartAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textName, btnSeeAll,totalCart;
    private CardView pizza,riceMeal,comboBudget,mealsGood,seafoods,soup,rice,pancit,bilao,noodles,pasta,dimsum,drinks;
    private FloatingActionButton floatingActionButton;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textName = findViewById(R.id.textName);
        totalCart = findViewById(R.id.totalCart);
        bottomNavigationView =  findViewById(R.id.bottom_nav);
        pizza = findViewById(R.id.pizza);
        riceMeal = findViewById(R.id.ricemeal);
        comboBudget = findViewById(R.id.combo);
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
        floatingActionButton = findViewById(R.id.iconCart);
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        //call popular list model
        String fname = SharedPreference.getSharedPreference(this).setFname();
        textName.setText(""+fname);
        bottomNavigationView.setSelectedItemId(R.id.home);
        BottomNav();
        Activites();
        ShowPopularLists();
        CountCart();
    }
    private void CountCart(){
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> call= apiInterface.getCart(email);
        call.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                cartList = response.body();
                cartAdapter = new CartAdapter(home_activity.this,cartList);
                recyclerViewCart.setAdapter(cartAdapter);
                int countCart = recyclerViewCart.getAdapter().getItemCount();
                totalCart.setText(String.valueOf(countCart));
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

            }
        });
    }
    private void ShowPopularLists() {
        Call<List<PopularListModel>> call= apiInterface.getPopular();
        call.enqueue(new Callback<List<PopularListModel>>() {
            @Override
            public void onResponse(Call<List<PopularListModel>> call, Response<List<PopularListModel>> response) {
                popularList = response.body();
                popularAdapter = new PopularAdapter(home_activity.this,popularList);
                recyclerView.setAdapter(popularAdapter);
                refresh();
            }

            @Override
            public void onFailure(Call<List<PopularListModel>> call, Throwable t) {

            }
        });
    }

    private void Activites() {
        btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,MenuActivty.class));
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, PizzaActivity.class));
            }
        });

        riceMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, RiceMealActivity.class));
            }
        });
        //initialize combo budget meal

        comboBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, ComboMealActivity.class));
            }
        });

        mealsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, MealsGoodActivity.class));
            }
        });

        seafoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, SeafoodsActivity.class));
            }
        });

        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, SoupActivity.class));
            }
        });

        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, RiceActivity.class));
            }
        });

        pancit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, PancitActivity.class));
            }
        });

        bilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, BilaoActivity.class));
            }
        });

        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, NoodlesActivity.class));
            }
        });

        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, PastaActivity.class));
            }
        });

        dimsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, DimsumActivity.class));
            }
        });

        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, DrinksActivity.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, CartActivity.class));
            }
        });

    }

    private void BottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), MenuActivty.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.promos:
                        startActivity(new Intent(getApplicationContext(), PromoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservation:
                        startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
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

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<PopularListModel>> call= apiInterface.getPopular();
                call.enqueue(new Callback<List<PopularListModel>>() {
                    @Override
                    public void onResponse(Call<List<PopularListModel>> call, Response<List<PopularListModel>> response) {
                        popularList = response.body();
                        popularAdapter = new PopularAdapter(home_activity.this,popularList);
                        recyclerView.setAdapter(popularAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<PopularListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedPreference.getSharedPreference(this).isLoggedIn()){
            Intent intent = new Intent(this,home_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}