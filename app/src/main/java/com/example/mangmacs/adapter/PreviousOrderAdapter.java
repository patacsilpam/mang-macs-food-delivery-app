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
import com.example.mangmacs.PreviousOrderDetailsActivity;
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
        Glide.with(context).load(previousOrderModel.getImgProduct()).into(holder.imgProduct);
        int subTotal = Integer.parseInt(previousOrderModel.getPrice()) * Integer.parseInt(previousOrderModel.getQuantities());
        holder.textProduct.setText(previousOrderModel.getProducts());
        holder.textVariation.setText(previousOrderModel.getVariations());
        holder.textPrice.setText(previousOrderModel.getPrice());
        holder.items.setText(previousOrderModel.getQuantities());
        holder.orderStatus.setText(previousOrderModel.getOrderStatus());
        holder.orderType.setText(previousOrderModel.getOrderType());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PreviousOrderDetailsActivity.class);
                intent.putExtra("customerName",previousOrderModel.getCustomerName());
                intent.putExtra("phoneNumber",previousOrderModel.getContactNumber());
                intent.putExtra("address",previousOrderModel.getCustomerAddress());
                intent.putExtra("productCode",previousOrderModel.getProductCode());
                intent.putExtra("product",previousOrderModel.getProducts());
                intent.putExtra("variation",previousOrderModel.getVariations());
                intent.putExtra("price",previousOrderModel.getPrice());
                intent.putExtra("subtotal",previousOrderModel.getSubTotal());
                intent.putExtra("quantity",previousOrderModel.getQuantities());
                intent.putExtra("orderStatus",previousOrderModel.getOrderStatus());
                intent.putExtra("orderType",previousOrderModel.getOrderType());
                intent.putExtra("imgProduct",previousOrderModel.getImgProduct());
                intent.putExtra("totalAmount",previousOrderModel.getTotalAmount());
                intent.putExtra("addOns",previousOrderModel.getAddOns());
                intent.putExtra("paymentPhoto",previousOrderModel.getPaymentPhoto());
                intent.putExtra("orderTime",previousOrderModel.getOrderedDate());
                intent.putExtra("deliveryTime",previousOrderModel.getRequiredTime());
                intent.putExtra("paymentTime",previousOrderModel.getOrderedDate());
                intent.putExtra("completedTime",previousOrderModel.getOrderedDate());
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
        private TextView textProduct,textVariation,items,textPrice,orderStatus,orderType;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textProduct = itemView.findViewById(R.id.textProduct);
            textVariation = itemView.findViewById(R.id.textVariation);
            textPrice = itemView.findViewById(R.id.textPrice);
            items = itemView.findViewById(R.id.items);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderType = itemView.findViewById(R.id.orderType);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
