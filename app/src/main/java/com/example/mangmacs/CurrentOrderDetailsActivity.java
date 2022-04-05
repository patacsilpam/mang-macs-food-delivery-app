package com.example.mangmacs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.mangmacs.adapter.NewOrdersDetailAdapter;
import com.example.mangmacs.adapter.OrderListsAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderDetailsActivity extends AppCompatActivity implements OrdersListener {
    private TextView orderStatus,orderNumber,totalAmount,arrowBack;
    private TextView dineInName,dineInEmail,pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress,devLabelAddress;
    private CardView deliveryDetails,pickUpDetails,dineInDetails;
    private RecyclerView newOrderDetailLists;
    private String newAccountName,newEmail,newCustomerName,newPhoneNumber,newLabelAddress,newAddress,newOrderType,newOrderStatus,newOrderNumber;
    private ArrayList<String> totalAmountList = new ArrayList<>();
    private List<CurrentOrdersModel> currentOrdersModels;
    private NewOrdersDetailAdapter newOrdersDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);
        arrowBack = findViewById(R.id.arrow_back);
        orderNumber = findViewById(R.id.orderNumber);
        orderStatus = findViewById(R.id.orderStatus);
        totalAmount = findViewById(R.id.totalAmount);
        dineInDetails = findViewById(R.id.dineInDetails);
        dineInName = findViewById(R.id.dineInName);
        dineInEmail = findViewById(R.id.dineInEmail);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        pickUpName = findViewById(R.id.pickUpName);
        pickUpEmail = findViewById(R.id.pickUpEmail);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryPhoneNum = findViewById(R.id.deliveryPhoneNum);
        devAddress = findViewById(R.id.devAddress);
        devLabelAddress = findViewById(R.id.labelAddress);
        deliveryDetails = findViewById(R.id.deliveryDetails);
        deliveryDetails.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        dineInDetails.setVisibility(View.VISIBLE);
        newOrderDetailLists = findViewById(R.id.newOrderDetailLists);
        newOrderDetailLists.setHasFixedSize(true);
        newOrderDetailLists.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dismissOrder();
        showOrders();
        Back();
    }
    private void dismissOrder() {
        Intent intent = getIntent();
        newAccountName = intent.getStringExtra("customerName");
        newEmail = intent.getStringExtra("email");
        newCustomerName = intent.getStringExtra("customerName");
        newPhoneNumber = intent.getStringExtra("phoneNumber");
        newLabelAddress = intent.getStringExtra("labelAddress");
        newAddress = intent.getStringExtra("address");
        newOrderType = intent.getStringExtra("orderType");
        newOrderStatus = intent.getStringExtra("orderStatus");
        newOrderNumber = intent.getStringExtra("orderNumber");
        orderStatus.setText(newOrderStatus);
        totalAmount.setText("₱ ".concat(String.valueOf(totalAmountList)).concat(".00"));
        if (newOrderType.equals("Pick Up")){
            pickUpName.setText(newAccountName);
            pickUpEmail.setText(newEmail);
            deliveryDetails.setVisibility(View.GONE);
            dineInDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);
        }
        else if (newOrderType.equals("Deliver")){
            deliveryName.setText(newCustomerName);
            deliveryPhoneNum.setText(newPhoneNumber);
            devAddress.setText(newAddress);
            devLabelAddress.setText(newLabelAddress);
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
        orderNumber.setText(newOrderNumber);
        String email = SharedPreference.getSharedPreference(this).setEmail();
        String number = orderNumber.getText().toString();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> call = apiInterface.getNewOrderDetails(email,number);
        call.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                currentOrdersModels = response.body();
                newOrdersDetailAdapter = new NewOrdersDetailAdapter(CurrentOrderDetailsActivity.this,currentOrdersModels,CurrentOrderDetailsActivity.this);
                newOrderDetailLists.setAdapter(newOrdersDetailAdapter);
            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {

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
    public void onTotalAmountChange(ArrayList<String> totalAmount) {
        totalAmountList = totalAmount;
    }


}