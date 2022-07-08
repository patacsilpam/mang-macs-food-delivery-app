package com.example.mangmacs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

public class BookNotificationAdapter extends RecyclerView.Adapter<BookNotificationAdapter.ViewHolder> {
    private Context context;
    private List<ReservationModel> reservationLists;
    public BookNotificationAdapter(Context context,List<ReservationModel> reservationLists){
        this.context = context;
        this.reservationLists = reservationLists;
    }
    @NonNull
    @Override
    public BookNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.book_update_lists,null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookNotificationAdapter.ViewHolder holder, int position) {
        ReservationModel reservationModel = reservationLists.get(position);
        String bookStatus = reservationModel.getStatus();
        String guests = reservationModel.getGuests();
        String date = reservationModel.getScheduled_date();
        String time = reservationModel.getScheduled_time();
        String refNumber = reservationModel.getRefNumber();
        String notifDate = reservationModel.getNotifDate();
        holder.notifDate.setText(notifDate);
        if (bookStatus.equals("Reserved")){
            holder.orderStatus.setText(bookStatus);
            holder.orderMessage.setText("Your table reservation" + refNumber + " for " + guests + " guests is already confirmed. See you at " + date + " " + time);
        }
        else if(bookStatus.equals("Not Available")){
            holder.orderStatus.setText(bookStatus);
            holder.orderMessage.setText("Sorry, there is no available table for accomodation.");
        }
        else{
            holder.cardView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reservationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderStatus,orderMessage,notifDate;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderMessage = itemView.findViewById(R.id.orderMessage);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            cardView = itemView.findViewById(R.id.cardView);
            notifDate = itemView.findViewById(R.id.notifDate);
        }
    }
}
