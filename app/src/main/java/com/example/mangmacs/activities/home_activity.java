package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.adapter.CartAdapter;
import com.example.mangmacs.adapter.PopularAdapter;
import com.example.mangmacs.api.CartInterface;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.PizzaListModel;
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_activity extends AppCompatActivity implements CartInterface {
    private RecyclerView recyclerView,recyclerViewCart;
    private List<PizzaListModel> popularList;
    private List<CartModel> cartList;
    private ApiInterface apiInterface;
    private PopularAdapter popularAdapter;
    private CartAdapter cartAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textName, btnSeeAll,totalCart;
    private CardView promo,pizza,appetizer,grilled,mealsGood,sizzling,noodles,bilao,pasta,dimsum,soup,drinks,dessert,pulutan,wine;
    private FloatingActionButton floatingActionButton;
    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.spin_kit);
        textName = findViewById(R.id.textName);
        totalCart = findViewById(R.id.totalCart);
        bottomNavigationView =  findViewById(R.id.bottom_nav);
        //initialize ids
        promo = findViewById(R.id.promo);
        pizza = findViewById(R.id.pizza);
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
        createNotificationChannel();
    }

    private void CountCart(){
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> call= apiInterface.getCart(email);
        call.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                cartList = response.body();
                cartAdapter = new CartAdapter(home_activity.this,cartList,home_activity.this);
                recyclerViewCart.setAdapter(cartAdapter);
                int countCart = recyclerViewCart.getAdapter().getItemCount();
                totalCart.setText(String.valueOf(countCart));
                String str_totalCart = totalCart.getText().toString();
                if(str_totalCart.equals("0")){
                    totalCart.setVisibility(View.INVISIBLE);
                }
                else{
                    totalCart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

            }
        });
    }
    private void ShowPopularLists() {
        if (!isNetworkAvailable()){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(home_activity.this,"No Connection",Toast.LENGTH_SHORT).show();
        }
        else{
            Sprite circle = new Circle();
            progressBar.setIndeterminateDrawable(circle);
            progressBar.setVisibility(View.VISIBLE);
            Call<List<PizzaListModel>> call= apiInterface.getPopular();
            call.enqueue(new Callback<List<PizzaListModel>>() {
                @Override
                public void onResponse(Call<List<PizzaListModel>> call, Response<List<PizzaListModel>> response) {
                    progressBar.setVisibility(View.GONE);
                    popularList = response.body();
                    popularAdapter = new PopularAdapter(home_activity.this,popularList);
                    recyclerView.setAdapter(popularAdapter);
                    refresh();
                }

                @Override
                public void onFailure(Call<List<PizzaListModel>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void Activites() {
        btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,MenuActivty.class));
            }
        });
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this,PromoActivity.class));
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, PizzaActivity.class));
            }
        });
        appetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, AppetizerActivity.class));
            }
        });
        grilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, GrilledActivity.class));
            }
        });
        mealsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, MealsGoodActivity.class));
            }
        });
        sizzling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, SizzlingActivity.class));
            }
        });
        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, NoodlesActivity.class));
            }
        });
        bilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, BilaoActivity.class));
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
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, SoupActivity.class));
            }
        });
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, DrinksActivity.class));
            }
        });
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, DessertActivity.class));
            }
        });
        pulutan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, PulutanActivity.class));
            }
        });
        wine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_activity.this, WineActivity.class));
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
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.book:
                        startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
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
                Call<List<PizzaListModel>> call= apiInterface.getPopular();
                call.enqueue(new Callback<List<PizzaListModel>>() {
                    @Override
                    public void onResponse(Call<List<PizzaListModel>> call, Response<List<PizzaListModel>> response) {
                        popularList = response.body();
                        popularAdapter = new PopularAdapter(home_activity.this,popularList);
                        recyclerView.setAdapter(popularAdapter);
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<PizzaListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(sign_up_activity.CHANNEL_ID, sign_up_activity.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(sign_up_activity.CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedPreference.getSharedPreference(this).isLoggedIn()){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }
    //check internet connection
    public boolean isNetworkAvailable(){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null){
                networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }catch (NullPointerException e){
            return false;
        }
        return false;
    }

    @Override
    public void onTotalPriceChange(String totalPrice) {

    }
}