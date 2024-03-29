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
import com.example.mangmacs.R;
import com.example.mangmacs.activities.PulutanListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class PulutanAdapter extends RecyclerView.Adapter<PulutanAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> ricecupList;
    public PulutanAdapter(Context context, List<ProductListModel> ricecupList){
        this.context = context;
        this.ricecupList = ricecupList;
    }
    @NonNull
    @Override
    public PulutanAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PulutanAdapter.ProductViewHolder holder, int position) {
        ProductListModel pulutanListModel = ricecupList.get(position);
        Glide.with(context)
                .load(pulutanListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(pulutanListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(pulutanListModel.getPrice()+".00"));
        holder.textDevTime.setText(pulutanListModel.getPreparationTime().concat(" mins"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PulutanListDetail.class);
                intent.putExtra("image", pulutanListModel.getImage());
                intent.putExtra("productName", pulutanListModel.getProductName());
                intent.putExtra("productCategory",pulutanListModel.getProductCategoryCombo());
                intent.putExtra("price", pulutanListModel.getPrice());
                intent.putExtra("productVariation", pulutanListModel.getProductVariation());
                intent.putExtra("code", pulutanListModel.getCodeCombo());
                intent.putExtra("status", pulutanListModel.getStocks());
                intent.putExtra("preparationTime",pulutanListModel.getPreparationTime());
                intent.putExtra("mainIngredients",pulutanListModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ricecupList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textProductName,textProductPrice,textDevTime;
        CardView productContainer;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textProductName = itemView.findViewById(R.id.productName);
            textProductPrice = itemView.findViewById(R.id.productPrice);
            productContainer = itemView.findViewById(R.id.productContainer);
            textDevTime = itemView.findViewById(R.id.devTime);
        }
    }
}
