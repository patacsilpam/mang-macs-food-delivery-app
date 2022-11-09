package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.AdressList;
import com.example.mangmacs.activities.PaymentActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class orderNow extends Fragment {
    private Button orderNow;
    private TextView prepTime;
    public orderNow() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_oder_now, container, false);
        orderNow = view.findViewById(R.id.orderNow);
        prepTime = view.findViewById(R.id.prepTime);
        String preparedTime = SharedPreference.getSharedPreference(getContext()).setPrepTime();
        //convert min to hour if preparation time is greater than or equal to 60 minutes
        if(Integer.parseInt(preparedTime) < 60){
            prepTime.setText(preparedTime.concat(" min"));
        }
        else{

            int convertMinHr = Integer.parseInt(preparedTime)/60;
            prepTime.setText(String.valueOf(convertMinHr).concat(" hr"));
        }
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();  //get date
                Calendar calendar = Calendar.getInstance();
                //format date
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yy/MM/dd");
                String strDate = dateFormatter.format(date);
                //format time
                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                timeFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                calendar.add(Calendar.MINUTE, Integer.parseInt(preparedTime));
                String strTime = timeFormatter.format(calendar.getTime());

                //intent time and date
                Intent intent = new Intent(getContext(),AdressList.class);
                intent.putExtra("date",strDate);
                intent.putExtra("time","-- --");
                intent.putExtra("orderTime",strTime);
                intent.putExtra("recipientName","none");
                intent.putExtra("labelAddress","none");
                intent.putExtra("phoneNumber","none");
                intent.putExtra("address","none");
                startActivity(intent);
            }
        });
        return view;
    }
}