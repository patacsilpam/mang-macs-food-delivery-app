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
import com.example.mangmacs.activities.AppetizerListDetail;
import com.example.mangmacs.activities.MealsGoodListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class SeafoodsAdapter extends RecyclerView.Adapter<SeafoodsAdapter.ProductViewHolder>{
    private Context context;
    private List<ProductListModel> seafoodsList;
    public SeafoodsAdapter(Context context, List<ProductListModel> seafoodsList){
        this.context = context;
        this.seafoodsList = seafoodsList;
    }
    @NonNull
    @Override
    public SeafoodsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new SeafoodsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeafoodsAdapter.ProductViewHolder holder, int position) {
        ProductListModel seafoodsModel = seafoodsList.get(position);
        Glide.with(context)
                .load(seafoodsModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(seafoodsModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(seafoodsModel.getPrice()+".00"));
        holder.textDevTime.setText(seafoodsModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image", seafoodsModel.getImage());
                intent.putExtra("productName", seafoodsModel.getProductName());
                intent.putExtra("productCategory",seafoodsModel.getProductCategoryCombo());
                intent.putExtra("price", seafoodsModel.getPrice());
                intent.putExtra("productVariation", seafoodsModel.getProductVariation());
                intent.putExtra("code", seafoodsModel.getCodeCombo());
                intent.putExtra("status", seafoodsModel.getStocks());
                intent.putExtra("preparationTime",seafoodsModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seafoodsList.size();
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
