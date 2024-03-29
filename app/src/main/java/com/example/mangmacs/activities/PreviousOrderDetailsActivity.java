package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.PreviousDetailAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrderDetailsActivity extends AppCompatActivity implements OrdersListener {
    private TextView orderNumber,orderType,totalAmount,paymentNumber,arrowBack,changeableStatus,txt_total_amount;
    private TextView pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress,devLabelAddress,deliveryFee,courierName;
    private TextView orderId,orderTime,completedTime;
    private RecyclerView prevOrderDetailList;
    private Button orderReceived;
    private RelativeLayout orderReceivedLayout;
    private LinearLayout courierLayout;
    private CardView deliveryDetails,pickUpDetails,deliveryFeeDetails,orderStatusLayout,cancelOrderLayout;
    private String newAccountName,newEmail,newRecipientName,newPhoneNumber,newLabelAddress,newAddress,newOrderType,newOrderStatus,newOrderNumber,newOrderDate,newDevTime,newCompletedTime,newPaymentMethod,newPaymentNumber,newDeliveryFee,newCourierName;
    private List<CurrentOrdersModel> prevOrderModel;
    private PreviousDetailAdapter previousDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_details);
        arrowBack = findViewById(R.id.arrow_back);
        orderNumber = findViewById(R.id.orderNumber);
        orderType = findViewById(R.id.orderType);
        courierName = findViewById(R.id.courierName);
        totalAmount = findViewById(R.id.totalAmount);
        txt_total_amount = findViewById(R.id.txt_total_amount);
        paymentNumber = findViewById(R.id.paymentNumber);
        changeableStatus = findViewById(R.id.changeableStatus);
        orderStatusLayout = findViewById(R.id.orderStatusLayout);
        orderReceivedLayout = findViewById(R.id.orderReceivedLayout);
        courierLayout = findViewById(R.id.courierLayout);
        cancelOrderLayout = findViewById(R.id.cancelOrderLayout);
        orderReceived = findViewById(R.id.orderReceived);
        deliveryDetails = findViewById(R.id.deliveryAddress);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryPhoneNum = findViewById(R.id.deliveryPhoneNum);
        devAddress = findViewById(R.id.devAddress);
        devLabelAddress = findViewById(R.id.labelAddress);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        pickUpName = findViewById(R.id.pickUpName);
        pickUpEmail = findViewById(R.id.pickUpEmail);
        orderId = findViewById(R.id.orderId);
        orderTime = findViewById(R.id.orderTime);
        completedTime = findViewById(R.id.completedTime);
        deliveryFee = findViewById(R.id.deliveryFee);
        deliveryFeeDetails = findViewById(R.id.deliveryFeeDetails);
        deliveryFeeDetails.setVisibility(View.VISIBLE);
        deliveryDetails.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        prevOrderDetailList = findViewById(R.id.prevOrderDetailLists);
        prevOrderDetailList.setHasFixedSize(true);
        prevOrderDetailList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        checkOrderType();
        showOrders();
        setOrderReceived();
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
        newPaymentMethod = intent.getStringExtra("paymentMethod");
        newDeliveryFee = intent.getStringExtra("deliveryFee");
        newCourierName = intent.getStringExtra("courierName");
        newPaymentNumber = intent.getStringExtra("paymentNumber");
        orderId.setText("#".concat(newOrderNumber));
        orderTime.setText(newOrderDate);
        completedTime.setText(newCompletedTime);
        orderType.setText(newOrderType);
        orderNumber.setText(newOrderNumber);
        deliveryFee.setText(newDeliveryFee);
        paymentNumber.setText(newPaymentNumber);
        //show order received button if order status is equal to order completed
        switch (newOrderStatus){
            case "Order Completed":
                cancelOrderLayout.setVisibility(View.GONE);
                break;
            case "Order Received":
                orderReceivedLayout.setVisibility(View.GONE);
                cancelOrderLayout.setVisibility(View.GONE);
                break;
            case "Invalid Payment":
            case "Out of Stock":
            case "Cancelled":
                orderReceivedLayout.setVisibility(View.GONE);
                orderStatusLayout.setVisibility(View.GONE);
                break;
            default:
                return;
        }

        //show customer information according to order type
        if (newOrderType.equals("Pick Up")){
            pickUpName.setText(newAccountName);
            pickUpEmail.setText(newEmail);
            deliveryDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);
            deliveryFeeDetails.setVisibility(View.GONE);
            changeableStatus.setText("Ready\nfor\nPick pick up");
            courierLayout.setVisibility(View.GONE);
        }
        else if (newOrderType.equals("Deliver")){
            deliveryName.setText(newRecipientName);
            deliveryPhoneNum.setText(newPhoneNumber);
            devAddress.setText(newAddress);
            devLabelAddress.setText(newLabelAddress);
            courierName.setText(newCourierName);
            deliveryDetails.setVisibility(View.VISIBLE);
            pickUpDetails.setVisibility(View.GONE);
            deliveryFeeDetails.setVisibility(View.VISIBLE);
        }
        else{
            pickUpDetails.setVisibility(View.GONE);
            deliveryDetails.setVisibility(View.GONE);
            deliveryFeeDetails.setVisibility(View.GONE);
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
    private void setOrderReceived(){
       orderReceived.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
               Call<CartModel> call = apiInterface.changeOrderStatus(newOrderNumber);
               call.enqueue(new Callback<CartModel>() {
                   @Override
                   public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                       if (response.body() != null){
                           String success = response.body().getSuccess();
                           if (success.equals("1")){
                               startActivity(new Intent(PreviousOrderDetailsActivity.this,MyOrdersActivity.class));
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

    @Override
    public void onProductsChange(ArrayList<String> products) {

    }

    @Override
    public void onProductCategoryChange(ArrayList<String> category) {

    }

    @Override
    public void onProductCodeChange(ArrayList<String> productCode) {

    }

    @Override
    public void onVariationChange(ArrayList<String> variations) {

    }

    @Override
    public void onQuantityChange(ArrayList<Integer> quantity) {

    }

    @Override
    public void onAddOnsChange(ArrayList<String> addOns) {

    }

    @Override
    public void onAddOnsFeeChange(ArrayList<Integer> addaonsFee) {

    }

    @Override
    public void onSpecialRequest(ArrayList<String> specialRequest) {

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
        int devFee = Integer.parseInt(deliveryFee.getText().toString());
        int totalOrder = Integer.parseInt(amount) + devFee;
        totalAmount.setText("₱ ".concat(String.valueOf(totalOrder)).concat(".00"));
        txt_total_amount.setText("₱ ".concat(amount).concat(".00"));
    }

    @Override
    public void onPreparationTimeChange(ArrayList<String> preparationTime) {

    }
}