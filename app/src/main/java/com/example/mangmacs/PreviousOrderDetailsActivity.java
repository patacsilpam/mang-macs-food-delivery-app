package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;

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

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrderDetailsActivity extends AppCompatActivity {
    private TextView orderStatus,customerName,phoneNumber,address,textProduct,textVariation,textPrice,items,totalAmount,arrowBack;
    private TextView newOrderTime,newPaymentTime,newShipTime,newCompletedTime,textPayment;
    private ImageView imgProduct,newPaymentPhoto;
    private Button buyAgain;
    private String productCode,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_details);
        textPayment = findViewById(R.id.textPayment);
        orderStatus = findViewById(R.id.orderStatus);
        customerName = findViewById(R.id.customerName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
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
        showOrderDetails();
        Back();
    }
    private void showOrderDetails(){
        Intent intent = getIntent();
        String status = intent.getStringExtra("orderStatus");
        String name = intent.getStringExtra("customerName");
        String phone_number = intent.getStringExtra("phoneNumber");
        String customer_address = intent.getStringExtra("address");
        String price = intent.getStringExtra("price");
        productCode = intent.getStringExtra("productCode");
        String product = intent.getStringExtra("product");
        String variation = intent.getStringExtra("variation");
        String quantity = intent.getStringExtra("quantity");
        String amount = intent.getStringExtra("totalAmount");
        image = intent.getStringExtra("imgProduct");
        String paymentPhoto = intent.getStringExtra("paymentPhoto");
        String orderTime = intent.getStringExtra("orderTime");
        String shipTime = intent.getStringExtra("shipTime");
        String paymentTime = intent.getStringExtra("paymentTime");
        String deliveredTime = intent.getStringExtra("deliveredTime");
        //display order details
        orderStatus.setText(status);
        customerName.setText(name);
        phoneNumber.setText(phone_number);
        address.setText(customer_address);
        textPrice.setText(price);
        textProduct.setText(product);
        textVariation.setText(variation);
        items.setText(quantity);
        totalAmount.setText(amount);
        newOrderTime.setText(orderTime);
        newShipTime.setText(shipTime);
        newPaymentTime.setText(paymentTime);
        newCompletedTime.setText(deliveredTime);
        Glide.with(getApplicationContext()).load(image).into(imgProduct);
        byte[] bytes = Base64.decode(paymentPhoto,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        newPaymentPhoto.setImageBitmap(bitmap);
        //textPayment.setText(paymentPhoto);
        reOrder();
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