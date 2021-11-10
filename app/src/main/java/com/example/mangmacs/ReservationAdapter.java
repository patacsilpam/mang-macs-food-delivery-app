package com.example.mangmacs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ReservationAdapter extends FragmentStateAdapter {
    public ReservationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new PreviousOrders();
        }
        return new PreviousReservation();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
