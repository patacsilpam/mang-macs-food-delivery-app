package com.example.mangmacs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.activities.MyReservationActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentReservationActivity extends AppCompatActivity {
    private TextView newReservedName,newPhoneNumber,newEmailAddress,newScheduledTime,newGuests,newId,arrowBack;
    private Button bookingStatus,cancelBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_reservation);
        newReservedName = findViewById(R.id.newReservedName);
        newPhoneNumber = findViewById(R.id.newPhoneNumber);
        newEmailAddress = findViewById(R.id.newEmailAddress);
        newScheduledTime = findViewById(R.id.newScheduledTime);
        newGuests = findViewById(R.id.newGuests);
        bookingStatus = findViewById(R.id.bookingStatus);
        newId = findViewById(R.id.newId);
        cancelBooking = findViewById(R.id.cancelBooking);
        arrowBack = findViewById(R.id.arrow_back);
        cancelBooking.setEnabled(false);
        showBookingDetails();
        Back();
    }
    private void showBookingDetails(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String firstname = intent.getStringExtra("firstName");
        String lastname = intent.getStringExtra("lastName");
        String phoneNumber = intent.getStringExtra("");
        String emailAddress = intent.getStringExtra("email");
        String schedDate = intent.getStringExtra("schedDate");
        String schedTime = intent.getStringExtra("schedTime");
        String guests = intent.getStringExtra("guests");
        String status = intent.getStringExtra("bookingStatus");
        String firstLastName = firstname.concat(" ").concat(lastname);
        String time = schedDate.concat(" - ").concat(schedTime);
        //display booking details
        newReservedName.setText(firstLastName);
        newPhoneNumber.setText(phoneNumber);
        newEmailAddress.setText(emailAddress);
        newScheduledTime.setText(time);
        newGuests.setText(guests);
        bookingStatus.setText(status);
        newId.setText(id);
        dismissBooking();
    }
    private void dismissBooking(){
        String status = bookingStatus.getText().toString();
        if (status.equals("Pending")){
            cancelBooking.setEnabled(true);
            cancelBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CurrentReservationActivity.this);
                    alertDialogBuilder.setMessage("Are you sure you want to cancel this booking?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String id = newId.getText().toString();
                                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                    Call<CartModel> callReservation = apiInterface.cancelBookings(id);
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
            cancelBooking.setEnabled(false);
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
}