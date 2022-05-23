package com.example.mangmacs.activities;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaListDetaill extends AppCompatActivity {
    private RelativeLayout priceLayout;
    private ImageView imageView;
    private TextView txt_arrow_back,medium,large,price,productCode;
    private TextView productName,status,email,fname,lname;
    private TextInputLayout pizza_addons;
    private EditText quantity;
    private RadioGroup variation,rdCode;
    private Button btnPizza,btnIncrement,btnDecrement;
    private int count = 1;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list_detaill);
        priceLayout = findViewById(R.id.priceLayout);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.pizzaproductName);
        status = findViewById(R.id.status);
        pizza_addons = findViewById(R.id.pizzaadd_ons);
        variation = findViewById(R.id.pizzavariation);
        rdCode = findViewById(R.id.rdCode);
        medium = findViewById(R.id.medium);
        large = findViewById(R.id.large);
        price = findViewById(R.id.price);
        productCode = findViewById(R.id.productCode);
        email = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        btnPizza = findViewById(R.id.btnAddtoCartPizza);
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
        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        String newProductname = intent.getStringExtra("productName");
        String newGroupPrice = intent.getStringExtra("groupPrice");
        String newGroupVariation = intent.getStringExtra("productVariation");
        String newProductStatus = intent.getStringExtra("status");
        String newGroupCode = intent.getStringExtra("groupCode");
        String newFirstName = SharedPreference.getSharedPreference(PizzaListDetaill.this).setFname();
        String newLastName = SharedPreference.getSharedPreference(PizzaListDetaill.this).setLname();
        String newEmailAddress = SharedPreference.getSharedPreference(PizzaListDetaill.this).setEmail();
        if(intent != null){
            Glide.with(PizzaListDetaill.this).load(image).into(imageView);
            productName.setText(newProductname);
            fname.setText(newFirstName);
            lname.setText(newLastName);
            email.setText(newEmailAddress);
            String[] splitCode = newGroupCode.split(",");//split product code
            String[] splitVariation = newGroupVariation.split(",");//split product variation or category
            String[] splitPrice = newGroupPrice.split(",");//split product price
            String[] splitStatus = newProductStatus.split(",");
            if (splitVariation[0] != null){
                medium.setText(splitVariation[0]);
            } else{
                medium.setVisibility(View.GONE);
            }
            if (splitVariation[1] != null){
                large.setText(splitVariation[1]);
            } else{
                large.setVisibility(View.GONE);
            }

            variation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                   int selectedVariation = variation.getCheckedRadioButtonId();
                   RadioButton radioButton = findViewById(selectedVariation);
                   String variation = radioButton.getText().toString();
                   String productStatus ="";
                   priceLayout.setVisibility(View.VISIBLE);
                   if (variation.equals("Medium")){
                       price.setText(String.valueOf(splitPrice[0]));
                       productCode.setText(String.valueOf(splitCode[0]));
                       status.setText(String.valueOf(splitStatus[0]));
                       productStatus = status.getText().toString();
                   } else{
                       price.setText(String.valueOf(splitPrice[1]));
                       productCode.setText(String.valueOf(splitCode[1]));
                       status.setText(String.valueOf(splitStatus[1]));
                       productStatus = status.getText().toString();
                   }
                    if (productStatus.equals("Out of Stock")){
                        status.setTextColor(Color.RED);
                        btnPizza.setEnabled(false);
                        btnIncrement.setEnabled(false);
                        btnDecrement.setEnabled(false);
                        btnDecrement.setBackground(getDrawable(R.drawable.minus_btn));
                        btnIncrement.setBackground(getDrawable(R.drawable.plus_button));
                    } else{
                        status.setTextColor(Color.GREEN);
                        btnPizza.setEnabled(true);
                        btnIncrement.setEnabled(true);
                        btnDecrement.setEnabled(true);
                        btnDecrement.setBackground(getDrawable(R.drawable.decrement_btn));
                        btnIncrement.setBackground(getDrawable(R.drawable.increment_btn));
                    }
                }
            });
        }
        AddToCart();
    }

    private void AddToCart() {
        btnPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(variation.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(),"Please select one variation",Toast.LENGTH_SHORT).show();
                }
                else{
                    String email_address = email.getText().toString();
                    String code = productCode.getText().toString();
                    String product = productName.getText().toString();
                    String add_ons = pizza_addons.getEditText().getText().toString();
                    int selectedSize = variation.getCheckedRadioButtonId();
                    RadioButton radioVariation = findViewById(selectedSize);
                    String getVariation = radioVariation.getText().toString();
                    int prices = Integer.parseInt(price.getText().toString());
                    int items = Integer.parseInt(quantity.getText().toString());
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> cartModelCall = apiComboInterface.addcart(email_address,code,product,getVariation,firstName,lastName,prices,items,add_ons,image);
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
            }
        });
    }

    private void Back() {
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PizzaListDetaill.this,PizzaActivity.class));
            }
        });
    }
}