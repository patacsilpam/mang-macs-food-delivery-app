package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.CurrentOrderDetailsActivity;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.MyOrdersActivity;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.List;

public class CurrentOrdersAdapter extends RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder>{
    private Context context;
    private List<CurrentOrdersModel> currentOrderList;
    public CurrentOrdersAdapter(Context context,List<CurrentOrdersModel> currentOrderLists){
        this.context = context;
        this.currentOrderList = currentOrderLists;
    }
    @NonNull
    @Override
    public CurrentOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_orders,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentOrdersAdapter.ViewHolder holder, int position) {
        CurrentOrdersModel currentOrdersModel = currentOrderList.get(position);
        Glide.with(context).load(currentOrdersModel.getImgProduct()).into(holder.imgProduct);
        holder.textProduct.setText(currentOrdersModel.getProducts());
        holder.textVariation.setText(currentOrdersModel.getVariations());
        holder.items.setText(currentOrdersModel.getQuantities());
        holder.textPrice.setText(currentOrdersModel.getPrice());
        holder.orderStatus.setText(currentOrdersModel.getOrderStatus());
        holder.orderType.setText(currentOrdersModel.getOrderType());
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context,CurrentOrderDetailsActivity.class);
                intent.putExtra("deliveryTime",currentOrdersModel.getRequiredDate().concat(" ").concat(currentOrdersModel.getRequiredTime()));
                intent.putExtra("customerName",currentOrdersModel.getCustomerName());
                intent.putExtra("recipientName",currentOrdersModel.getRecipientName());
                intent.putExtra("email",currentOrdersModel.getEmail());
                intent.putExtra("phoneNumber",currentOrdersModel.getContactNumber());
                intent.putExtra("labelAddress",currentOrdersModel.getLabelAddress());
                intent.putExtra("address",currentOrdersModel.getCustomerAddress());
                intent.putExtra("orderNumber",currentOrdersModel.getOrderNumber());
                intent.putExtra("orderType",currentOrdersModel.getOrderType());
                intent.putExtra("orderStatus",currentOrdersModel.getOrderStatus());
                intent.putExtra("totalAmount",currentOrdersModel.getTotalAmount());
                intent.putExtra("paymentMethod",currentOrdersModel.getPaymentType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView textProduct,textVariation,items,textPrice,orderStatus,orderType,viewMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textProduct = itemView.findViewById(R.id.textProduct);
            textVariation = itemView.findViewById(R.id.textVariation);
            textPrice = itemView.findViewById(R.id.textPrice);
            items = itemView.findViewById(R.id.items);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderType = itemView.findViewById(R.id.orderType);
            viewMore = itemView.findViewById(R.id.viewMore);
        }
    }
}
