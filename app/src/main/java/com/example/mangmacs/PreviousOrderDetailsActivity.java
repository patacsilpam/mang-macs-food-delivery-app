package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.activities.MyOrdersActivity;
import com.example.mangmacs.adapter.PreviousDetailAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrderDetailsActivity extends AppCompatActivity implements OrdersListener {
    private TextView orderStatus,orderNumber,orderType,totalAmount,arrowBack;
    private TextView dineInName,dineInEmail,pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress,devLabelAddress;
    private TextView orderId,orderTime,deliveryTime,completedTime;
    private RecyclerView prevOrderDetailList;
    private CardView deliveryDetails,pickUpDetails,dineInDetails;
    private RelativeLayout devTimeDetails;
    private String newAccountName,newEmail,newRecipientName,newPhoneNumber,newLabelAddress,newAddress,newOrderType,newOrderStatus,newOrderNumber,newOrderDate,newDevTime,newCompletedTime;
    private List<CurrentOrdersModel> prevOrderModel;
    private PreviousDetailAdapter previousDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_details);
        arrowBack = findViewById(R.id.arrow_back);
        orderNumber = findViewById(R.id.orderNumber);
        orderStatus = findViewById(R.id.orderStatus);
        orderType = findViewById(R.id.orderType);
        totalAmount = findViewById(R.id.totalAmount);
        devTimeDetails = findViewById(R.id.devTimeDetails);
        deliveryDetails = findViewById(R.id.deliveryAddress);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryPhoneNum = findViewById(R.id.deliveryPhoneNum);
        devAddress = findViewById(R.id.devAddress);
        devLabelAddress = findViewById(R.id.labelAddress);
        dineInDetails = findViewById(R.id.dineInDetails);
        dineInName = findViewById(R.id.dineInName);
        dineInEmail = findViewById(R.id.dineInEmail);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        pickUpName = findViewById(R.id.pickUpName);
        pickUpEmail = findViewById(R.id.pickUpEmail);
        orderId = findViewById(R.id.orderId);
        orderTime = findViewById(R.id.orderTime);
        deliveryTime = findViewById(R.id.deliveryTime);
        completedTime = findViewById(R.id.completedTime);
        deliveryDetails.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        dineInDetails.setVisibility(View.VISIBLE);
        devTimeDetails.setVisibility(View.GONE);
        prevOrderDetailList = findViewById(R.id.prevOrderDetailLists);
        prevOrderDetailList.setHasFixedSize(true);
        prevOrderDetailList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        checkOrderType();
        reOrder();
        showOrders();
        Back();
    }
    private void checkOrderType(){
       Intent intent = getIntent();
        newAccountName = intent.getStringExtra("customerName");
        newEmail = intent.getStringExtra("email");
        newRecipientName = intent.getStringExtra("recipientName");
        newPhoneNumber = intent.getStringExtra("phoneNumber");
        newLabelAddress = intent.getStringExtra("labelAddress");
        newAddress = intent.getStringExtra("address");
        newOrderType = intent.getStringExtra("orderType");
        newOrderStatus = intent.getStringExtra("orderStatus");
        newOrderNumber = intent.getStringExtra("orderNumber");
        newOrderDate = intent.getStringExtra("orderDate");
        newDevTime = intent.getStringExtra("deliveryTime");
        newCompletedTime = intent.getStringExtra("completedTime");
        orderId.setText(newOrderNumber);
        orderTime.setText(newOrderDate);
        deliveryTime.setText(newDevTime);
        completedTime.setText(newCompletedTime);
        orderStatus.setText(newOrderStatus);
        orderType.setText(newOrderType);
        orderNumber.setText(newOrderNumber);
        String OrderType = orderType.getText().toString();
        if (OrderType.equals("Pick Up")){
            pickUpName.setText(newAccountName);
            pickUpEmail.setText(newEmail);
            deliveryDetails.setVisibility(View.GONE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);

        }
        else if (OrderType.equals("Delivery")){
            deliveryName.setText(newRecipientName);
            deliveryPhoneNum.setText(newPhoneNumber);
            devAddress.setText(newAddress);
            devLabelAddress.setText(newLabelAddress);
            devTimeDetails.setVisibility(View.VISIBLE);
            deliveryDetails.setVisibility(View.VISIBLE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.GONE);
        }
        else{
            dineInName.setText(newAccountName);
            dineInEmail.setText(newEmail);
            dineInDetails.setVisibility(View.VISIBLE);
            pickUpDetails.setVisibility(View.GONE);
            deliveryDetails.setVisibility(View.GONE);
        }
    }
    private void showOrders(){
        String email = SharedPreference.getSharedPreference(this).setEmail();
        String number = orderNumber.getText().toString();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> call = apiInterface.getPreviousOrderDetails(email,number);
        call.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                prevOrderModel = response.body();
                previousDetailAdapter = new PreviousDetailAdapter(PreviousOrderDetailsActivity.this,prevOrderModel,PreviousOrderDetailsActivity.this);
                prevOrderDetailList.setAdapter(previousDetailAdapter);
            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {

            }
        });
    }
    private void reOrder() {
        /*buyAgain.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    private void Back() {
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            }
        });
    }

    @Override
    public void onProductsChange(ArrayList<String> products) {

    }

    @Override
    public void onProductCodeChange(ArrayList<String> productCode) {

    }

    @Override
    public void onVariationChange(ArrayList<String> variations) {

    }

    @Override
    public void onQuantityChange(ArrayList<String> quantity) {

    }

    @Override
    public void onAddOnsChange(ArrayList<String> addOns) {

    }

    @Override
    public void onSubTotalChange(ArrayList<String> subTotal) {

    }

    @Override
    public void onPriceChange(ArrayList<String> price) {

    }

    @Override
    public void onImgProductChange(ArrayList<String> imgProduct) {

    }

    @Override
    public void onCustomerIdChange(String customerId) {

    }

    @Override
    public void onTotalAmountChange(String amount) {
        totalAmount.setText("₱ ".concat(amount).concat(".00"));
    }
}