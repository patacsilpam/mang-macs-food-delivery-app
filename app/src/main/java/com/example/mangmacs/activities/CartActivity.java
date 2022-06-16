package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.CartAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.SettingsModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private List<CartModel> cartModelList;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    private TextView cart,textProductPrice,arrowBack;
    private Button checkOut,goBackMenu;
    private ProgressBar progressBar;
    private View emptyCart;
    private int countCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        progressBar = findViewById(R.id.spin_kit);
        recyclerView = findViewById(R.id.recyclerviewCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cart = findViewById(R.id.totalCart);
        textProductPrice = findViewById(R.id.textProductPrice);
        checkOut = findViewById(R.id.checkOut);
        arrowBack = findViewById(R.id.arrow_back);
        emptyCart = findViewById(R.id.emptyCart);
        goBackMenu = emptyCart.findViewById(R.id.goBackMenu);
        emptyCart.setVisibility(View.GONE);
        showCart();
        CheckOut();
        Back();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalPrice"));
    }
    private void Back() {
        //arrow back button
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,home_activity.class));
            }
        });
    }

    private void CheckOut() {
        //check out button
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,OrderModeActivity.class));
            }
        });
    }

    private void showCart() {
            Sprite circle = new Circle();
            progressBar.setIndeterminateDrawable(circle);
            progressBar.setVisibility(View.VISIBLE);
            String email = SharedPreference.getSharedPreference(this).setEmail();
            ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
            Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
            getUserCart.enqueue(new Callback<List<CartModel>>() {
                @Override
                public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                    progressBar.setVisibility(View.GONE);
                    cartModelList = response.body();
                    cartAdapter = new CartAdapter(CartActivity.this,cartModelList);
                    recyclerView.setAdapter(cartAdapter);
                    countCart = recyclerView.getAdapter().getItemCount();
                    cart.setText("("+String.valueOf(countCart)+")");
                    if(countCart == 0){
                        checkOut.setEnabled(false);
                        emptyCart.setVisibility(View.VISIBLE);
                        goBackMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(),MenuActivty.class));
                            }
                        });
                    }
                    else{
                        checkOut.setEnabled(true);
                        emptyCart.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<CartModel>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String totalPrice = intent.getStringExtra("totalprice");
            textProductPrice.setText(totalPrice);
        }
    };

}