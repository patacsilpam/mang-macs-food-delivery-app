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

public class ChickenAdapter extends RecyclerView.Adapter<ChickenAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> chickenList;
    public ChickenAdapter(Context context, List<ProductListModel> chickenList){
        this.context = context;
        this.chickenList = chickenList;
    }
    @NonNull
    @Override
    public ChickenAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ChickenAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChickenAdapter.ProductViewHolder holder, int position) {
        ProductListModel chickenModel = chickenList.get(position);
        Glide.with(context)
                .load(chickenModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(chickenModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(chickenModel.getPrice()+".00"));
        holder.textDevTime.setText(chickenModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image", chickenModel.getImage());
                intent.putExtra("productName", chickenModel.getProductName());
                intent.putExtra("productCategory",chickenModel.getProductCategoryCombo());
                intent.putExtra("price", chickenModel.getPrice());
                intent.putExtra("productVariation", chickenModel.getProductVariation());
                intent.putExtra("code", chickenModel.getCodeCombo());
                intent.putExtra("status", chickenModel.getStocks());
                intent.putExtra("preparationTime",chickenModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chickenList.size();
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
