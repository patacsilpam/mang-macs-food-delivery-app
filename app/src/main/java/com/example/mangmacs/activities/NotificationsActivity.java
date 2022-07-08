package com.example.mangmacs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.BookNotificationAdapter;
import com.example.mangmacs.adapter.OrderNotifAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CurrentOrdersModel;
import com.example.mangmacs.model.ReservationModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView orderUpdateLists,bookUpdateLists;
    private LinearLayout orderUpdateLayout,bookUpdateLayout;
    private OrderNotifAdapter orderNotifAdapter;
    private BookNotificationAdapter bookNotificationAdapter;
    private List<CurrentOrdersModel> notifLists;
    private List<ReservationModel> reservationLists;
    private View notifBadge;
    private int countOrderUpdates = 0;
    private int countBookUpdates = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        orderUpdateLayout = findViewById(R.id.orderUpdateLayout);
        bookUpdateLayout = findViewById(R.id.bookUpdateLayout);
        orderUpdateLists = findViewById(R.id.orderUpdateLists);
        bookUpdateLists = findViewById(R.id.bookUpdateLists);
        orderUpdateLists.setHasFixedSize(true);
        bookUpdateLists.setHasFixedSize(true);
        orderUpdateLists.setLayoutManager(new LinearLayoutManager(this));
        bookUpdateLists.setLayoutManager(new LinearLayoutManager(this));
        ShowOrderNotifications();
        ShowBookNotifications();
    }
    private void ShowOrderNotifications(){
        String emailAddress = SharedPreference.getSharedPreference(NotificationsActivity.this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> call = apiInterface.getOrdersNotif(emailAddress);
        call.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                notifLists = response.body();
                orderNotifAdapter = new OrderNotifAdapter(NotificationsActivity.this,notifLists);
                orderUpdateLists.setAdapter(orderNotifAdapter);
                countOrderUpdates = orderNotifAdapter.getItemCount();
                if (countOrderUpdates == 0){
                    orderUpdateLayout.setVisibility(View.GONE);
                }
                else{
                    orderUpdateLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {

            }
        });
    }
    private void ShowBookNotifications(){
        String emailAddress = SharedPreference.getSharedPreference(NotificationsActivity.this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<ReservationModel>> call = apiInterface.getBookNotif(emailAddress);
        call.enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                reservationLists = response.body();
                bookNotificationAdapter = new BookNotificationAdapter(NotificationsActivity.this,reservationLists);
                bookUpdateLists.setAdapter(bookNotificationAdapter);
                countBookUpdates = bookNotificationAdapter.getItemCount();
                if (countBookUpdates == 0){
                    bookUpdateLayout.setVisibility(View.GONE);
                }
                else{
                    bookUpdateLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {

            }
        });
    }

}