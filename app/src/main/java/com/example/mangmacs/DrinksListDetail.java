package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DrinksListDetail extends AppCompatActivity {
    private ImageView imageView;
    private TextView productName,productPrice,productVariation,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productVariation = findViewById(R.id.productVariation);
        status = findViewById(R.id.status);
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        int productprice = intent.getIntExtra("price",0);
        String productvariation = intent.getStringExtra("productVariation");
        String productstatus = intent.getStringExtra("status");
        if(intent != null){
            Glide.with(DrinksListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText("₱ "+Integer.toString(productprice)+".00");
            productVariation.setText(productvariation);
            status.setText(productstatus);
        }
    }
}