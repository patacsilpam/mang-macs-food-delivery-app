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

public class WineListDetailActivity extends AppCompatActivity {
    private LinearLayout ingredientsLayout;
    private CardView baseCardview;
    private ImageView imageView,showIngredients;
    private TextView txt_arrow_back;
    private TextView productName,productPrice,ingredients,status,customerId,fname,lname;
    private TextInputLayout drinksAddons;
    private EditText quantity;
    private Button btnAddtoCart,btnIncrement,btnDecrement;
    private Intent intent;
    private String image,category;
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.riceMealProduct);
        productPrice = findViewById(R.id.riceMealPrice);
        drinksAddons = findViewById(R.id.riceMeal_addOns);
        status = findViewById(R.id.riceMealStatus);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        btnAddtoCart = findViewById(R.id.btnRiceMeal);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        quantity = findViewById(R.id.quantity);
        ingredients = findViewById(R.id.ingredients);
        showIngredients = findViewById(R.id.showIngredients);
        ingredientsLayout = findViewById(R.id.ingredientLayout);
        baseCardview = findViewById(R.id.baseCardview);
        btnIncrement = findViewById(R.id.increment);
        btnDecrement = findViewById(R.id.decrement);
        btnDecrement.setEnabled(false); //set button decrement not clickable
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
        //button increment
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                quantity.setText(String.valueOf(count));
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
        String productstatus = intent.getStringExtra("preparationTime");
        String newIngredients =intent.getStringExtra("mainIngredients");
        String firstname = SharedPreference.getSharedPreference(WineListDetailActivity.this).setFname();
        String lastname = SharedPreference.getSharedPreference(WineListDetailActivity.this).setLname();
        String customerID = SharedPreference.getSharedPreference(WineListDetailActivity.this).setEmail();
        if(intent != null){
            Glide.with(WineListDetailActivity.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText(Integer.toString(productprice));
            ingredients.setText(newIngredients.toLowerCase(Locale.ROOT));
            status.setText(productstatus.concat(" min"));
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
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
                String add_ons = drinksAddons.getEditText().getText().toString();
                String preparedTime = status.getText().toString();
                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,category,variation,firstName,lastName,price,number,add_ons,image,preparedTime);
                cartModelCall.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success =response.body().getSuccess();
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"New Order Added Successfully",Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(WineListDetailActivity.this, WineActivity.class));
            }
        });
    }
}