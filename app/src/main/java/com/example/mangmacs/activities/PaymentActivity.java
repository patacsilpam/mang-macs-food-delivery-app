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
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements OrdersListener {
    private TextView arrowBack,total,customerID,emailAddress;
    private Button payDelivery;
    private RecyclerView recyclerViewOrder;
    private CardView cardViewImg;
    private ImageView imgPayment;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String date,time,recipientName,phoneNumber,address,labelAddress,totalPrice;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private Bitmap bitmap;
    private Uri selectedImage;
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
        recipientName = intent.getStringExtra("fullName");
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
                orderListsAdapter = new OrderListsAdapter(PaymentActivity.this,orderModelLists,PaymentActivity.this);
                recyclerViewOrder.setAdapter(orderListsAdapter);
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

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
    private void PayDelivery() {
        payDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailAddress.getText().toString();
                String fname = SharedPreference.getSharedPreference(PaymentActivity.this).setFname();
                String lname = SharedPreference.getSharedPreference(PaymentActivity.this).setLname();
                String accountName = fname.concat(" ").concat(lname);
                String paymentPhoto = imageToString();
                String orderStatus = "Pending";
                String orderType = "Deliver";
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> insertOrder = apiInterface.insertOrder(productCodeList,accountName,recipientName,address,labelAddress,email,phoneNumber,orderLists,variationList,quantityList,addOnsList,priceList,subTotalList,totalPrice,paymentPhoto,imgProductList,orderType,orderStatus,date,time);
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

    private void Back() {
        //arrow back button
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this, OrderActivity.class));
            }
        });
    }

    @Override
    public void onProductsChange(ArrayList<String> products) {
        orderLists = products;
    }

    @Override
    public void onProductCodeChange(ArrayList<String> productCodes) {
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
}