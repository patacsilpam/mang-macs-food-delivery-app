package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.CartAdapter;
import com.example.mangmacs.adapter.MyAddressAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.AddressListModel;
import com.example.mangmacs.model.CartModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private List<CartModel> cartModelList;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    private TextView cart,textProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.recyclerviewCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cart = findViewById(R.id.totalCart);
        textProductPrice = findViewById(R.id.textProductPrice);
        showCart();
        Intent intent = getIntent();
        int total = intent.getIntExtra("total",0);
        textProductPrice.setText(String.valueOf(total));
    }

    private void showCart() {
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
        getUserCart.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                cartModelList = response.body();
                cartAdapter = new CartAdapter(CartActivity.this,cartModelList);
                recyclerView.setAdapter(cartAdapter);
               int countCart =  recyclerView.getAdapter().getItemCount();
               cart.setText("("+String.valueOf(countCart)+")");

            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

            }
        });
    }

}