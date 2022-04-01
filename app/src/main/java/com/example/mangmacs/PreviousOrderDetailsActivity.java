package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.activities.MyOrdersActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.io.ByteArrayOutputStream;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrderDetailsActivity extends AppCompatActivity {
    private TextView orderStatus,orderType,textProduct,textVariation,textPrice,items,totalAmount,arrowBack;
    private TextView dineInName,dineInEmail,pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress;
    private TextView newOrderTime,newPaymentTime,newShipTime,newCompletedTime,textPayment;
    private ImageView imgProduct,newPaymentPhoto;
    private CardView deliveryAddress,pickUpDetails,dineInDetails;
    private Button buyAgain;
    private String productCode,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_details);
        textPayment = findViewById(R.id.textPayment);
        orderStatus = findViewById(R.id.orderStatus);
        orderType = findViewById(R.id.orderType);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryPhoneNum = findViewById(R.id.deliveryPhoneNum);
        devAddress = findViewById(R.id.devAddress);
        dineInName = findViewById(R.id.dineInName);
        dineInEmail = findViewById(R.id.dineInEmail);
        pickUpName = findViewById(R.id.pickUpName);
        pickUpEmail = findViewById(R.id.pickUpEmail);
        textPrice = findViewById(R.id.textPrice);
        textProduct = findViewById(R.id.textProduct);
        textVariation = findViewById(R.id.textVariation);
        items = findViewById(R.id.items);
        totalAmount = findViewById(R.id.totalAmount);
        arrowBack = findViewById(R.id.arrow_back);
        imgProduct = findViewById(R.id.imgProduct);
        buyAgain = findViewById(R.id.buyAgain);
        newPaymentPhoto = findViewById(R.id.newPaymentPhoto);
        newOrderTime = findViewById(R.id.orderTime);
        newPaymentTime = findViewById(R.id.paymentTime);
        newShipTime= findViewById(R.id.shipTime);
        newCompletedTime = findViewById(R.id.completedTime);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        dineInDetails = findViewById(R.id.dineInDetails);
        deliveryAddress.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        dineInDetails.setVisibility(View.VISIBLE);
        checkOrderType();
        reOrder();
        Back();
    }
    private void checkOrderType(){
       Intent intent = getIntent();
       String orderType = intent.getStringExtra("orderType");
        if (orderType.equals("Pick Up")){
            deliveryAddress.setVisibility(View.GONE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);

        }
        else if (orderType.equals("Delivery")){
            deliveryAddress.setVisibility(View.VISIBLE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.GONE);
        }
        else{
            dineInDetails.setVisibility(View.VISIBLE);
            pickUpDetails.setVisibility(View.GONE);
            deliveryAddress.setVisibility(View.GONE);
        }
    }
    private void reOrder() {
        buyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "pammpatacsil@gmail.com";
                String fname = "Pamela";
                String lname = "Patacsil";
                String product = textProduct.getText().toString();
                String variation = textVariation.getText().toString();
                String add_ons = "";
                int newPrice = Integer.parseInt(textPrice.getText().toString());
                int newItems = Integer.parseInt(items.getText().toString());
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> callCart = apiInterface.addcart(email,productCode,product,variation,fname,lname,newPrice,newItems,add_ons,image);
                callCart.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if (response.body() != null){
                            String success = response.body().getSuccess();
                            if (success.equals("1")){
                                startActivity(new Intent(getApplicationContext(), CartActivity.class));
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
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            }
        });
    }
}