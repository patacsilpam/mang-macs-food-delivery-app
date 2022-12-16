package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinksListDetail extends AppCompatActivity {
    private LinearLayout ingredientsLayout;
    private RelativeLayout stocksLayout;
    private CardView baseCardview;
    private ImageView imageView,showIngredients;
    private TextView txt_arrow_back;
    private TextView productName,productPrice,ingredients,status,itemStock,customerId,fname,lname;
    private TextInputLayout drinksAddons;
    private EditText quantity;
    private Button btnAddtoCart,btnDecrement,btnIncrement;
    private Intent intent;
    private String image,category,stockCode;
    int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.drinksproductName);
        productPrice = findViewById(R.id.drinksproductPrice);
        drinksAddons = findViewById(R.id.drinksadd_ons);
        status = findViewById(R.id.status);
        itemStock = findViewById(R.id.itemStock);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        btnAddtoCart = findViewById(R.id.btnAddtoCartDrinks);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        quantity = findViewById(R.id.quantity);
        ingredients = findViewById(R.id.ingredients);
        showIngredients = findViewById(R.id.showIngredients);
        ingredientsLayout = findViewById(R.id.ingredientLayout);
        stocksLayout = findViewById(R.id.stocksLayout);
        baseCardview = findViewById(R.id.baseCardview);
        btnIncrement = findViewById(R.id.increment);
        btnDecrement = findViewById(R.id.decrement);
        ShowIngredients();
        IncrementDecrement();
        DisplayProductDetails();
        Back();
    }
    private void ShowIngredients(){
        showIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingredientsLayout.getVisibility() == View.GONE){
                    ingredientsLayout.setVisibility(View.VISIBLE);
                    showIngredients.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else{
                    ingredientsLayout.setVisibility(View.GONE);
                    showIngredients.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });
    }
    private void IncrementDecrement() {
        btnDecrement.setEnabled(false); //set button decrement not clickable
        //button increment
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                quantity.setText(String.valueOf(count));
                int numQty = Integer.parseInt(quantity.getText().toString());
                int numStocks = Integer.parseInt(itemStock.getText().toString());
                if (numQty > numStocks){
                    quantity.setText(String.valueOf(numStocks));
                }
            }
        });
        //button decrement
        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                quantity.setText(String.valueOf(count));

            }
        });
        //disable button decrement to edit quantity if it is equal to one
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number = quantity.getText().toString();
                if(number.equals("1")){
                    btnDecrement.setEnabled(false);
                }else{
                    btnDecrement.setEnabled(true);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number = quantity.getText().toString();
                if(number.equals("1")){
                    btnDecrement.setEnabled(false);
                }else{
                    btnDecrement.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    private void DisplayProductDetails() {
        //get the value from its adapter
        intent = getIntent();
        image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        category = intent.getStringExtra("productCategory");
        int productprice = intent.getIntExtra("price",0);
        int stocks = intent.getIntExtra("stocks",0);
        stockCode = intent.getStringExtra("stockCode");
        String productstatus = intent.getStringExtra("preparationTime");
        String newIngredients =intent.getStringExtra("mainIngredients");
        String firstname = SharedPreference.getSharedPreference(DrinksListDetail.this).setFname();
        String lastname = SharedPreference.getSharedPreference(DrinksListDetail.this).setLname();
        String customerID = SharedPreference.getSharedPreference(DrinksListDetail.this).setEmail();
        if(intent != null){
            Glide.with(DrinksListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText(Integer.toString(productprice));
            ingredients.setText(newIngredients.toLowerCase(Locale.ROOT));
            status.setText(productstatus.concat(" mins"));
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
            if (stocks <= 0){
                itemStock.setText("Out of Stock");
                itemStock.setTextColor(Color.RED);
                btnAddtoCart.setEnabled(false);
                btnIncrement.setEnabled(false);
            }
            else{
                itemStock.setText(String.valueOf(stocks));
            }

        }
       AddToCart();
    }

    private void AddToCart() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = customerId.getText().toString();
                String code = intent.getStringExtra("code");
                String product = productName.getText().toString();
                String variation = "";
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                int price = Integer.parseInt(productPrice.getText().toString());
                int number = Integer.parseInt(quantity.getText().toString());
                String specialReq = drinksAddons.getEditText().getText().toString();
                String preparedTime = status.getText().toString();
                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,stockCode,product,category,variation,firstName,lastName,price,number,"",0,specialReq,image,preparedTime);
                cartModelCall.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success =response.body().getSuccess();
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),home_activity.class));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void Back() {
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinksListDetail.this,DrinksActivity.class));
            }
        });
    }
}