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
        String getProducts = currentOrdersModel.getProducts();
        String[] splitProduct = getProducts.split(",");
        for (int i =0; i<=2; i++){
            holder.textProduct.setText(splitProduct[i]);
        }
        //
        String getVariations = currentOrdersModel.getVariations();
        String[] splitVariations = getVariations.split(",");
        for (String variations : splitVariations){
            holder.textVariation.setText(variations);
            holder.cardView.setVisibility(View.VISIBLE);
        }
        //
        String getPrices = currentOrdersModel.getPrice();
        String[] splitPrices = getPrices.split(",");
        for (String prices : splitPrices){
            holder.textPrice.setText(prices);
        }
        //
        String getItems = currentOrdersModel.getQuantities();
        String[] splitItems = getItems.split(",");
        for (String items : splitItems){
            holder.items.setText(items);
        }
        //
        String getImgProducts = currentOrdersModel.getImgProduct();
        String[] splitImgProduct = getImgProducts.split(",");
        for (String imgProducts : splitImgProduct){
            Glide.with(context).load(imgProducts).into(holder.imgProduct);
        }
        holder.orderStatus.setText(currentOrdersModel.getOrderStatus());
        holder.orderType.setText(currentOrdersModel.getOrderType());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrentOrderDetailsActivity.class);
                intent.putExtra("id",currentOrdersModel.getId());
                intent.putExtra("customerName",currentOrdersModel.getCustomerName());
                intent.putExtra("phoneNumber",currentOrdersModel.getContactNumber());
                intent.putExtra("address",currentOrdersModel.getCustomerAddress());
                intent.putExtra("product",currentOrdersModel.getProducts().replace(",",""));
                intent.putExtra("variation",currentOrdersModel.getVariations().replace(",",""));
                intent.putExtra("price",currentOrdersModel.getPrice().replace(",",""));
                intent.putExtra("subtotal",currentOrdersModel.getSubTotal().replace(",",""));
                intent.putExtra("quantity",currentOrdersModel.getQuantities().replace(",",""));
                intent.putExtra("imgProduct",currentOrdersModel.getImgProduct());
                intent.putExtra("paymentPhoto",currentOrdersModel.getPaymentPhoto());
                intent.putExtra("orderStatus",currentOrdersModel.getOrderStatus());
                intent.putExtra("orderType",currentOrdersModel.getOrderType());
                intent.putExtra("totalAmount",currentOrdersModel.getTotalAmount());
                intent.putExtra("addOns",currentOrdersModel.getAddOns().replace("",""));
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
