package com.example.mangmacs.activities;

import static android.content.ContentValues.TAG;

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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;

import com.example.mangmacs.adapter.OrderListsAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.SettingsModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpPayment extends AppCompatActivity implements OrdersListener {
    private ProgressBar progressBar;
    private TextView arrowBack,total,waitingTime;
    private Button pickUpOrder;
    private ImageView imgPayment;
    private RecyclerView recyclerViewOrder;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    private String date,time,orderTime,estTime,token,recipientName,address,labelAddress,phoneNumber;
    private int totalPrice;
    private Bitmap bitmap;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private ArrayList<String> orderLists = new ArrayList<>();
    private ArrayList<String> productCodeList = new ArrayList<>();
    private ArrayList<String> productCategoryList = new ArrayList<>();
    private ArrayList<String> variationList = new ArrayList<>();
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private ArrayList<String> addOnsList = new ArrayList<>();
    private ArrayList<Integer> addOnsFeeList = new ArrayList<>();
    private ArrayList<String> specialReqList = new ArrayList<>();
    private ArrayList<String> subTotalList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<String> imgProductList = new ArrayList<>();
    private ArrayList<String> preparationTimeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_payment);
        waitingTime = findViewById(R.id.waitingTime);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        pickUpOrder = findViewById(R.id.pickUpOrder);
        pickUpOrder.setEnabled(false);
        progressBar = findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.GONE);
        imgPayment = findViewById(R.id.imgPayment);
        recyclerViewOrder = findViewById(R.id.recyclerviewPayment);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));
        Intent intent = getIntent();
        date = intent.getStringExtra("pickUpDate");
        time = intent.getStringExtra("pickUpTime");
        orderTime = intent.getStringExtra("pickUpOrderTime");
        showOrders();
        PickUpOrders();
        Back();
        CameraPermission();
        setFirebaseToken();
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
                    }});
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
           estTime = intent.getStringExtra("waitingTime");
        }
    };
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
    public void onProductCategoryChange(ArrayList<String> category) {
        productCategoryList = category;

    }

    @Override
    public void onProductCodeChange(ArrayList<String> productCodes) {
        productCodeList = productCodes;
        ArrayList<String> stockList = new ArrayList<>();
        String emailAddress = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<SettingsModel>> callId = apiInterface.getSettings(productCodeList,emailAddress);
        callId.enqueue(new Callback<List<SettingsModel>>() {
            @Override
            public void onResponse(Call<List<SettingsModel>> call, Response<List<SettingsModel>> response) {
                List<SettingsModel> settingsList = response.body();
                for(SettingsModel list: settingsList) {
                    int stocks = list.getStocks();
                   for (int i = 0; i<quantityList.size(); i++){
                       if(quantityList.get(i) <= stocks){
                          stockList.add("True");
                       }
                       else{
                           stockList.add("False");
                       }
                   }
                }
               if(orderTime.equals("later")){
                    waitingTime.setText(date + " " + time);

                }
               else if(stockList.contains("False")){
                   try {
                       Date newDate = new Date();
                       SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                       df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                       String getCurrentTime = String.valueOf(df.format(newDate));
                       Date currentTime = df.parse(getCurrentTime);
                       Calendar cal = Calendar.getInstance();
                       cal.setTime(currentTime);
                       cal.add(Calendar.MINUTE, Integer.parseInt(estTime));
                       String newTime = df.format(cal.getTime());
                       waitingTime.setText(newTime);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }
                else{
                    waitingTime.setText("Pick Up anytime");
                }

            }

            @Override
            public void onFailure(Call<List<SettingsModel>> call, Throwable t) {

            }

        });
    }
    private void PickUpOrders() {
        pickUpOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sprite circle = new Circle();
                progressBar.setIndeterminateDrawable(circle);
                progressBar.setVisibility(View.VISIBLE);
                String fname = SharedPreference.getSharedPreference(PickUpPayment.this).setFname();
                String lname = SharedPreference.getSharedPreference(PickUpPayment.this).setLname();
                String email = SharedPreference.getSharedPreference(PickUpPayment.this).setEmail();
                String accountName = fname.concat(" ").concat(lname);
                String estTime = waitingTime.getText().toString();
                String orderType = "Pick Up";
                String orderStatus = "Pending";
                String paymentPhoto = imageToString();
                String customerId = SharedPreference.getSharedPreference(getApplicationContext()).setID();
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> insertOrder = apiInterface.insertOrder(productCodeList,customerId,accountName,"","","",token,email,"",orderLists,productCategoryList,variationList,quantityList,addOnsList,addOnsFeeList,specialReqList,priceList,subTotalList, String.valueOf(totalPrice),paymentPhoto,"",imgProductList,preparationTimeList,orderType,orderStatus,date,time,0,estTime);
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
                        Toast.makeText(getApplicationContext(), "Ordered Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
    public void onAddOnsFeeChange(ArrayList<Integer> addaonsFee) {
        addOnsFeeList = addaonsFee;
    }

    @Override
    public void onSpecialRequest(ArrayList<String> specialRequest) {
        specialReqList = specialRequest;
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