package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mangmacs.R;
import com.example.mangmacs.activities.AdressList;
import com.example.mangmacs.activities.PaymentActivity;

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
                startActivity(new Intent(getContext(), AdressList.class));
            }
        });
        return view;
    }
}