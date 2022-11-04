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
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mangmacs.InputMinMax;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ReservationModel;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

public class ReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerDiningArea;
    private TextInputEditText people,date,time,commentSuggestion;
    private TextInputLayout guestsError,dateError,timeError;
    private TextView diningAreaError,textRequired;
    private Button btnBookNow;
    private RelativeLayout bgDiningArea;
    private BottomNavigationView bottomNavigationView;
    private int hour,min;
    private String token,strDiningArea;
    private String[] diningAreaList = {"Please select dining area","Resto","Venue"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        spinnerDiningArea = findViewById(R.id.diningArea);
        people = findViewById(R.id.people);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        diningAreaError = findViewById(R.id.diningAreaError);
        commentSuggestion = findViewById(R.id.commentsSuggestions);
        guestsError = findViewById(R.id.guestsError);
        dateError = findViewById(R.id.dateError);
        timeError = findViewById(R.id.timeError);
        textRequired = findViewById(R.id.textRequired);
        bgDiningArea = findViewById(R.id.bgDiningArea);
        btnBookNow = findViewById(R.id.btnBookNow);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.book);
        textRequired.setVisibility(View.GONE);
        spinnerDiningArea.setOnItemSelectedListener(this);
        setDiningArea();
        SetCalendar();
        Booking();
        setFirebaseToken();
        BottomNav();
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
                String selDineArea = spinnerDiningArea.getSelectedItem().toString();
                String comments = commentSuggestion.getText().toString();
                if (selDineArea.equals("Please select dining area")){
                    diningAreaError.setVisibility(View.VISIBLE);
                    bgDiningArea.setBackground(getResources().getDrawable(R.drawable.stroke_red_square));
                }
                if (sched_date.isEmpty()){
                    dateError.setError("Required");
                    dateError.setErrorIconDrawable(null);
                }
                if (sched_time.isEmpty()){
                    timeError.setError("Required");
                    timeError.setErrorIconDrawable(null);
                }
                if (guests.isEmpty()){
                    guestsError.setError("Required");
                    guestsError.setErrorIconDrawable(null);
                }
                else{
                    Intent intent = new Intent(ReservationActivity.this,DineInActivity.class);
                    intent.putExtra("guests",guests);
                    intent.putExtra("reserved_date",sched_date);
                    intent.putExtra("reserved_time",sched_time);
                    intent.putExtra("dining_area",selDineArea);
                    intent.putExtra("comments",comments);
                    startActivity(intent);
                }
            }
        });
    }
    private void setDiningArea(){
        ArrayAdapter adptrDiningArea = new ArrayAdapter(this, android.R.layout.simple_spinner_item,diningAreaList);
        adptrDiningArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiningArea.setAdapter(adptrDiningArea);

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
                        try {
                            Date newDate = new Date();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/M/dd hh:mm aa");
                            df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                            String getCurrentTime = String.valueOf(df.format(newDate));
                            String getSelectedDate = date.getText().toString();
                            String getSelectedTime = time.getText().toString();
                            String getDateTime = getSelectedDate +" "+ getSelectedTime;
                            Date currentTime = df.parse(getCurrentTime);
                            Date selectedTime = df.parse(getDateTime);
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
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/M/dd hh:mm aa");
                                    df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                                    String getCurrentTime = String.valueOf(df.format(newDate));
                                    String getSelectedDate = date.getText().toString();
                                    String getSelectedTime = time.getText().toString();
                                    String getDateTime = getSelectedDate +" "+ getSelectedTime;
                                    Date currentTime = df.parse(getCurrentTime);
                                    Date selectedTime = df.parse(getDateTime);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        strDiningArea = diningAreaList[position].toString();

        if(strDiningArea.equals("Venue")){
            people.setFilters(new InputFilter[]{ new InputMinMax(1,155)});
        }
        else {
            people.setFilters(new InputFilter[]{ new InputMinMax(1,99)});
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void BottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(),MenuActivty.class));
                        overridePendingTransition(0,0);
                    case R.id.book:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return true;
            }
        });
    }
}