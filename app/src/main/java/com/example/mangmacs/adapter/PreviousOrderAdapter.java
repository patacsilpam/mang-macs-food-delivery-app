package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.activities.PreviousOrderDetailsActivity;
import com.example.mangmacs.R;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.List;

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.ViewHolder> {
    private Context context;
    private List<CurrentOrdersModel> previousOrderList;
    public PreviousOrderAdapter(Context context,List<CurrentOrdersModel> previousOrderList){
        this.context = context;
        this.previousOrderList = previousOrderList;
    }
    @NonNull
    @Override
    public PreviousOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_orders,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousOrderAdapter.ViewHolder holder, int position) {
        CurrentOrdersModel previousOrderModel = previousOrderList.get(position);
        int productPrice = Integer.parseInt(previousOrderModel.getPrice());
        int addOnsPrice = Integer.parseInt(previousOrderModel.getAddOnsFee());
        Glide.with(context).load(previousOrderModel.getImgProduct()).into(holder.imgProduct);
        holder.textProduct.setText(previousOrderModel.getProducts());
        holder.textVariation.setText(previousOrderModel.getVariations());
        holder.textPrice.setText(String.valueOf(productPrice + addOnsPrice));
        holder.items.setText(previousOrderModel.getQuantities());
        holder.orderStatus.setText(previousOrderModel.getOrderStatus());
        holder.orderType.setText(previousOrderModel.getOrderType());
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PreviousOrderDetailsActivity.class);
                intent.putExtra("orderDate",previousOrderModel.getOrderedDate());
                intent.putExtra("deliveryTime",previousOrderModel.getRequiredDate().concat(" ").concat(previousOrderModel.getRequiredTime()));
                intent.putExtra("completedTime",previousOrderModel.getCompletedTime());
                intent.putExtra("customerName",previousOrderModel.getCustomerName());
                intent.putExtra("recipientName",previousOrderModel.getRecipientName());
                intent.putExtra("email",previousOrderModel.getEmail());
                intent.putExtra("phoneNumber",previousOrderModel.getContactNumber());
                intent.putExtra("labelAddress",previousOrderModel.getLabelAddress());
                intent.putExtra("address",previousOrderModel.getCustomerAddress());
                intent.putExtra("orderNumber",previousOrderModel.getOrderNumber());
                intent.putExtra("orderType",previousOrderModel.getOrderType());
                intent.putExtra("orderStatus",previousOrderModel.getOrderStatus());
                intent.putExtra("totalAmount",previousOrderModel.getTotalAmount());
                intent.putExtra("paymentMethod",previousOrderModel.getPaymentType());
                intent.putExtra("deliveryFee",previousOrderModel.getDeliveryChange());
                intent.putExtra("courierName",previousOrderModel.getCourierName());
                intent.putExtra("paymentNumber",previousOrderModel.getPaymentNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return previousOrderList.size() ;
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
