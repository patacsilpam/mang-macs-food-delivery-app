package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ComboBudgetListDetail extends AppCompatActivity {
    private ImageView imageView;
    private TextView productName,productPrice,productVariation,status;
    private Button btnAddtoCart;
    private DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_budget_list_detail);
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
            Glide.with(ComboBudgetListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText("₱ "+Integer.toString(productprice)+".00");
            productVariation.setText(productvariation);
            status.setText(productstatus);
        }
        dbHandler = new DbHandler(ComboBudgetListDetail.this);
        btnAddtoCart = findViewById(R.id.btnAddtoCart);
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringProductName = productName.getText().toString();
                String stringProductPrice = productPrice.getText().toString();
                dbHandler.addNewProducts(stringProductName, stringProductPrice);
               Toast.makeText(ComboBudgetListDetail.this,"Add",Toast.LENGTH_SHORT).show();
            }
        });
    }
}