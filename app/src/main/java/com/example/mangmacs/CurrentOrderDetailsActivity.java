package com.example.mangmacs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.activities.MyOrdersActivity;
import com.example.mangmacs.activities.ViewPaymentActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderDetailsActivity extends AppCompatActivity {
    private TextView orderStatus,textProduct,textVariation,textPrice,items,totalAmount,newOrderId,arrowBack,neworderType;
    private TextView dineInName,dineInEmail,pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress,textPayment;
    private ImageView imgProduct,newPaymentPhoto;
    private Button cancelOrder;
    private CardView deliveryAddress,pickUpDetails,dineInDetails;
    private  String paymentPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);
        textPayment = findViewById(R.id.textPayment);
        orderStatus = findViewById(R.id.orderStatus);
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
        imgProduct = findViewById(R.id.imgProduct);
        newPaymentPhoto = findViewById(R.id.paymentPhoto);
        arrowBack = findViewById(R.id.arrow_back);
        cancelOrder = findViewById(R.id.cancelOrder);
        newOrderId = findViewById(R.id.newOrderId);
        neworderType = findViewById(R.id.orderType);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        dineInDetails = findViewById(R.id.dineInDetails);
        deliveryAddress.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        dineInDetails.setVisibility(View.VISIBLE);
        cancelOrder.setEnabled(false);
        showOrderDetails();
        Back();
        String imgString = textPayment.getText().toString();
        byte [] encodeByte=Base64.decode(imgString.getBytes(), 0);
        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        newPaymentPhoto.setImageBitmap(bitmap);
    }

    private void showOrderDetails(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String status = intent.getStringExtra("orderStatus");
        String name = intent.getStringExtra("customerName");
        String phone_number = intent.getStringExtra("phoneNumber");
        String customer_address = intent.getStringExtra("address");
        String price = intent.getStringExtra("price");
        String product = intent.getStringExtra("product");
        String variation = intent.getStringExtra("variation");
        String quantity = intent.getStringExtra("quantity");
        String amount = intent.getStringExtra("totalAmount");
        String image = intent.getStringExtra("imgProduct");
        paymentPhoto = intent.getStringExtra("paymentPhoto");
        String orderType = intent.getStringExtra("orderType");
        String fname = SharedPreference.getSharedPreference(this).setFname();
        String lname = SharedPreference.getSharedPreference(this).setLname();
        String email = SharedPreference.getSharedPreference(this).setEmail();
        //display order details
        neworderType.setText(orderType);
        orderStatus.setText(status);
        deliveryName.setText(name);
        deliveryPhoneNum.setText(phone_number);
        devAddress.setText(customer_address);
        textPrice.setText("₱ "+price);
        textProduct.setText(product);
        textVariation.setText(variation);
        items.setText("x"+quantity);
        totalAmount.setText("₱ " +amount);
        newOrderId.setText(id);
        dineInName.setText(fname.concat(" ").concat(lname));
        dineInEmail.setText(email);
        pickUpName.setText(fname.concat(" ").concat(lname));
        pickUpEmail.setText(email);
        Glide.with(getApplicationContext()).load(image).into(imgProduct);
        dismissOrder(); //a method to for cancellation of orders
        textPayment.setText(paymentPhoto);

        //Bitmap b=StringToBitMap(imgString);


    }
    /*public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }*/
    private void dismissOrder() {
        String getOrderType = neworderType.getText().toString();
        if (getOrderType.equals("Pick Up")){
            deliveryAddress.setVisibility(View.GONE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);
        }
        else if (getOrderType.equals("Deliver")){
            deliveryAddress.setVisibility(View.VISIBLE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.GONE);
        }
        else{
            dineInDetails.setVisibility(View.VISIBLE);
            pickUpDetails.setVisibility(View.GONE);
            deliveryAddress.setVisibility(View.GONE);
        }
        String status = orderStatus.getText().toString();
        if (status.equals("Pending")){
            cancelOrder.setEnabled(true);
            cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = newOrderId.getText().toString();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CurrentOrderDetailsActivity.this);
                    alertDialogBuilder.setCancelable(false)
                            .setMessage("Are you sure you want to cancel this order?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                    Call<CartModel> callOrder = apiInterface.cancelOrder(id);
                                    callOrder.enqueue(new Callback<CartModel>() {
                                        @Override
                                        public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                                            if(response.body() != null){
                                                String success = response.body().getSuccess();
                                                if (success.equals("1")){
                                                    Toast.makeText(getApplicationContext(),"Cancelled Order Successfully",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(),MyOrdersActivity.class));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CartModel> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog  alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
        else{
            cancelOrder.setEnabled(false);
            cancelOrder.setBackgroundColor(Color.LTGRAY);
        }
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