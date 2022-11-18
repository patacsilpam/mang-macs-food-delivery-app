package com.example.mangmacs.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.PickUpPayment;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.ReservationActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LaterPickUp extends Fragment {
    private Button pickUpLater;
    private TextInputEditText time,date;
    private TextView textRequired;
    private Calendar calendar;
    private int hour,min;
    public LaterPickUp() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_later_pick_up, container, false);
        pickUpLater = view.findViewById(R.id.pickUpLater);
        time = view.findViewById(R.id.time);
        date = view.findViewById(R.id.date);
        textRequired = view.findViewById(R.id.textRequired);
        textRequired.setVisibility(View.GONE);
        //button pick up later
        PickUpLater();
        SetDate();
        SetTime();
        return view;
    }
    //set selected time in edittext
    private void SetDate() {
        //date picker
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

    private void SetTime() {
        int storedPrepTime = Integer.parseInt(SharedPreference.getSharedPreference(getContext()).setPrepTime());
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
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
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/M/dd hh:mm aa");
                                    df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                                    String getCurrentTime = String.valueOf(df.format(newDate));
                                    String getSelectedDate = date.getText().toString();
                                    String getSelectedTime = time.getText().toString();
                                    String getDateTime = getSelectedDate +" "+ getSelectedTime;
                                    //parse selected time and date
                                    Date currentTime = df.parse(getCurrentTime);
                                    Date selectedTime = df.parse(getDateTime);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(currentTime);
                                    cal.add(Calendar.MINUTE,storedPrepTime);
                                    String getEstTime = df.format(cal.getTime());
                                    Date estTime = df.parse(getEstTime);
                                    //disable pick up button if selected is less than preptime
                                    if (selectedTime.after(estTime)){
                                        pickUpLater.setEnabled(true);
                                        textRequired.setVisibility(View.GONE);
                                    }
                                    else{
                                        pickUpLater.setEnabled(false);
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

    private void PickUpLater() {
        pickUpLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = date.getText().toString();
                String strTime = time.getText().toString();
                String orderTime = "later";
                if (strDate.isEmpty()){
                    date.setError("Required");
                }
                if (strTime.isEmpty()){
                    time.setError("Required");
                }
                else{
                    Intent intent = new Intent(getContext(), PickUpPayment.class);
                    intent.putExtra("pickUpDate",strDate);
                    intent.putExtra("pickUpTime",strTime);
                    intent.putExtra("pickUpOrderTime",orderTime);
                    startActivity(intent);
                }
            }
        });
    }
}