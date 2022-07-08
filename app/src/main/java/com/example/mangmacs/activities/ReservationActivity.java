package com.example.mangmacs.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.model.ReservationModel;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {
    private EditText people,date,time;
    private TextView textRequired;
    private Button btnBookNow,addPhoneNumber;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout reservationLayout;
    private View emptyPhoneNumber;
    private int hour,min;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        people = findViewById(R.id.people);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        textRequired = findViewById(R.id.textRequired);
        reservationLayout = findViewById(R.id.reservationLayout);
        emptyPhoneNumber = findViewById(R.id.emptyPhoneNumber);
        addPhoneNumber = emptyPhoneNumber.findViewById(R.id.addPhoneNumber);
        btnBookNow = findViewById(R.id.btnBookNow);
        textRequired.setVisibility(View.GONE);
        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.reservation);
        BottomNav();
        //ShowReservation();
        SetCalendar();
        Booking();
        setFirebaseToken();
    }
    /*private void ShowReservation(){
        String phoneNumber = SharedPreference.getSharedPreference(getApplicationContext()).setPhoneNumber();
        if (phoneNumber == null){
            reservationLayout.setVisibility(View.GONE);
            emptyPhoneNumber.setVisibility(View.VISIBLE);
            addPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),MyAccountActivity.class));
                }
            });

        }
        else{
            reservationLayout.setVisibility(View.VISIBLE);
            emptyPhoneNumber.setVisibility(View.GONE);
        }
    }*/
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
    private void Booking() {
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guests = people.getText().toString();
                String sched_date = date.getText().toString();
                String sched_time = time.getText().toString();
                String firstname = SharedPreference.getSharedPreference(getApplicationContext()).setFname();
                String lastname = SharedPreference.getSharedPreference(getApplicationContext()).setLname();
                //String phoneNumber = SharedPreference.getSharedPreference(getApplicationContext()).setPhoneNumber();
                if (guests.isEmpty()){
                    people.setError("Required");
                }
                if (sched_date.isEmpty()){
                    date.setError("Required");
                }
                if (sched_time.isEmpty()){
                    time.setError("Required");
                }
                else{
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    String email = SharedPreference.getSharedPreference(ReservationActivity.this).setEmail();
                    Call<ReservationModel> reservationCall = apiInterface.reservation(token,firstname,lastname,guests,email,"",sched_date,sched_time);
                    reservationCall.enqueue(new Callback<ReservationModel>() {
                        @Override
                        public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                            Toast.makeText(getApplicationContext(),"Book Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ReservationActivity.this, ReservationActivity.class));
                        }

                        @Override
                        public void onFailure(Call<ReservationModel> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void SetCalendar() {
        //date picker
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "yyyy/MM/dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.TAIWAN);
                date.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        date.setText(year + "/" + (month+1) + "/" + dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(today);
                datePickerDialog.show();
            }
        });
        //time picker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ReservationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                Calendar calendar1 = calendar.getInstance();
                                calendar1.set(0,0,0,hour,min);
                                time.setText(DateFormat.format("hh:mm aa",calendar1));
                                try {
                                    Date newDate = new Date();
                                    SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                                    df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                                    String getCurrentTime = String.valueOf(df.format(newDate));
                                    String getSelectedTime = time.getText().toString();
                                    Date currentTime = df.parse(getCurrentTime);
                                    Date selectedTime = df.parse(getSelectedTime);
                                    if (selectedTime.after(currentTime)){
                                        btnBookNow.setEnabled(true);
                                        textRequired.setVisibility(View.GONE);
                                    }
                                    else{
                                        btnBookNow.setEnabled(false);
                                        textRequired.setVisibility(View.VISIBLE);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                //displayed previous selected time
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });
    }

    private void BottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), MenuActivty.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservation:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.promo:
                        startActivity(new Intent(getApplicationContext(), PromoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
    }
}