package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.CartAdapter;
import com.example.mangmacs.adapter.OrderListsAdapter;
import com.example.mangmacs.adapter.OrderListsAdapter.ProductViewHolder;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class DineInActivity extends AppCompatActivity implements OrdersListener {
    private TextView arrowBack,total,fullName,emailAddress;
    private RecyclerView recyclerViewOrder;
    private Button placeOrder;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String strDate,totalPrice;
    private ArrayList<String> orderLists = new ArrayList<>();
    private ArrayList<String> productCodeList = new ArrayList<>();
    private ArrayList<String> variationList = new ArrayList<>();
    private ArrayList<String> quantityList = new ArrayList<>();
    private ArrayList<String> addOnsList = new ArrayList<>();
    private ArrayList<String> subTotalList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<String> imgProductList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        fullName = findViewById(R.id.fullname);
        emailAddress = findViewById(R.id.email);
        placeOrder = findViewById(R.id.placeOrder);
        recyclerViewOrder = findViewById(R.id.recyclerviewOrder);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        Date date = new Date();  //get date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        strDate = dateFormatter.format(date);
        showOrders();
        Back();
        PlaceOrder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));

    }

    private void showOrders() {
        String fname = SharedPreference.getSharedPreference(getApplicationContext()).setFname();
        String lname = SharedPreference.getSharedPreference(getApplicationContext()).setLname();
        String accountName = fname +" "+ lname;
        String email = SharedPreference.getSharedPreference(this).setEmail();
        fullName.setText(accountName);
        emailAddress.setText(email);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
        getUserCart.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                orderModelLists = response.body();
                orderListsAdapter = new OrderListsAdapter(DineInActivity.this,orderModelLists,DineInActivity.this);
                recyclerViewOrder.setAdapter(orderListsAdapter);

            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

            }
        });
    }

    private void Back() {
        //arrow back button
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DineInActivity.this,OrderModeActivity.class));
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalPrice = intent.getStringExtra("totalorderprice");
            total.setText(totalPrice);
        }
    };
    private void PlaceOrder() {
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String requiredTime = "now";
                String accountName = fullName.getText().toString();
                String address = "";
                String labelAddress = "";
                String email = emailAddress.getText().toString();
                String phoneNumber = "";
                String paymentPhoto = "not required";
                String orderStatus = "Pending";
                String orderType = "Dine in";
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> insertOrder = apiInterface.insertOrder(productCodeList,accountName,"",address,labelAddress,email,phoneNumber,orderLists,variationList,quantityList,addOnsList,priceList,subTotalList,totalPrice,paymentPhoto,imgProductList,orderType,orderStatus,strDate,requiredTime);
                insertOrder.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if (response.body() != null) {
                            String success = response.body().getSuccess();
                            if (success.equals("1")) {
                                startActivity(new Intent(getApplicationContext(), home_activity.class));
                                Toast.makeText(getApplicationContext(), "Ordered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                        @Override
                        public void onFailure(Call<CartModel> call, Throwable t) {
                            startActivity(new Intent(getApplicationContext(),home_activity.class));
                            Toast.makeText(getApplicationContext(),"Ordered Successfully",Toast.LENGTH_SHORT).show();
                        }});
            }
        });
    }

    @Override
    public void onProductsChange(ArrayList<String> products) {
        orderLists = products;
    }
    public void onProductCodeChange(ArrayList<String> productCodes){
        productCodeList = productCodes;
    }
    @Override
    public void onVariationChange(ArrayList<String> variations) {
        variationList = variations;
    }

    @Override
    public void onQuantityChange(ArrayList<String> quantity) {
            quantityList = quantity;
    }

    @Override
    public void onAddOnsChange(ArrayList<String> addOns) {
        addOnsList = addOns;
    }

    @Override
    public void onSubTotalChange(ArrayList<String> subTotal) {
        subTotalList = subTotal;
    }

    @Override
    public void onPriceChange(ArrayList<String> price) {
        priceList = price;
    }

    @Override
    public void onImgProductChange(ArrayList<String> imgProduct) {
        imgProductList = imgProduct;
    }

    @Override
    public void onCustomerIdChange(String customerId) {

    }

    @Override
    public void onTotalAmountChange(ArrayList<String> totalAmount) {

    }


}