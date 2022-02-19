package com.example.mangmacs.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.PickUpPayment;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.OrderListsAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private TextView arrowBack,total,customerID,emailAddress;
    private Button payDelivery;
    private RecyclerView recyclerViewOrder;
    private CardView cardViewImg;
    private ImageView imgPayment;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String productCode,imgProduct,totalPrice,productName,variation,add_ons,date,time,fullname,phoneNumber,address,labelAddress;
    private int quantity,price,subtotal;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private Bitmap bitmap;
    private Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        customerID = findViewById(R.id.customerId);
        emailAddress = findViewById(R.id.email);
        payDelivery = findViewById(R.id.payDelivery);
        cardViewImg = findViewById(R.id.cardViewImg);
        imgPayment = findViewById(R.id.imgPayment);
        recyclerViewOrder = findViewById(R.id.recyclerViewOrder);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        fullname = intent.getStringExtra("fullName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        address = intent.getStringExtra("address");
        labelAddress = intent.getStringExtra("labelAddress");
        payDelivery.setEnabled(false);
        showOrders();
        Back();
        PayDelivery();
        CameraPermission();
    }

   private void CameraPermission(){
       imgPayment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (ContextCompat.checkSelfPermission(PaymentActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                   Intent intent = new Intent();
                   intent.setType("image/*");
                   intent.setAction(Intent.ACTION_GET_CONTENT);
                   activityResultLauncher.launch(intent);
               }
               else{
                   requestStoragePermission();
               }
           }
       });
   }
    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Album requires permission to access photos");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(PaymentActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                }
            });
            alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                if (data != null){
                    selectedImage = data.getData();
                    if (selectedImage != null){
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                            imgPayment.setImageBitmap(bitmap);
                            imgPayment.setVisibility(View.VISIBLE);
                            payDelivery.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private void PayDelivery() {
            payDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //String customerId = customerID.getText().toString();
                    String email = emailAddress.getText().toString();
                    String paymentPhoto = imageToString();
                    String orderStatus = "Pending";
                    String orderType = "Deliver";
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> insertOrder = apiInterface.insertOrder(productCode,date,date,time,fullname,address,labelAddress,email,phoneNumber,productName,variation,quantity,add_ons,price,subtotal,totalPrice,paymentPhoto,imgProduct,orderType,orderStatus);
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
        String email = SharedPreference.getSharedPreference(this).setEmail();
        customerID.setText(customerId);
        emailAddress.setText(email);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
        getUserCart.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                orderModelLists = response.body();
                orderListsAdapter = new OrderListsAdapter(PaymentActivity.this,orderModelLists);
                recyclerViewOrder.setAdapter(orderListsAdapter);
                //   countCart = recyclerView.getAdapter().getItemCount();
                // cart.setText("("+String.valueOf(countCart)+")");
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
                startActivity(new Intent(PaymentActivity.this, OrderActivity.class));
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