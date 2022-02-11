package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.CurrentReservationActivity;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

public class CurrentBookingAdapter extends RecyclerView.Adapter<CurrentBookingAdapter.ViewHolder> {
    private Context context;
    private List<ReservationModel> reservationLists;
    public CurrentBookingAdapter(Context context,List<ReservationModel> reservationLists){
        this.context = context;
        this.reservationLists = reservationLists;
    }
    @NonNull
    @Override
    public CurrentBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_booking,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentBookingAdapter.ViewHolder holder, int position) {
        ReservationModel reservationModel = reservationLists.get(position);
        holder.reservationName.setText(reservationModel.getFname().concat(" ").concat(reservationModel.getLname()));
        holder.reservedDateTime.setText(reservationModel.getScheduled_date().concat(" - ").concat(reservationModel.getScheduled_time()));
        holder.guests.setText(reservationModel.getGuests().concat(" people"));
        holder.createdAt.setText(reservationModel.getCreatedAt());
        holder.reservationStatus.setText(reservationModel.getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrentReservationActivity.class);
                intent.putExtra("id",reservationModel.getId());
                intent.putExtra("firstName",reservationModel.getFname());
                intent.putExtra("lastName",reservationModel.getLname());
                intent.putExtra("email",reservationModel.getEmail());
                intent.putExtra("schedDate",reservationModel.getScheduled_date());
                intent.putExtra("schedTime",reservationModel.getScheduled_time());
                intent.putExtra("guests",reservationModel.getGuests());
                intent.putExtra("bookingStatus",reservationModel.getStatus());
                intent.putExtra("createdAt",reservationModel.getCreatedAt());
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
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationName = itemView.findViewById(R.id.reservedName);
            reservedDateTime = itemView.findViewById(R.id.reservedDateTime);
            guests = itemView.findViewById(R.id.guests);
            createdAt = itemView.findViewById(R.id.createdAt);
            reservationStatus = itemView.findViewById(R.id.reservationStatus);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
