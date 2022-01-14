package com.example.mangmacs.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mangmacs.fragment.orderLater;
import com.example.mangmacs.fragment.orderNow;

public class OrderNowLaterAdapter extends FragmentStateAdapter {
    public OrderNowLaterAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new orderLater();
        }
        return new orderNow();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
