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
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DineInActivity extends AppCompatActivity {
    private TextView arrowBack,total,customerID,fullName,emailAddress;
    private RecyclerView recyclerViewOrder;
    private Button placeOrder;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String productCode,imgProduct,totalPrice,productName,variation,add_ons,strDate;
    private int quantity,price,subtotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        customerID = findViewById(R.id.customerId);
        fullName = findViewById(R.id.fullname);
        emailAddress = findViewById(R.id.email);
        placeOrder = findViewById(R.id.placeOrder);
        recyclerViewOrder = findViewById(R.id.recyclerviewOrder);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        Date date = new Date();  //get date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yy/MM/dd");
        strDate = dateFormatter.format(date);
        showOrders();
        Back();
        PlaceOrder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));
    }

    private void PlaceOrder() {
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerId = customerID.getText().toString();
                String requiredDate = "";
                String requiredTime = "";
                String fullname = fullName.getText().toString();
                String address = "";
                String labelAddress = "";
                String email = emailAddress.getText().toString();
                String phoneNumber = "";
                String paymentPhoto = "not required";
                String orderStatus = "Pending";
                String orderType = "Dine in";
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> insertOrder = apiInterface.insertOrder(productCode,strDate,requiredDate,requiredTime,fullname,address,labelAddress,email,phoneNumber,productName,variation,quantity,add_ons,price,subtotal,totalPrice,paymentPhoto,"",orderType,orderStatus);
                insertOrder.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if (response.body() != null){
                            String success = response.body().getSuccess();
                            if (success.equals("1")){
                                startActivity(new Intent(getApplicationContext(),home_activity.class));
                                Toast.makeText(getApplicationContext(),"Ordered Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {
                        startActivity(new Intent(getApplicationContext(),home_activity.class));
                    }
                });
            }
        });
    }

    private void showOrders() {
        String customerId = SharedPreference.getSharedPreference(getApplicationContext()).setID();
        String fname = SharedPreference.getSharedPreference(getApplicationContext()).setFname();
        String lname = SharedPreference.getSharedPreference(getApplicationContext()).setLname();
        String fullname = fname +" "+ lname;
        String email = SharedPreference.getSharedPreference(this).setEmail();
        customerID.setText(customerId);
        fullName.setText(fullname);
        emailAddress.setText(email);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
        getUserCart.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                orderModelLists = response.body();
                orderListsAdapter = new OrderListsAdapter(DineInActivity.this,orderModelLists);
                recyclerViewOrder.setAdapter(orderListsAdapter);

             //   recyclerViewOrder.setOnTouchListener();
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
            productCode = intent.getStringExtra("productCode");
            productName = intent.getStringExtra("productName");
            variation = intent.getStringExtra("variation");
            quantity = intent.getIntExtra("quantity",0);
            add_ons = intent.getStringExtra("add_ons");
            price = intent.getIntExtra("price",0);
            subtotal = intent.getIntExtra("subtotal",0);
            imgProduct = intent.getStringExtra("imgProduct");
            total.setText(totalPrice);
        }
    };
}