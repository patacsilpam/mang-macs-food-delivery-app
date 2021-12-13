package com.example.mangmacs.activities;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
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
    private TextView productName,status,customerId,fname,lname;;
    private TextInputLayout pizza_addons;
    private RadioGroup variation,rdCode;
    private Button btnPizza;
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
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        btnPizza = findViewById(R.id.btnAddtoCartPizza);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        String groupPrice = intent.getStringExtra("groupPrice");
        String groupVariation = intent.getStringExtra("productVariation");
        String productstatus = intent.getStringExtra("status");
        String groupCode = intent.getStringExtra("groupCode");
        String firstname = SharedPreference.getSharedPreference(PizzaListDetaill.this).setFname();
        String lastname = SharedPreference.getSharedPreference(PizzaListDetaill.this).setLname();
        String customerID = SharedPreference.getSharedPreference(PizzaListDetaill.this).setEmail();
        if(intent != null){
            Glide.with(PizzaListDetaill.this).load(image).into(imageView);
            productName.setText(productname);
            status.setText(productstatus);
            String[] splitCode = groupCode.split(",");
            String[] splitVariation = groupVariation.split(",");
            String[] splitPrice = groupPrice.split(",");

            String product1 = splitVariation[0]+"          ₱ "+splitPrice[0]+" .00"+splitCode[0];
            SpannableString spannableCode1 = new SpannableString(product1);
            ForegroundColorSpan fcsWhite1 = new ForegroundColorSpan(Color.WHITE);
            spannableCode1.setSpan(fcsWhite1,24,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            medium.setText(spannableCode1);

            String product2 = splitVariation[1]+"          ₱ "+splitPrice[1]+" .00"+splitCode[1];
            SpannableString spannableCode2 = new SpannableString(product2);
            ForegroundColorSpan fcsWhite2 = new ForegroundColorSpan(Color.WHITE);
            spannableCode2.setSpan(fcsWhite2,25,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            large.setText(spannableCode2);
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
        }
        btnPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = customerId.getText().toString();
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                String product = productName.getText().toString();
                String add_ons = pizza_addons.getEditText().getText().toString();
                int selectedSize = variation.getCheckedRadioButtonId();
                RadioButton size = findViewById(selectedSize);
                String getSize = size.getText().toString();
                String getCode = getSize.replace("Medium","  ");
                String variation = getSize.replace("          ₱","       ,");
                int price = 0;

                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,getCode,product,variation,firstName,lastName,price,add_ons);
                cartModelCall.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success =response.body().getSuccess();
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Cart Added Successfully",Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(PizzaListDetaill.this,PizzaActivity.class));
            }
        });
    }
}