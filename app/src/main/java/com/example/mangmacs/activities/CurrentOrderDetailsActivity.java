package com.example.mangmacs.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.NewOrdersDetailAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderDetailsActivity extends AppCompatActivity implements OrdersListener{
    private TextView orderNumber,orderType,totalAmount,paymentNumber,txtPrepStatus,arrowBack,estTime,changeableStatus;
    private TextView pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress,devLabelAddress,deliveryFee,courierName;
    private CardView deliveryDetails,pickUpDetails,deliveryFeeDetails;
    private Button btnCancelOrder;
    private ImageView pendingIcon,processingIcon,forDeliveryIcon;
    private View line1,line3,line4;
    private RecyclerView newOrderDetailLists;
    private RelativeLayout cancelOrderLayout;
    private LinearLayout courierLayout;
    private List<CurrentOrdersModel> currentOrdersModels;
    private NewOrdersDetailAdapter newOrdersDetailAdapter;
    private ArrayList<String> productCodeList = new ArrayList<>();
    private ArrayList<String> productList = new ArrayList<>();
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<String> variationList = new ArrayList<>();
    private String newAccountName,newOrderQuantity,newEmail,newRecipientName,newPhoneNumber,newLabelAddress,newAddress,newOrderType,newOrderStatus,newOrderNumber,newDeliveryTime,newPaymentMethod,newPaymentNumber,newDeliveryFee,newRequiredTme,newRequiredDate,newWaitingTime,newCourierName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);
        arrowBack = findViewById(R.id.arrow_back);
        changeableStatus = findViewById(R.id.changeableStatus);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);
        estTime = findViewById(R.id.estTime);
        txtPrepStatus = findViewById(R.id.txtPrepStatus);
        orderNumber = findViewById(R.id.orderNumber);
        orderType = findViewById(R.id.orderType);
        totalAmount = findViewById(R.id.totalAmount);
        paymentNumber = findViewById(R.id.paymentNumber);
        pendingIcon = findViewById(R.id.pendingIcon);
        processingIcon = findViewById(R.id.processingIcon);
        forDeliveryIcon = findViewById(R.id.forDeliveryIcon);
        line1 = findViewById(R.id.line1);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        courierLayout = findViewById(R.id.courierLayout);
        courierName = findViewById(R.id.courierName);
        cancelOrderLayout = findViewById(R.id.cancelOrderLayout);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        pickUpName = findViewById(R.id.pickUpName);
        pickUpEmail = findViewById(R.id.pickUpEmail);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryPhoneNum = findViewById(R.id.deliveryPhoneNum);
        devAddress = findViewById(R.id.devAddress);
        devLabelAddress = findViewById(R.id.labelAddress);
        deliveryDetails = findViewById(R.id.deliveryDetails);
        deliveryFee = findViewById(R.id.deliveryFee);
        deliveryFeeDetails = findViewById(R.id.deliveryFeeDetails);
        deliveryFeeDetails.setVisibility(View.VISIBLE);
        deliveryDetails.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        newOrderDetailLists = findViewById(R.id.newOrderDetailLists);
        newOrderDetailLists.setHasFixedSize(true);
        newOrderDetailLists.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dismissOrder();
        showOrders();
        cancelOrders();
        Back();
    }
    //getting data from pass intent
    @SuppressLint("ResourceAsColor")
    private void dismissOrder() {
        Intent intent = getIntent();
        newOrderQuantity = intent.getStringExtra("orderQuantity");
        newDeliveryTime = intent.getStringExtra("deliveryTime");
        newAccountName = intent.getStringExtra("customerName");
        newEmail = intent.getStringExtra("email");
        newRecipientName = intent.getStringExtra("recipientName");
        newPhoneNumber = intent.getStringExtra("phoneNumber");
        newLabelAddress = intent.getStringExtra("labelAddress");
        newAddress = intent.getStringExtra("address");
        newOrderType = intent.getStringExtra("orderType");
        newOrderStatus = intent.getStringExtra("orderStatus");
        newOrderNumber = intent.getStringExtra("orderNumber");
        newPaymentMethod = intent.getStringExtra("paymentMethod");
        newDeliveryFee = intent.getStringExtra("deliveryFee");
        newRequiredTme = intent.getStringExtra("requiredTime");
        newRequiredDate = intent.getStringExtra("requiredDate");
        newWaitingTime = intent.getStringExtra("waitingTime");
        newCourierName = intent.getStringExtra("courierName");
        newPaymentNumber = intent.getStringExtra("paymentNumber");
        orderType.setText(newOrderType);
        orderNumber.setText("#".concat(newOrderNumber));
        deliveryFee.setText(newDeliveryFee);
        paymentNumber.setText(newPaymentNumber);
        String OrderType = orderType.getText().toString();
        //show food prep status
        if ((Integer.parseInt(newOrderQuantity) > 2)) {
            txtPrepStatus.setVisibility(View.VISIBLE);
        }
        //show order status
        if (newOrderStatus.equals("Pending")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        if(newOrderStatus.equals("Order Processing")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            processingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line3.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        if(newOrderStatus.equals("Out for Delivery") || newOrderStatus.equals("Ready for Pick Up")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            processingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            forDeliveryIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line3.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line4.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        //show customer details
        if (OrderType.equals("Pick Up")){
            pickUpName.setText(newAccountName);
            pickUpEmail.setText(newEmail);
            deliveryDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);
            deliveryFeeDetails.setVisibility(View.GONE);
            changeableStatus.setText("Ready\nfor\nPick up");
            courierLayout.setVisibility(View.GONE);
        }
        else if (OrderType.equals("Deliver")){
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
        //show estimated delivery time
        if(newRequiredTme.contains("now")){

            estTime.setText(newWaitingTime);
        }
        else{
            estTime.setText(newRequiredDate.concat(" ").concat(newRequiredTme));
        }
    }
    //show customer orders in recyclerview
    private void showOrders(){
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> call = apiInterface.getNewOrderDetails(email,newOrderNumber);
        call.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                currentOrdersModels = response.body();
                newOrdersDetailAdapter = new NewOrdersDetailAdapter(CurrentOrderDetailsActivity.this,currentOrdersModels,CurrentOrderDetailsActivity.this);
                newOrderDetailLists.setAdapter(newOrdersDetailAdapter);
                newOrdersDetailAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {

            }
        });
    }


    //arrow back button
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
        productList = products;
    }

    @Override
    public void onProductCategoryChange(ArrayList<String> category) {
        categoryList = category;

    }

    @Override
    public void onProductCodeChange(ArrayList<String> productCode) {
        //get product code index[0] to cancel orders
        productCodeList = productCode;
        String code = productCode.get(0);
       // Toast.makeText(this, String.valueOf(productCodeList), Toast.LENGTH_SHORT).show();
        if (newOrderStatus.equals("Pending")){
            cancelOrderLayout.setVisibility(View.VISIBLE);
            btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CurrentOrderDetailsActivity.this);
                    alertDialogBuilder.setCancelable(false)
                            .setMessage("Are you sure you want to cancel this order?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                    Call<CurrentOrdersModel> callOrder = apiInterface.cancelOrder(code,productList,quantityList,categoryList,variationList);
                                    callOrder.enqueue(new Callback<CurrentOrdersModel>() {
                                        @Override
                                        public void onResponse(Call<CurrentOrdersModel> call, Response<CurrentOrdersModel> response) {
                                            if(response.body() != null){
                                                String success = response.body().getSuccess();
                                                if (success.equals("1")){
                                                    startActivity(new Intent(getApplicationContext(),MyOrdersActivity.class));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CurrentOrdersModel> call, Throwable t) {
                                            startActivity(new Intent(getApplicationContext(),MyOrdersActivity.class));
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
            cancelOrderLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onVariationChange(ArrayList<String> variations) {
        variationList = variations;
    }

    @Override
    public void onQuantityChange(ArrayList<Integer> quantity) {
        quantityList = quantity;

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
        //display total amount
        int totalOrder = Integer.parseInt(amount);
        int totalPayment = totalOrder + Integer.parseInt(newDeliveryFee);
        totalAmount.setText("₱ ".concat(String.valueOf(totalPayment)).concat(".00"));
    }

    @Override
    public void onPreparationTimeChange(ArrayList<String> preparationTime) {

    }
    private void cancelOrders(){
        //String code = productCodeList.get(0);

    }

}