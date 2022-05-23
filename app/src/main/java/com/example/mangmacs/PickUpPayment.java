package com.example.mangmacs;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mangmacs.activities.DineInActivity;
import com.example.mangmacs.activities.PickUpActivity;

import com.example.mangmacs.activities.home_activity;
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

public class PickUpPayment extends AppCompatActivity implements OrdersListener {
    private TextView arrowBack,total;
    private Button pickUpOrder;
    private ImageView imgPayment;
    private RecyclerView recyclerViewOrder;
    private RadioGroup choosePayment;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String date,time;
    private int totalPrice;
    private Bitmap bitmap;
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
        setContentView(R.layout.activity_pick_up_payment);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        pickUpOrder = findViewById(R.id.pickUpOrder);
        imgPayment = findViewById(R.id.imgPayment);
        choosePayment  = findViewById(R.id.choosePayment);
        recyclerViewOrder = findViewById(R.id.recyclerviewPayment);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        pickUpOrder.setEnabled(false);
        showOrders();
        PickUpOrders();
        Back();
        CameraPermission();
    }
    private void showOrders() {
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
        getUserCart.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                orderModelLists = response.body();
                orderListsAdapter = new OrderListsAdapter(PickUpPayment.this,orderModelLists,PickUpPayment.this);
                recyclerViewOrder.setAdapter(orderListsAdapter);
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

            }
        });
    }
    private void CameraPermission(){
        imgPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PickUpPayment.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
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
                    ActivityCompat.requestPermissions(PickUpPayment.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
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
                    Uri selectedImage = data.getData();
                    if (selectedImage != null){
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                            imgPayment.setImageBitmap(bitmap);
                            imgPayment.setVisibility(View.VISIBLE);
                            pickUpOrder.setEnabled(true);
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
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalPrice = intent.getIntExtra("totalorderprice",0);
            total.setText(String.valueOf(totalPrice));
        }
    };
    private void PickUpOrders() {
        pickUpOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (choosePayment.getCheckedRadioButtonId() == -1){
                   Toast.makeText(getApplicationContext(),"Please choose a payment method",Toast.LENGTH_SHORT).show();
               } else{
                   String fname = SharedPreference.getSharedPreference(PickUpPayment.this).setFname();
                   String lname = SharedPreference.getSharedPreference(PickUpPayment.this).setLname();
                   String email = SharedPreference.getSharedPreference(PickUpPayment.this).setEmail();
                   String accountName = fname.concat(lname);
                   String address = "";
                   String labelAddress = "";
                   String phoneNumber = "";
                   String orderType = "Pick Up";
                   String orderStatus = "Pending";
                   String paymentPhoto = imageToString();
                   int selectedPayment = choosePayment.getCheckedRadioButtonId();
                   RadioButton radioButton = findViewById(selectedPayment);
                   String paymentType = radioButton.getText().toString();
                   ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                   Call<CartModel> insertOrder = apiInterface.insertOrder(productCodeList,accountName,"",address,labelAddress,email,phoneNumber,orderLists,variationList,quantityList,addOnsList,priceList,subTotalList, String.valueOf(totalPrice),paymentPhoto,paymentType,imgProductList,orderType,orderStatus,date,time,0);
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
                           startActivity(new Intent(getApplicationContext(), home_activity.class));
                           Toast.makeText(getApplicationContext(),"Ordered Successfully",Toast.LENGTH_SHORT).show();
                       }
                   });
               }
            }
        });
    }

    private void Back() {
        //arrow back button
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickUpPayment.this, PickUpActivity.class));
            }
        });
    }
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

    @Override
    public void onTotalAmountChange(String amount) {

    }
}