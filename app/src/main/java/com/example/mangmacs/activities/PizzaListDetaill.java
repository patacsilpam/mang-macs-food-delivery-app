package com.example.mangmacs.activities;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaListDetaill extends AppCompatActivity {
    private LinearLayout ingredientsLayout;
    private CardView baseCardview;
    private ImageView imageView,showIngredients;
    private RelativeLayout priceLayout;
    private TextView txt_arrow_back,singleVariation,price,productCode;
    private TextView productName,status,ingredients,email,fname,lname;
    private TextInputLayout pizza_addons;
    private EditText quantity;
    private RadioGroup variation,rdCode;
    private RadioButton radioButton;
    private Button btnPizza,btnIncrement,btnDecrement;
    private int count = 1;
    private String image,category;

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
        price = findViewById(R.id.price);
        productCode = findViewById(R.id.productCode);
        email = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        btnPizza = findViewById(R.id.btnAddtoCartPizza);
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
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                quantity.setText(String.valueOf(count));
            }
        });
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
        category = intent.getStringExtra("productCategory");
        String newGroupPrice = intent.getStringExtra("groupPrice");
        String newGroupVariation = intent.getStringExtra("productVariation");
        String newProductStatus = intent.getStringExtra("preparationTime");
        String newGroupCode = intent.getStringExtra("groupCode");
        String newIngredients =intent.getStringExtra("mainIngredients");
        String newFirstName = SharedPreference.getSharedPreference(PizzaListDetaill.this).setFname();
        String newLastName = SharedPreference.getSharedPreference(PizzaListDetaill.this).setLname();
        String newEmailAddress = SharedPreference.getSharedPreference(PizzaListDetaill.this).setEmail();
        String[] splitPrice = newGroupPrice.split(",");
        String[] splitVariation = newGroupVariation.split(",");
        String[] splitCode = newGroupCode.split(",");
        if(intent != null){
            Glide.with(PizzaListDetaill.this).load(image).into(imageView);
            productName.setText(newProductname);
            ingredients.setText(newIngredients.toLowerCase(Locale.ROOT));
            fname.setText(newFirstName);
            lname.setText(newLastName);
            email.setText(newEmailAddress);
            status.setText(newProductStatus.concat("min"));
            price.setText(splitPrice[0]);
            for(int i = 0; i<splitVariation.length; i++){
                radioButton = new RadioButton(this);
                radioButton.setText(splitVariation[i]);
                radioButton.setTextAppearance(this, android.R.style.TextAppearance);
                variation.addView(radioButton);
            }
            //check variation array length
            variation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                   int selectedVariation = variation.getCheckedRadioButtonId();
                   radioButton = findViewById(selectedVariation);
                   String variation = radioButton.getText().toString();
                   if(variation.contains("Medium")){
                       price.setText(splitPrice[0]);
                       productCode.setText(splitCode[0]);
                   }
                   else{
                       price.setText(splitPrice[1]);
                       productCode.setText(splitCode[1]);
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
                    int prices = Integer.parseInt(price.getText().toString());
                    int items = Integer.parseInt(quantity.getText().toString());
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    int selectedSize = variation.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedSize);
                    String getVariation = radioButton.getText().toString();
                    String preparedTime = status.getText().toString();
                    ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> cartModelCall = apiComboInterface.addcart(email_address,code,product,category,getVariation,firstName,lastName,prices,items,add_ons,image,preparedTime);
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