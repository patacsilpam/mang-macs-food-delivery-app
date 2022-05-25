package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilaoListDetail extends AppCompatActivity {
    private RelativeLayout priceLayout;
    private ImageView imageView;
    private TextView productName,price,productCode,singleVariation;
    private TextView txt_arrow_back,status,customerId,fname,lname;
    private TextInputLayout bilaoAddOns;
    private EditText quantity;
    private Button btnAddtoCart,btnIncrement,btnDecrement;
    private RadioButton radioButton;
    private RadioGroup rdVariation;
    private int count = 1;
    private String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilao_list_detail);
        priceLayout = findViewById(R.id.priceLayout);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.bilaoproductName);
        bilaoAddOns = findViewById(R.id.bilaoadd_ons);
        singleVariation = findViewById(R.id.singleVariation);
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
        btnIncrement = findViewById(R.id.increment);
        btnDecrement = findViewById(R.id.decrement);
        btnDecrement.setEnabled(false); //set button decrement not clickable
        //button increment
        IncrementDecrement();
        DisplayProductDetails();
        Back();
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
        String productname = intent.getStringExtra("productName");
        String productprice = intent.getStringExtra("groupPriceBilao");
        String productvariation = intent.getStringExtra("productVariationBilao");
        String newProductStatus = intent.getStringExtra("status");
        String groupCode = intent.getStringExtra("groupCode");
        String customerID = SharedPreference.getSharedPreference(BilaoListDetail.this).setEmail();
        String firstname = SharedPreference.getSharedPreference(BilaoListDetail.this).setFname();
        String lastname = SharedPreference.getSharedPreference(BilaoListDetail.this).setLname();
        if(intent != null){
            Glide.with(BilaoListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
            String[] splitPrice = productprice.split(","); //split product price
            String[] splitVariation = productvariation.split(",");//split product variation or category
            String[] splitCode = groupCode.split(",");//split product code
            String[] splitStatus = newProductStatus.split(",");
            if(splitVariation.length == 1){
                status.setText(newProductStatus);
                productCode.setText(groupCode);
                price.setText(productprice);
                singleVariation.setText(productvariation);
                status.setVisibility(View.VISIBLE);
                singleVariation.setVisibility(View.VISIBLE);
                priceLayout.setVisibility(View.VISIBLE);
                rdVariation.setVisibility(View.GONE);
                String statusColor = status.getText().toString();
                if (statusColor.equals("In Stock")){
                    status.setTextColor(Color.parseColor("#36c76b"));
                }
                else{
                    status.setTextColor(Color.RED);
                }

            }
            else{
                for (int i = 0; i<splitVariation.length; i++){
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(splitVariation[i]);
                    rdVariation.addView(radioButton);
                    rdVariation.setVisibility(View.VISIBLE);
                    String statusColor = status.getText().toString();
                    if (statusColor.equals("In Stock")){
                        status.setTextColor(Color.parseColor("#36c76b"));
                    }
                    else{
                        status.setTextColor(Color.RED);
                    }
                }
            }
            rdVariation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int selectedVariation = rdVariation.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedVariation);
                    String variation = radioButton.getText().toString();
                    priceLayout.setVisibility(View.VISIBLE);
                    status.setVisibility(View.VISIBLE);
                    String[] splitPrice = productprice.split(",");
                    String[] splitStatus = newProductStatus.split(",");
                    String[] splitCode = groupCode.split(",");
                    try {
                        if(variation.equals("7-10 Person")){
                            price.setText(splitPrice[0]);
                            status.setText(splitStatus[0]);
                            productCode.setText(splitCode[0]);
                            String statusColor = status.getText().toString();
                            if (statusColor.equals("In Stock")){
                                status.setTextColor(Color.parseColor("#36c76b"));
                            }
                            else{
                                status.setTextColor(Color.RED);
                            }
                        }
                        else if(variation.equals("10-15 Person")){
                            price.setText(splitPrice[1]);
                            status.setText(splitStatus[1]);
                            productCode.setText(splitCode[1]);
                            String statusColor = status.getText().toString();
                            if (statusColor.equals("In Stock")){
                                status.setTextColor(Color.parseColor("#36c76b"));
                            }
                            else{
                                status.setTextColor(Color.RED);
                            }
                        }
                        else{
                            price.setText(splitPrice[2]);
                            status.setText(splitStatus[2]);
                            productCode.setText(splitCode[2]);
                            String statusColor = status.getText().toString();
                            if (statusColor.equals("In Stock")){
                                status.setTextColor(Color.parseColor("#36c76b"));
                            }
                            else{
                                status.setTextColor(Color.RED);
                            }
                        }
                    }
                    catch (Exception e){

                    }

                }
            });
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
                else if(rdVariation.getVisibility() == View.GONE && rdVariation.getCheckedRadioButtonId() == -1){
                    String id = customerId.getText().toString();
                    String code = productCode.getText().toString();
                    String product = productName.getText().toString();
                    String add_ons = bilaoAddOns.getEditText().getText().toString();
                    String getSingleVar = singleVariation.getText().toString();
                    int prices = Integer.parseInt(price.getText().toString());
                    int number = Integer.parseInt(quantity.getText().toString());
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,getSingleVar,firstName,lastName,prices,number,add_ons,image);
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
                else{
                    String id = customerId.getText().toString();
                    String code = productCode.getText().toString();
                    String product = productName.getText().toString();
                    String add_ons = bilaoAddOns.getEditText().getText().toString();
                    int selectedSize = rdVariation.getCheckedRadioButtonId();
                    RadioButton size = findViewById(selectedSize);
                    String variation = size.getText().toString();
                    int prices = Integer.parseInt(price.getText().toString());
                    int number = Integer.parseInt(quantity.getText().toString());
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,variation,firstName,lastName,prices,number,add_ons,image);
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