package com.example.mangmacs.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.CurrentBookingAdapter;
import com.example.mangmacs.adapter.PreviousBookingAdapter;
import com.example.mangmacs.adapter.PreviousOrderAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CurrentOrdersModel;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousReservation extends Fragment {
    private RecyclerView previousBookingLists;
    private List<ReservationModel> reservationModel;
    private PreviousBookingAdapter previousBookingAdapter;
    public PreviousReservation() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previousBookingLists = view.findViewById(R.id.previousBookingLists);
        previousBookingLists.setLayoutManager(new LinearLayoutManager(getActivity()));
        previousBookingLists.setHasFixedSize(true);
        String email = SharedPreference.getSharedPreference(getActivity()).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<ReservationModel>> callCurrentBookings = apiInterface.getPreviousBookings(email);
        callCurrentBookings.enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                reservationModel = response.body();
                previousBookingAdapter = new PreviousBookingAdapter(getActivity(),reservationModel);
                previousBookingLists.setAdapter(previousBookingAdapter);
            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {

            }
        });
    }
}