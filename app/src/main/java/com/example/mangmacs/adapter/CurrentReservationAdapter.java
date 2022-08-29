package com.example.mangmacs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

public class CurrentReservationAdapter extends RecyclerView.Adapter<CurrentReservationAdapter.ViewHolder> {
    private List<ReservationModel> reservationList;
    private Context context;
    public CurrentReservationAdapter(Context context, List<ReservationModel> reservationList){
        this.context = context;
        this.reservationList = reservationList;
    }
    @NonNull
    @Override
    public CurrentReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order_details,parent,false);
        return new CurrentReservationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentReservationAdapter.ViewHolder holder, int position) {
        ReservationModel reservationModel = reservationList.get(position);
        Glide.with(context).load(reservationModel.getImgProduct()).into(holder.imgProduct);
        holder.textProduct.setText(reservationModel.getProducts());
        holder.textVariation.setText(reservationModel.getVariations());
        holder.items.setText(reservationModel.getQuantities());
        holder.textPrice.setText(reservationModel.getPrice());
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView textProduct,textVariation,items,textPrice;
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
             imgProduct = itemView.findViewById(R.id.imgProduct);
             textProduct = itemView.findViewById(R.id.textProduct);
             textVariation = itemView.findViewById(R.id.textVariation);
             textPrice = itemView.findViewById(R.id.textPrice);
             items = itemView.findViewById(R.id.items);
        }
    }
}
