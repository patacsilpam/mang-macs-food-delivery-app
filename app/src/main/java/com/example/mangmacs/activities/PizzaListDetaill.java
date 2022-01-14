package com.example.mangmacs.activities;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class PizzaListDetaill extends AppCompatActivity {
    private ImageView imageView;
    private TextView txt_arrow_back,medium,large;
    private TextView productName,status,email,fname,lname;;
    private TextInputLayout pizza_addons;
    private EditText quantity;
    private RadioGroup variation,rdCode;
    private Button btnPizza,btnIncrement,btnDecrement;
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list_detaill);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.pizzaproductName);
        status = findViewById(R.id.status);
        pizza_addons = findViewById(R.id.pizzaadd_ons);
        variation = findViewById(R.id.pizzavariation);
        rdCode = findViewById(R.id.rdCode);
        medium = findViewById(R.id.medium);
        large = findViewById(R.id.large);
        email = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        btnPizza = findViewById(R.id.btnAddtoCartPizza);
        quantity = findViewById(R.id.quantity);
        btnIncrement = findViewById(R.id.increment);
        btnDecrement = findViewById(R.id.decrement);
        btnDecrement.setEnabled(false); //set button decrement not clickable
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
        //get the value from its adapter
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        String groupPrice = intent.getStringExtra("groupPrice");
        String groupVariation = intent.getStringExtra("productVariation");
        String productstatus = intent.getStringExtra("status");
        String groupCode = intent.getStringExtra("groupCode");
        String firstname = SharedPreference.getSharedPreference(PizzaListDetaill.this).setFname();
        String lastname = SharedPreference.getSharedPreference(PizzaListDetaill.this).setLname();
        String emailAddress = SharedPreference.getSharedPreference(PizzaListDetaill.this).setEmail();
        if(intent != null){
            Glide.with(PizzaListDetaill.this).load(image).into(imageView);
            productName.setText(productname);
            status.setText(productstatus);
            String[] splitCode = groupCode.split(",");
            String[] splitVariation = groupVariation.split(",");
            String[] splitPrice = groupPrice.split(",");

            String product1 = splitVariation[0]+"          ₱ "+splitPrice[0]+".00"+splitCode[0];
            SpannableString spannableCode1 = new SpannableString(product1);
            ForegroundColorSpan fcsWhite1 = new ForegroundColorSpan(Color.WHITE);
            spannableCode1.setSpan(fcsWhite1,23,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            medium.setText(spannableCode1);

            String product2 = splitVariation[1]+"         ₱ "+splitPrice[1]+".00"+splitCode[1];
            SpannableString spannableCode2 = new SpannableString(product2);
            ForegroundColorSpan fcsWhite2 = new ForegroundColorSpan(Color.WHITE);
            spannableCode2.setSpan(fcsWhite2,23,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            large.setText(spannableCode2);
            email.setText(emailAddress);
            fname.setText(firstname);
            lname.setText(lastname);
        }
        btnPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(variation.getCheckedRadioButtonId() == -1){
                   Toast.makeText(getApplicationContext(),"Please select one variation",Toast.LENGTH_SHORT).show();
               }
               else{
                   String email_address = email.getText().toString();
                   String firstName = fname.getText().toString();
                   String lastName = lname.getText().toString();
                   String product = productName.getText().toString();
                   String add_ons = pizza_addons.getEditText().getText().toString();
                   int selectedSize = variation.getCheckedRadioButtonId();
                   RadioButton size = findViewById(selectedSize);
                   String getSize = size.getText().toString();
                   String variation = getSize.replace("₱",",");
                   String strPrice = size.getText().toString();
                   int prices = Integer.parseInt(strPrice.substring(17,20));
                   int number = Integer.parseInt(quantity.getText().toString());
                   ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                   Call<CartModel> cartModelCall = apiComboInterface.addcart(email_address,getSize,product,variation,firstName,lastName,prices,number,add_ons);
                   cartModelCall.enqueue(new Callback<CartModel>() {
                       @Override
                       public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                           if(response.body() != null){
                               String success =response.body().getSuccess();
                               if(success.equals("1")){
                                   Toast.makeText(getApplicationContext(),"New Order Added Successfully",Toast.LENGTH_SHORT).show();
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

        //a back button
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PizzaListDetaill.this,PizzaActivity.class));
            }
        });
    }
}