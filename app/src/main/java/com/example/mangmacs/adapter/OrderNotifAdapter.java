package com.example.mangmacs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.List;

public class OrderNotifAdapter extends RecyclerView.Adapter<OrderNotifAdapter.ViewHolder> {
    private List<CurrentOrdersModel> notifLists;
    private Context context;
    public OrderNotifAdapter(Context context, List<CurrentOrdersModel> notifLists){
        this.context = context;
        this.notifLists = notifLists;
    }
    @NonNull
    @Override
    public OrderNotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_update_lists,null);
        return new OrderNotifAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderNotifAdapter.ViewHolder holder, int position) {
        CurrentOrdersModel currentOrdersModel = notifLists.get(position);
        String orderStatus = currentOrdersModel.getOrderStatus();
        String orderNumber = currentOrdersModel.getOrderNumber();
        String notifDate = currentOrdersModel.getNotifDate();
        Glide.with(context).load(currentOrdersModel.getImgProduct()).into(holder.imgProduct);
        holder.notifDate.setText(notifDate);
        if (orderStatus.equals("Order Received")){
            holder.orderStatus.setText(orderStatus);
            holder.orderMessage.setText("Your order " + orderNumber + " has been confirm.");
        }
        else if(orderStatus.equals("Order Processing")){
            holder.orderStatus.setText(orderStatus);
            holder.orderMessage.setText("Your order " + orderNumber + " is on its processs.");
        }
        else if(orderStatus.equals("Out for Delivery")){
            holder.orderStatus.setText(orderStatus);
            holder.orderMessage.setText("Your order " + orderNumber + " is out for delivery.");
        }
        else if(orderStatus.equals("Order Completed")){
            holder.orderStatus.setText(orderStatus);
            holder.orderMessage.setText("Your order " + orderNumber + " has been delivered.");
        }
       else if(orderStatus.equals("Ready for Pick Up")){
                holder.orderStatus.setText(orderStatus);
                holder.orderMessage.setText("Your order " + orderNumber + " is ready for pick up.");
        }
       else{
           holder.cardView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return notifLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderStatus,orderMessage,notifDate;
        private ImageView imgProduct;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderMessage = itemView.findViewById(R.id.orderMessage);
            notifDate = itemView.findViewById(R.id.notifDate);
            imgProduct = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
