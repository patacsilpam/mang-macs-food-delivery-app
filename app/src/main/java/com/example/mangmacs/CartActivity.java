package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mangmacs.adapter.CartAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<String> productId, productName, productPrice;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    private DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        dbHandler = new DbHandler(CartActivity.this);
        productId = new ArrayList<>();
        productName = new ArrayList<>();
        productPrice = new ArrayList<>();
        displayProducts();
        recyclerView = findViewById(R.id.recyclerview);
        cartAdapter = new CartAdapter(CartActivity.this,productId,productName,productPrice);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
    }
    void displayProducts() {
        Cursor cursor = dbHandler.readProducts();
        if(cursor.getCount() == 0){
            Toast.makeText(CartActivity.this,"No Data", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                productId.add(cursor.getString(0));
                productName.add(cursor.getString(1));
                productPrice.add(cursor.getString(2));
            }
        }
    }
}