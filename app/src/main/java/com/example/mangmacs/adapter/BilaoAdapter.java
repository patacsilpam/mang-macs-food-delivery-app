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
import com.example.mangmacs.activities.BilaoListDetail;
import com.example.mangmacs.model.PancitBilaoListModel;
import com.example.mangmacs.R;

import java.util.List;

public class BilaoAdapter extends RecyclerView.Adapter<BilaoAdapter.ProductViewHolder> {
    private Context context;
    private List<PancitBilaoListModel> pancitBilaoList;
    public BilaoAdapter(Context context,List<PancitBilaoListModel> pancitBilaoList){
        this.context = context;
        this.pancitBilaoList = pancitBilaoList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.bilao_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PancitBilaoListModel pancitBilaoListModel = pancitBilaoList.get(position);
        Glide.with(context)
                .load(pancitBilaoListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(pancitBilaoListModel.getProductName());
        holder.textProductPrice.setText(String.valueOf(pancitBilaoListModel.getPrice()));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BilaoListDetail.class);
                intent.putExtra("image",pancitBilaoListModel.getImage());
                intent.putExtra("productName",pancitBilaoListModel.getProductName());
                intent.putExtra("price",pancitBilaoListModel.getPrice());
                intent.putExtra("status",pancitBilaoListModel.getStatus());
                intent.putExtra("productVariation",pancitBilaoListModel.getProductVariation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pancitBilaoList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textProductName,textProductPrice;
        CardView productContainer;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textProductName = itemView.findViewById(R.id.productName);
            textProductPrice = itemView.findViewById(R.id.productPrice);
            productContainer = itemView.findViewById(R.id.productContainer);
        }
    }
}
