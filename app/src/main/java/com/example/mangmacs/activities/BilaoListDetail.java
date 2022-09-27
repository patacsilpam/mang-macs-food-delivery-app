package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilaoListDetail extends AppCompatActivity {
    private LinearLayout ingredientsLayout;
    private ImageView imageView,showIngredients;
    private TextView productName,price,productCode,ingredients;
    private TextView txt_arrow_back,status,customerId,fname,lname;
    private TextInputLayout bilaoAddOns;
    private EditText quantity;
    private Button btnAddtoCart,btnIncrement,btnDecrement;
    private RadioButton radioButton;
    private RadioGroup rdVariation;
    private int count = 1;
    private int newPrepTime = 0;
    private String image,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilao_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.bilaoproductName);
        bilaoAddOns = findViewById(R.id.bilaoadd_ons);
        status = findViewById(R.id.status);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        rdVariation = findViewById(R.id.bilaovariation);
        price = findViewById(R.id.price);
        productCode = findViewById(R.id.productCode);
        btnAddtoCart = findViewById(R.id.btnBilao);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        quantity = findViewById(R.id.quantity);
        ingredients = findViewById(R.id.ingredients);
        showIngredients = findViewById(R.id.showIngredients);
        ingredientsLayout = findViewById(R.id.ingredientLayout);
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
        category = intent.getStringExtra("productCategory");
        newPrepTime = intent.getIntExtra("preparationTime",0);
        String productname = intent.getStringExtra("productName");
        String productprice = intent.getStringExtra("groupPriceBilao");
        String productvariation = intent.getStringExtra("productVariationBilao");
        String groupCode = intent.getStringExtra("groupCode");
        String newIngredients =intent.getStringExtra("mainIngredients");
        String customerID = SharedPreference.getSharedPreference(BilaoListDetail.this).setEmail();
        String firstname = SharedPreference.getSharedPreference(BilaoListDetail.this).setFname();
        String lastname = SharedPreference.getSharedPreference(BilaoListDetail.this).setLname();
        if(intent != null){
            String[] splitVariation = productvariation.split(",");
            String[] splitPrice = productprice.split(",");
            String[] splitCode = groupCode.split(",");
            Glide.with(BilaoListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            ingredients.setText(newIngredients.toLowerCase(Locale.ROOT));
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
            price.setText(splitPrice[0]);
            //convert min to hour if preparation time is greater than or equal to 60 minutes
            if (newPrepTime < 60){
                status.setText(String.valueOf(newPrepTime).concat("min"));
            }
            else{
                int convertMinHr = newPrepTime/60;
                status.setText(String.valueOf(convertMinHr).concat("hr"));
            }
            //show product variation through looping
            for (int i = 0; i<splitVariation.length; i++){
                radioButton = new RadioButton(this);
                radioButton.setText(splitVariation[i]);
                radioButton.setTextAppearance(this, android.R.style.TextAppearance);
                rdVariation.addView(radioButton);
                //get radio button text
                rdVariation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        int selectSize = rdVariation.getCheckedRadioButtonId();
                        radioButton = findViewById(selectSize);
                        String variation =  radioButton.getText().toString();
                        if(variation.contains("7 - 10 Person")){
                            price.setText(splitPrice[0]);
                            productCode.setText(splitCode[0]);
                        }
                        else if(variation.contains("10 -15 Person")){
                            price.setText(splitPrice[1]);
                            productCode.setText(splitCode[1]);
                        }
                        else{
                            price.setText(splitPrice[2]);
                            productCode.setText(splitCode[2]);
                        }
                    }
                });
            }

        }
        AddToCart();
    }

    private void AddToCart() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdVariation.getVisibility() == View.VISIBLE && rdVariation.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(),"Please select one variation",Toast.LENGTH_SHORT).show();
                }
                else{
                    String id = customerId.getText().toString();
                    String code = productCode.getText().toString();
                    String product = productName.getText().toString();
                    String specialReq = bilaoAddOns.getEditText().getText().toString();
                    int selectedSize = rdVariation.getCheckedRadioButtonId();
                    RadioButton size = findViewById(selectedSize);
                    String variation = size.getText().toString();
                    int prices = Integer.parseInt(price.getText().toString());
                    int number = Integer.parseInt(quantity.getText().toString());
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    String preparedTime = String.valueOf(newPrepTime);
                    ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,category,variation,firstName,lastName,prices,number,"",0,specialReq,image,preparedTime);
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
                startActivity(new Intent(BilaoListDetail.this,BilaoActivity.class));
            }
        });
    }
}