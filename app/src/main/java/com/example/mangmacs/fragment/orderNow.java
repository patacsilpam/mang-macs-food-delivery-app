package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.activities.AdressList;
import com.example.mangmacs.activities.PaymentActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class orderNow extends Fragment {
    private Button orderNow;
    public orderNow() {
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
        View view = inflater.inflate(R.layout.fragment_oder_now, container, false);
        orderNow = view.findViewById(R.id.orderNow);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();  //get date
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yy/MM/dd");
                String strDate = dateFormatter.format(date);
                String strTime = "now";
                //intent time and date
                Intent intent = new Intent(getContext(),AdressList.class);
                intent.putExtra("date",strDate);
                intent.putExtra("time",strTime);
                startActivity(intent);
            }
        });
        return view;
    }
}