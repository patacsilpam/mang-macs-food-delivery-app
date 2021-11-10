package com.example.mangmacs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.HOUR_OF_DAY;

public class ReservationActivity extends AppCompatActivity {
    EditText fname,lname,people,date,time;
    int hour,min;
    Button btnBookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        people = findViewById(R.id.people);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        btnBookNow = findViewById(R.id.btnBookNow);
        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.reservation);
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @SuppressLint("NonConstantResourceId")
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.home:
                       startActivity(new Intent(getApplicationContext(),home_activity.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.reservation:
                       return true;
                   case R.id.menu:
                       startActivity(new Intent(getApplicationContext(),MenuActivty.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.account:
                       startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                       overridePendingTransition(0,0);
                       return true;
               }
               return true;
           }
       });
        //date picker
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "yy/dd/MM";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.TAIWAN);
                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReservationActivity.this, setDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //time picker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ReservationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                //initialize calendar
                                Calendar calendar1 = calendar.getInstance();
                                //set hour and date
                                calendar1.set(0,0,0,hour,min);
                                //set selected time on edittext
                                time.setText(DateFormat.format("hh:mm aa",calendar1));

                            }
                        },12,0,false
                );
                //displayed previous selected time
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });
        //booking
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String guests = people.getText().toString();
                String sched_date = date.getText().toString();
                String sched_time = time.getText().toString();
                if(firstname.isEmpty()){
                    fname.setError("Required");
                }
                if (lastname.isEmpty()){
                    lname.setError("Required");
                }
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
                   String email =  SharedPreference.getSharedPreference(ReservationActivity.this).LoggedInUser();
                    Call<ReservationModel> reservationCall = apiInterface.reservation(firstname,lastname,guests,email,sched_date,sched_time);
                    reservationCall.enqueue(new Callback<ReservationModel>() {
                        @Override
                        public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                            startActivity(new Intent(ReservationActivity.this,ConfirmReservationActivity.class));
                        }

                        @Override
                        public void onFailure(Call<ReservationModel> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}