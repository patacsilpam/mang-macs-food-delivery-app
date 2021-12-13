package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.BilaoAdapter;
import com.example.mangmacs.adapter.ComboMealAdapter;
import com.example.mangmacs.adapter.MyAddressAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.AddressListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAddressActivity extends AppCompatActivity {
    private FloatingActionButton addAddress;
    private RecyclerView recyclerView;
    private List<AddressListModel> addressLists;
    private MyAddressAdapter myAddressAdapter;
    TextView fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        fullname = findViewById(R.id.afullname);
        addAddress = findViewById(R.id.addAddress);
        recyclerView = findViewById(R.id.listaddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAddressActivity.this, CreateAddressActivity.class));
            }
        });
        showAddresses();
    }

    private void showAddresses() {
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<AddressListModel>> getUserAddress = apiInterface.getAddress(email);
        getUserAddress.enqueue(new Callback<List<AddressListModel>>() {
            @Override
            public void onResponse(Call<List<AddressListModel>> call, Response<List<AddressListModel>> response) {
                addressLists = response.body();
                myAddressAdapter = new MyAddressAdapter(MyAddressActivity.this,addressLists);
                recyclerView.setAdapter(myAddressAdapter);
                deleteAddress();
            }

            @Override
            public void onFailure(Call<List<AddressListModel>> call, Throwable t) {

            }
        });
    }

    private void deleteAddress() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AddressListModel deleteAddress  = addressLists.get(viewHolder.getBindingAdapterPosition());
                int position = viewHolder.getBindingAdapterPosition();
                addressLists.remove(viewHolder.getBindingAdapterPosition());
                myAddressAdapter.notifyItemChanged(viewHolder.getBindingAdapterPosition());
            }
        });
    }
}