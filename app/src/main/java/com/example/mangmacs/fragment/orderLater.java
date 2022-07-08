package com.example.mangmacs.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.AdressList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class orderLater extends Fragment {
    private Button orderLater;
    private EditText time,date;
    private TextView textRequired;
    private Calendar calendar;
    private int hour,min,prepTime;
    public orderLater() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_later, container, false);
        orderLater = view.findViewById(R.id.orderLater);
        time = view.findViewById(R.id.time);
        date = view.findViewById(R.id.date);
        textRequired = view.findViewById(R.id.textRequired);
        textRequired.setVisibility(View.GONE);
        OrderLater();
        SetDate();
        SetTime();
        return view;
    }
    private void SetTime() {
        int storedPrepTime = Integer.parseInt(SharedPreference.getSharedPreference(getContext()).setPrepTime());
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
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
                                    //get current time
                                    Date newDate = new Date();
                                    SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                                    df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                                    String getCurrentTime = String.valueOf(df.format(newDate));
                                    String getSelectedTime = time.getText().toString();
                                    //parse selected time and date
                                    Date currentTime = df.parse(getCurrentTime);
                                    Date selectedTime = df.parse(getSelectedTime);
                                    //add current time to prep time
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(currentTime);
                                    cal.add(Calendar.MINUTE,storedPrepTime);
                                    String getEstTime = df.format(cal.getTime());
                                    Date estTime = df.parse(getEstTime);
                                    //disable pick up button if selected is less than preptime
                                    if (selectedTime.after(estTime)){
                                        orderLater.setEnabled(true);
                                        textRequired.setVisibility(View.GONE);
                                    }
                                    else{
                                        orderLater.setEnabled(false);
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

    private void SetDate() {
        calendar = Calendar.getInstance();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
    }

    private void OrderLater() {
        orderLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = date.getText().toString();
                String strTime = time.getText().toString();
                if (strDate.isEmpty()){
                    date.setError("Required");
                }
                if (strTime.isEmpty()){
                    time.setError("Required");
                }
                else{
                    String orderTime = "later";
                    Intent intent = new Intent(getContext(), AdressList.class);
                    intent.putExtra("date",strDate);
                    intent.putExtra("time",strTime);
                    intent.putExtra("orderTime",orderTime);
                    startActivity(intent);
                }
            }
        });
    };
}