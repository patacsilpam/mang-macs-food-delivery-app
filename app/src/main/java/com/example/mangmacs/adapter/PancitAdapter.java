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
import com.example.mangmacs.activities.PancitListDetail;
import com.example.mangmacs.model.PancitListModel;
import com.example.mangmacs.R;

import java.util.List;

public class PancitAdapter extends RecyclerView.Adapter<PancitAdapter.ProductViewHolder> {
    private Context context;
    private List<PancitListModel> pancitList;
    public PancitAdapter(Context context,List<PancitListModel> pancitList){
        this.context = context;
        this.pancitList = pancitList;
    }
    @NonNull
    @Override
    public PancitAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pancit_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PancitAdapter.ProductViewHolder holder, int position) {
        PancitListModel pancitListModel = pancitList.get(position);
        Glide.with(context)
                .load(pancitListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(pancitListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(pancitListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PancitListDetail.class);
                intent.putExtra("image",pancitListModel.getImage());
                intent.putExtra("productName",pancitListModel.getProductName());
                intent.putExtra("price",pancitListModel.getPrice());
                intent.putExtra("status",pancitListModel.getStatus());
                intent.putExtra("productVariation",pancitListModel.getProductVariation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pancitList.size();
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
