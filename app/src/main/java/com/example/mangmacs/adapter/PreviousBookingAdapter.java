package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.activities.ReservationActivity;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

public class PreviousBookingAdapter extends RecyclerView.Adapter<PreviousBookingAdapter.ViewHolder> {
    private Context context;
    private List<ReservationModel> reservationLists;
    public PreviousBookingAdapter(Context context,List<ReservationModel> reservationLists){
        this.context = context;
        this.reservationLists = reservationLists;
    }
    @NonNull
    @Override
    public PreviousBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_booking,parent,false);
        return new PreviousBookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousBookingAdapter.ViewHolder holder, int position) {
        ReservationModel reservationModel = reservationLists.get(position);
        holder.reservationName.setText(reservationModel.getFname().concat(" ").concat(reservationModel.getLname()));
        holder.reservedDateTime.setText(reservationModel.getScheduled_date().concat(" - ").concat(reservationModel.getScheduled_time()));
        holder.guests.setText(reservationModel.getGuests().concat(" people"));
        holder.createdAt.setText(reservationModel.getCreatedAt());
        holder.reservationStatus.setText(reservationModel.getStatus());
        holder.reservationStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReservationActivity.class);
                intent.putExtra("firstname",reservationModel.getFname());
                intent.putExtra("lastname",reservationModel.getLname());
                intent.putExtra("time",reservationModel.getScheduled_time());
                intent.putExtra("guests",reservationModel.getGuests());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reservationName,reservedDateTime,guests,createdAt,reservationStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationName = itemView.findViewById(R.id.reservedName);
            reservedDateTime = itemView.findViewById(R.id.reservedDateTime);
            guests = itemView.findViewById(R.id.guests);
            createdAt = itemView.findViewById(R.id.createdAt);
            reservationStatus = itemView.findViewById(R.id.reservationStatus);
        }
    }
}
