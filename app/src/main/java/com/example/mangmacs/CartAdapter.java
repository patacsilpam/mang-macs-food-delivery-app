package com.example.mangmacs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList productId,productName,productPrice;
    Context context;
    CartAdapter(Context context, ArrayList productId, ArrayList productName, ArrayList productPrice){
        this.context = context;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
       holder.textProductName.setText(String.valueOf(productName.get(position)));
       holder.textProductPrice.setText(String.valueOf(productPrice.get(position)));
    }

    @Override
    public int getItemCount() {
        return productId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textProductName,textProductPrice;
        private TextView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.txtProductName);
            textProductPrice = itemView.findViewById(R.id.txtProductPrice);
        }
    }
}
