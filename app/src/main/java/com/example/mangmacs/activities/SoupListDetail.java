package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoupListDetail extends AppCompatActivity {
    private ImageView imageView;
    private TextView txt_arrow_back;
    private TextView productName,productPrice,status,customerId,fname,lname;
    private TextInputLayout drinksAddons;
    private Button btnAddtoCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soup_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.soupproductName);
        productPrice = findViewById(R.id.soupproductPrice);
        drinksAddons = findViewById(R.id.soupadd_ons);
        status = findViewById(R.id.status);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        btnAddtoCart = findViewById(R.id.btnSoup);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        int productprice = intent.getIntExtra("price",0);
        String productstatus = intent.getStringExtra("status");
        String firstname = SharedPreference.getSharedPreference(SoupListDetail.this).setFname();
        String lastname = SharedPreference.getSharedPreference(SoupListDetail.this).setLname();
        String customerID = SharedPreference.getSharedPreference(SoupListDetail.this).setEmail();
        if(intent != null){
            Glide.with(SoupListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText(Integer.toString(productprice));
            status.setText(productstatus);
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
        }
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = customerId.getText().toString();
                String code = intent.getStringExtra("code");
                String product = productName.getText().toString();
                String variation = "none";
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                int price = Integer.parseInt(productPrice.getText().toString());
                String add_ons = drinksAddons.getEditText().getText().toString();
                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,variation,firstName,lastName,price,add_ons);
                cartModelCall.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success =response.body().getSuccess();
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Added to cart Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });
        //a back button
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SoupListDetail.this,SoupActivity.class));
            }
        });
    }
}