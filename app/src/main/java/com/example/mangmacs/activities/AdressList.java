package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);
        arrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerViewAddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowAddress();
        Back();
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
}