package com.example.mangmacs.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mangmacs.fragment.LaterPickUp;
import com.example.mangmacs.fragment.NowPickUp;

public class PickUpAdapter extends FragmentStateAdapter {
    public PickUpAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new LaterPickUp();
        }
        return new NowPickUp();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
