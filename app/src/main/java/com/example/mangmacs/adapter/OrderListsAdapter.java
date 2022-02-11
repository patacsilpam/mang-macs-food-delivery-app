package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.PancitBilaoListModel;

import java.util.List;

public class OrderListsAdapter extends RecyclerView.Adapter<OrderListsAdapter.ProductViewHolder> {
    private Context context;
    private List<CartModel> orderList;
    public OrderListsAdapter(Context context, List<CartModel> orderList){
        this.context = context;
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public OrderListsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order_lists,null);
        return new OrderListsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListsAdapter.ProductViewHolder holder, int position) {
        CartModel orderModel = orderList.get(position);
        Glide.with(context).load(orderModel.getImageProduct()).into(holder.imgProduct);
        String variation = orderModel.getVariationCart();
        String[] splitVariation =  variation.split(",");
        holder.product.setText(orderModel.getProoductNameCart());
        holder.variation.setText(splitVariation[0]);
        holder.items.setText(String.valueOf(orderModel.getQuantityCart()));
        holder.price.setText(String.valueOf(orderModel.getPriceCart()));

        int price = orderModel.getQuantityCart() * orderModel.getPriceCart();
        Intent intent = new Intent("TotalOrderPrice");
        intent.putExtra("totalorderprice",String.valueOf(orderModel.getTotalprice()));
        intent.putExtra("productCode",orderModel.getProductCodeCart());
        intent.putExtra("productName",orderModel.getProoductNameCart());
        intent.putExtra("variation",orderModel.getVariationCart());
        intent.putExtra("quantity",orderModel.getQuantityCart());
        intent.putExtra("add_ons",orderModel.getAdd_onsCart());
        intent.putExtra("price",orderModel.getPriceCart());
        intent.putExtra("subtotal",price);
        intent.putExtra("imgProduct",orderModel.getImageProduct());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView product,variation,items,price;
        private ImageView imgProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product);
            variation = itemView.findViewById(R.id.variation);
            items = itemView.findViewById(R.id.items);
            price = itemView.findViewById(R.id.total);
            imgProduct = itemView.findViewById(R.id.image);
        }
    }
}
