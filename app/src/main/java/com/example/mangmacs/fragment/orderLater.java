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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.activities.AdressList;
import com.example.mangmacs.activities.PaymentActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class orderLater extends Fragment {
    private Button orderLater;
    private TextInputEditText time,date;
    int hour,min;
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
        //button pick up later
        orderLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AdressList.class));
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
                String Format = "yy/MM/dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.TAIWAN);
                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), setDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //time picker
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
        return view;
    }
}