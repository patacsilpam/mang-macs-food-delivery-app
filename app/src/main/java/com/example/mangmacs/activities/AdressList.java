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
import com.example.mangmacs.adapter.DeliveryAddressAdapter;
import com.example.mangmacs.adapter.MyAddressAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.AddressListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdressList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<AddressListModel> addressLists;
    private DeliveryAddressAdapter myAddressAdapter;
    private TextView arrowBack,chosenAddress;
    private Button chooseAddress;
    private String fullname,phoneNumber,address,labelAddress,customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);
        arrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerViewAddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chooseAddress = findViewById(R.id.chooseAddress);
        ShowAddress();
        Back();
        ChooseAddress();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,new IntentFilter("MyUserDetails"));

    }

    private void ChooseAddress() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        chooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),PaymentActivity.class);
                intent1.putExtra("date",date);
                intent1.putExtra("time",time);
                intent1.putExtra("fullName",fullname);
                intent1.putExtra("phoneNumber",phoneNumber);
                intent1.putExtra("address",address);
                intent1.putExtra("labelAddress",labelAddress);
                startActivity(intent1);
            }
        });
    }

    private void Back() {
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdressList.this,OrderActivity.class));
            }
        });
    }

    private void ShowAddress() {
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<AddressListModel>> getUserAddress = apiInterface.getAddress(email);
        getUserAddress.enqueue(new Callback<List<AddressListModel>>() {
            @Override
            public void onResponse(Call<List<AddressListModel>> call, Response<List<AddressListModel>> response) {
                addressLists = response.body();
                myAddressAdapter = new DeliveryAddressAdapter(AdressList.this,addressLists);
                recyclerView.setAdapter(myAddressAdapter);
            }

            @Override
            public void onFailure(Call<List<AddressListModel>> call, Throwable t) {

            }
        });
    }
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fullname = intent.getStringExtra("fullName");
            phoneNumber = intent.getStringExtra("phoneNumber");
            address = intent.getStringExtra("address");
            labelAddress = intent.getStringExtra("labelAddress");
        }
    };
}