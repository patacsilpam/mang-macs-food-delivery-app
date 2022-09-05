package com.example.mangmacs.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.CurrentReservationAdapter;
import com.example.mangmacs.adapter.PreviousBookingDetailAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousReservationActivity extends AppCompatActivity {
    private PreviousBookingDetailAdapter reservationAdapter;
    private List<ReservationModel> reservationModelList;
    private TextView newReservedName,newEmailAddress,newScheduledTime,newGuests,newId,newTotalAmount,newOrderNumber,txt_total_amount,arrowBack;
    private RecyclerView previousBookDetails;
    private Button bookingStatus,orderReceived;
    private CardView cancelOrderLayout;
    private RelativeLayout orderReceivedLayout;
    private String id,orderNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_reservation);
        newReservedName = findViewById(R.id.bookName);
        newEmailAddress = findViewById(R.id.bookEmail);
        newScheduledTime = findViewById(R.id.bookSchedDateTime);
        newGuests = findViewById(R.id.bookGuests);
        arrowBack = findViewById(R.id.arrow_back);
        bookingStatus = findViewById(R.id.bookingStatus);
        newTotalAmount = findViewById(R.id.totalAmount);
        newOrderNumber = findViewById(R.id.bookOrderNumber);
        txt_total_amount = findViewById(R.id.total_amount);
        orderReceived = findViewById(R.id.orderReceived);
        orderReceivedLayout = findViewById(R.id.orderReceivedLayout);
        cancelOrderLayout = findViewById(R.id.cancelOrderLayout);
        previousBookDetails = findViewById(R.id.previousBookDetails);
        previousBookDetails.setHasFixedSize(true);
        previousBookDetails.setLayoutManager(new LinearLayoutManager(this));
        showBookingDetails();
        Back();
    }
    private void showBookingDetails(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        orderNumber = intent.getStringExtra("refNumber");
        String firstname = intent.getStringExtra("firstName");
        String lastname = intent.getStringExtra("lastName");
        String emailAddress = intent.getStringExtra("email");
        String schedDate = intent.getStringExtra("schedDate");
        String schedTime = intent.getStringExtra("schedTime");
        String guests = intent.getStringExtra("guests");
        String status = intent.getStringExtra("bookingStatus");
        String totalAmount = intent.getStringExtra("totalAmount");
        String firstLastName = firstname.concat(" ").concat(lastname);
        String time = schedDate.concat(" ").concat(schedTime);
        //display booking details
        newReservedName.setText(firstLastName);
        newEmailAddress.setText(emailAddress);
        newScheduledTime.setText(time);
        newTotalAmount.setText("₱ ".concat(totalAmount).concat(".00"));
        newGuests.setText(guests.concat(" people"));
        bookingStatus.setText(status);
        newOrderNumber.setText(orderNumber);
        showBookingOrders();
        setOrderReceived();
        //show order received button if order status is equal to resereved
        if ((status.equals("Reserved") || status.equals("Order Received")) || (status.equals("Pending") || status.equals("Finished"))){
            orderReceivedLayout.setVisibility(View.GONE);
            cancelOrderLayout.setVisibility(View.GONE);
            if(status.equals("Finished")){
                bookingStatus.setText("Completed");
            }
        }

        else{
            txt_total_amount.setText("₱ ".concat(totalAmount).concat(" .00"));
            orderReceivedLayout.setVisibility(View.GONE);
            bookingStatus.setVisibility(View.GONE);
        }
    }
    private void showBookingOrders() {
        String emailAddress = SharedPreference.getSharedPreference(PreviousReservationActivity.this).setEmail();
        String orderNumber = newOrderNumber.getText().toString();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<ReservationModel>> call = apiInterface.getPreviousBookingsDetails(emailAddress,orderNumber);
        call.enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                reservationModelList = response.body();
                reservationAdapter = new PreviousBookingDetailAdapter(PreviousReservationActivity.this,reservationModelList);
                previousBookDetails.setAdapter(reservationAdapter);
            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {

            }
        });
    }
    private void setOrderReceived(){
        orderReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> call = apiInterface.changeBookingStatus(orderNumber);
                call.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if (response.body() != null){
                            String success = response.body().getSuccess();
                            if (success.equals("1")){
                                startActivity(new Intent(PreviousReservationActivity.this,MyReservationActivity.class));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });
    }
    private void Back(){
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyReservationActivity.class));
            }
        });
    }
}