package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.PickUpPayment;
import com.example.mangmacs.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NowPickUp extends Fragment  {
    private Button pickUpNow;
    private TextView prepTime;
    public NowPickUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_pick_up,container,false);
        pickUpNow = view.findViewById(R.id.pickUpNow);
        prepTime = view.findViewById(R.id.prepTime);
        String preparedTime = SharedPreference.getSharedPreference(getContext()).setPrepTime();
        if (Integer.parseInt(preparedTime) < 60){
            prepTime.setText(preparedTime.concat(" min"));
        }
        else{
            int convertMinHr = Integer.parseInt(preparedTime)/60;
            prepTime.setText(String.valueOf(convertMinHr).concat(" hr"));
        }
        pickUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();  //get date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
                String strDate = dateFormatter.format(date);
                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm aa");
                timeFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                calendar.add(Calendar.MINUTE, Integer.parseInt(preparedTime));
                String strTime = timeFormatter.format(calendar.getTime());
                //intent time and date
                Intent intent = new Intent(getContext(), PickUpPayment.class);
                intent.putExtra("pickUpDate",strDate);
                intent.putExtra("pickUpTime",strTime);
                intent.putExtra("pickUpOrderTime",strTime);
                startActivity(intent);
            }
        });
        return  view;
    }
}