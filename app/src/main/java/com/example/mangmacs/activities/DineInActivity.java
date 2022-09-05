package com.example.mangmacs.activities;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

public class DineInActivity extends AppCompatActivity implements OrdersListener {
    private TextView arrowBack,total,fullName,emailAddress,schedDate,guests;
    private RecyclerView recyclerViewOrder;
    private Button placeOrder;
    private ImageView imgPayment;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String strDate,strTime,strGuests,token;
    private int totalPrice;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private Bitmap bitmap;
    private Uri selectedImage;
    private ArrayList<String> orderLists = new ArrayList<>();
    private ArrayList<String> productCodeList = new ArrayList<>();
    private ArrayList<String> productCategoryList = new ArrayList<>();
    private ArrayList<String> variationList = new ArrayList<>();
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private ArrayList<String> addOnsList = new ArrayList<>();
    private ArrayList<String> subTotalList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<String> imgProductList = new ArrayList<>();
    private ArrayList<String> preparationTimeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        fullName = findViewById(R.id.fullname);
        emailAddress = findViewById(R.id.email);
        placeOrder = findViewById(R.id.placeOrder);
        imgPayment = findViewById(R.id.imgPayment);
        schedDate = findViewById(R.id.schedDate);
        guests = findViewById(R.id.guests);
        placeOrder.setEnabled(false);
        recyclerViewOrder = findViewById(R.id.recyclerviewOrder);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        Intent intent  = getIntent();
        strDate = intent.getStringExtra("reserved_date");
        strTime = intent.getStringExtra("reserved_time");
        strGuests = intent.getStringExtra("guests");
        showOrders();
        CameraPermission();
        setFirebaseToken();
        PlaceOrder();
        Back();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));
        schedDate.setText(strDate.concat(" ").concat(strTime));
        guests.setText(strGuests);
    }
    private void setFirebaseToken(){
        FirebaseMessaging.getInstance().subscribeToTopic("mangmacs");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token = task.getResult().getToken();
                            Log.d(TAG,"On complete " + token);
                        }
                        else{
                            Log.d(TAG,"Token not generated");
                        }
                    }
                });
    }
    private void CameraPermission(){
        imgPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DineInActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
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
                    ActivityCompat.requestPermissions(DineInActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
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
                            placeOrder.setEnabled(true);
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
                startActivity(new Intent(DineInActivity.this,ReservationActivity.class));
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalPrice = intent.getIntExtra("totalorderprice",0);
            total.setText(String.valueOf(totalPrice));
        }
    };
    private void PlaceOrder() {
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = SharedPreference.getSharedPreference(DineInActivity.this).setFname();
                String lname =SharedPreference.getSharedPreference(DineInActivity.this).setLname();
                String email = SharedPreference.getSharedPreference(DineInActivity.this).setEmail();
                String customerId = SharedPreference.getSharedPreference(DineInActivity.this).setID();
                String paymentPhoto = imageToString();
                String orderStatus = "Pending";
                String orderType = "Dine In";
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> insertOrder = apiInterface.dineInOrder(productCodeList,customerId,token,fname,lname,strGuests,email,strDate,strTime,orderLists,productCategoryList,variationList,quantityList,addOnsList,priceList,subTotalList,String.valueOf(totalPrice),paymentPhoto,"",imgProductList,preparationTimeList,orderType,orderStatus);
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

    @Override
    public void onProductCategoryChange(ArrayList<String> category) {
        productCategoryList = category;
    }

    public void onProductCodeChange(ArrayList<String> productCodes){
        productCodeList = productCodes;
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

    @Override
    public void onPreparationTimeChange(ArrayList<String> preparationTime) {
        preparationTimeList = preparationTime;
    }
}