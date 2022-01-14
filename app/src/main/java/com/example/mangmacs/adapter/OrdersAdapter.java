package com.example.mangmacs.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mangmacs.fragment.CurrentOrders;
import com.example.mangmacs.fragment.PreviousOrders;

public class OrdersAdapter extends FragmentStateAdapter{

    public OrdersAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new PreviousOrders();
        }
        return new CurrentOrders();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}