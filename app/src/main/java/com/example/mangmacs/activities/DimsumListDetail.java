package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DimsumListDetail extends AppCompatActivity {
    private ImageView imageView;
    private TextView txt_arrow_back;
    private TextView productName,productPrice,status,customerId,fname,lname;
    private TextInputLayout comboAddOns;
    private EditText quantity;
    private Button btnAddtoCart,btnIncrement,btnDecrement;
    private Intent intent;
    private String image;
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimsum_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.dimsumproductName);
        productPrice = findViewById(R.id.dimsumproductPrice);
        comboAddOns = findViewById(R.id.dimsumadd_ons);
        status = findViewById(R.id.status);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        btnAddtoCart = findViewById(R.id.btnAddtoCartDimsum);
        quantity = findViewById(R.id.quantity);
        btnIncrement = findViewById(R.id.increment);
        btnDecrement = findViewById(R.id.decrement);
        btnDecrement.setEnabled(false); //set button decrement not clickable
        IncrementDecrement();
        DisplayProductDetails();
        Back();
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
        int productprice = intent.getIntExtra("price",0);
        String productstatus = intent.getStringExtra("status");
        String firstname = SharedPreference.getSharedPreference(DimsumListDetail.this).setFname();
        String lastname = SharedPreference.getSharedPreference(DimsumListDetail.this).setLname();
        String customerID = SharedPreference.getSharedPreference(DimsumListDetail.this).setEmail();
        if(intent != null){
            Glide.with(DimsumListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText(Integer.toString(productprice));
            status.setText(productstatus);
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
            if (productstatus.equals("Out of Stock")){
                status.setTextColor(Color.RED);
                btnAddtoCart.setEnabled(false);
                btnIncrement.setEnabled(false);
                btnDecrement.setEnabled(false);
                btnDecrement.setBackground(getDrawable(R.drawable.minus_btn));
                btnIncrement.setBackground(getDrawable(R.drawable.plus_button));
            } else{
                status.setTextColor(Color.GREEN);
                btnAddtoCart.setEnabled(true);
                btnIncrement.setEnabled(true);
                btnDecrement.setEnabled(true);
                btnDecrement.setBackground(getDrawable(R.drawable.decrement_btn));
                btnIncrement.setBackground(getDrawable(R.drawable.increment_btn));
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
                String add_ons = comboAddOns.getEditText().getText().toString();
                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,variation,firstName,lastName,price,number,add_ons,image);
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
        //a back button
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DimsumListDetail.this,DimsumActivity.class));
            }
        });
    }
}