package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mangmacs.PickUpPayment;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.PaymentActivity;

public class NowPickUp extends Fragment  {
    private Button pickUpNow;
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
        pickUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PickUpPayment.class));
            }
        });
        return  view;
    }
}