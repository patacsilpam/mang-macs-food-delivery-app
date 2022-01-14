package com.example.mangmacs.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mangmacs.fragment.CurrentReservation;
import com.example.mangmacs.fragment.PreviousOrders;
import com.example.mangmacs.fragment.PreviousReservation;

public class ReservationAdapter extends FragmentStateAdapter {
    public ReservationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new PreviousReservation();
        }
        return new CurrentReservation();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
