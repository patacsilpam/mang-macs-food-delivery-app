package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;

public class PizzaListDetaill extends AppCompatActivity {
    private ImageView imageView;
    private TextView txt_arrow_back;
    private TextView productName,productPrice,productVariation,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list_detaill);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productVariation = findViewById(R.id.productVariation);
        status = findViewById(R.id.status);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        int productprice = intent.getIntExtra("price",0);
        String productvariation = intent.getStringExtra("productVariation");
        String productstatus = intent.getStringExtra("status");
        if(intent != null){
            Glide.with(PizzaListDetaill.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText("₱"+Integer.toString(productprice)+".00");
            productVariation.setText(productvariation);
            status.setText(productstatus);

        }
        //a back button
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PizzaListDetaill.this,PizzaActivity.class));
            }
        });
    }
}