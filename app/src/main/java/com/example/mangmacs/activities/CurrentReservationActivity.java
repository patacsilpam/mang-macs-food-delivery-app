package com.example.mangmacs.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.mangmacs.adapter.ReservationAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.ReservationModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentReservationActivity extends AppCompatActivity {
    private CurrentReservationAdapter reservationAdapter;
    private List<ReservationModel> reservationModelList;
    private TextView newReservedName,newEmailAddress,newScheduledTime,newGuests,newId,arrowBack,newTotalAmount,newPhoneNo,newOrderNumber,newPaymentNumber,newSpecialReq,newTxtNote;
    private Button bookingStatus,cancelBooking;
    private RelativeLayout cancelBookingLayout;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_reservation);
        newReservedName = findViewById(R.id.reservedName);
        newEmailAddress = findViewById(R.id.reservedEmail);
        newScheduledTime = findViewById(R.id.bookSchedDateTime);
        newGuests = findViewById(R.id.bookGuests);
        newPhoneNo = findViewById(R.id.reservedPhoneNo);
        newTxtNote = findViewById(R.id.txtNote);
        cancelBooking = findViewById(R.id.cancelBooking);
        arrowBack = findViewById(R.id.arrow_back);
        bookingStatus = findViewById(R.id.bookingStatus);
        newSpecialReq = findViewById(R.id.specialRequest);
        newOrderNumber = findViewById(R.id.reservedNumber);
        //newPaymentNumber = findViewById(R.id.paymentNumber);
        cancelBookingLayout = findViewById(R.id.cancelBookingLayout);
        /*newTotalAmount = findViewById(R.id.totalAmount);
        newBookDetails = findViewById(R.id.newBookDetails);
        newBookDetails.setHasFixedSize(true);
        newBookDetails.setLayoutManager(new LinearLayoutManager(this));*/
        showBookingDetails();
        Back();
    }
    private void showBookingDetails(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String orderNumber = intent.getStringExtra("refNumber");
        String firstname = intent.getStringExtra("firstName");
        String lastname = intent.getStringExtra("lastName");
        String emailAddress = intent.getStringExtra("email");
        String phoneNo = intent.getStringExtra("phoneNo");
        String schedDate = intent.getStringExtra("schedDate");
        String schedTime = intent.getStringExtra("schedTime");
        String guests = intent.getStringExtra("guests");
        String status = intent.getStringExtra("bookingStatus");
        String totalAmount = intent.getStringExtra("totalAmount");
        String paymentNumber = intent.getStringExtra("paymentNumber");
        String specialRequest = intent.getStringExtra("specialRequest");
        String waitingTime = intent.getStringExtra("waitingTime");
        String firstLastName = firstname.concat(" ").concat(lastname);
        String time = schedDate.concat(" ").concat(schedTime);

        //display booking details
        newOrderNumber.setText(orderNumber);
        newReservedName.setText(firstLastName);
        newEmailAddress.setText(emailAddress);
        newPhoneNo.setText(String.valueOf(phoneNo));
        newScheduledTime.setText(time);
        newGuests.setText(guests.concat(" people"));
        newTxtNote.setText("Note: Please arrive before " +  waitingTime +" minutes to avoid having your table canceled.");
        //newPaymentNumber.setText(paymentNumber);
        bookingStatus.setText(status);
        newSpecialReq.setText(specialRequest);
        //newTotalAmount.setText("₱ ".concat(totalAmount).concat(" .00"));
        //showBookingOrders();
        dismissBooking();
    }

    private void dismissBooking(){
        String status = bookingStatus.getText().toString();
        String orderNumber = newOrderNumber.getText().toString();
        //Toast.makeText(getApplicationContext(),orderNumber,Toast.LENGTH_SHORT).show();
        if (status.equals("Pending")){
            cancelBookingLayout.setVisibility(View.VISIBLE);
            cancelBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CurrentReservationActivity.this);
                    alertDialogBuilder.setMessage("Are you sure you want to cancel this booking?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                    Call<CartModel> callReservation = apiInterface.cancelBookings(orderNumber);
                                    callReservation.enqueue(new Callback<CartModel>() {
                                        @Override
                                        public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                                            if (response.body() != null){
                                                String success = response.body().getSuccess();
                                                if (success.equals("1")){
                                                    Toast.makeText(getApplicationContext(),"Cancelled booking successfully",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(),MyReservationActivity.class));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CartModel> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } else{
            cancelBookingLayout.setVisibility(View.GONE);
            cancelBooking.setBackgroundColor(Color.LTGRAY);
        }


    }
    private void Back(){
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyReservationActivity.class));
            }
        });
    }
     /* private void showBookingOrders() {
        String emailAddress = SharedPreference.getSharedPreference(CurrentReservationActivity.this).setEmail();
        String orderNumber = newOrderNumber.getText().toString();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<ReservationModel>> call = apiInterface.getCurrentBookingsDetails(emailAddress,orderNumber);
        call.enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                reservationModelList = response.body();
                reservationAdapter = new CurrentReservationAdapter(CurrentReservationActivity.this,reservationModelList);
                newBookDetails.setAdapter(reservationAdapter);
            }
            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {}
        });}*/
}