package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.MenuActivty;
import com.example.mangmacs.activities.ReservationActivity;
import com.example.mangmacs.adapter.CurrentBookingAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.ReservationModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentReservation extends Fragment {
    private RecyclerView currentBookingLists;
    private List<ReservationModel> reservationModel;
    private CurrentBookingAdapter currentBookingAdapter;
    private View emptyBook;
    private Button addBook;
    private ProgressBar progressBar;
    private int countCart;
    public CurrentReservation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.spin_kit);
        emptyBook = view.findViewById(R.id.emptyBook);
        addBook = getView().findViewById(R.id.addBook);
        currentBookingLists = view.findViewById(R.id.currentBookingLists);
        currentBookingLists.setLayoutManager(new LinearLayoutManager(getActivity()));
        currentBookingLists.setHasFixedSize(true);
        showCurrentReservation();
    }

    private void showCurrentReservation() {
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.VISIBLE);
        String email = SharedPreference.getSharedPreference(getActivity()).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<ReservationModel>> callCurrentBookings = apiInterface.getCurrentBookings(email);
        callCurrentBookings.enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                progressBar.setVisibility(View.GONE);
                reservationModel = response.body();
                currentBookingAdapter = new CurrentBookingAdapter(getActivity(),reservationModel);
                currentBookingLists.setAdapter(currentBookingAdapter);
                countCart = currentBookingLists.getAdapter().getItemCount();
                emptyBook.setVisibility(View.GONE);
                if (countCart == 0){
                    emptyBook.setVisibility(View.VISIBLE);
                    addBook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), MenuActivty.class));
                        }
                    });
                } else{
                    emptyBook.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}