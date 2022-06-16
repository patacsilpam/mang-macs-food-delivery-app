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
import com.example.mangmacs.activities.MealsGoodListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class VegetableAdapter extends RecyclerView.Adapter<VegetableAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> vegetableList;
    public VegetableAdapter(Context context,List<ProductListModel> vegetableList){
        this.context = context;
        this.vegetableList = vegetableList;
    }
    @NonNull
    @Override
    public VegetableAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new VegetableAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableAdapter.ProductViewHolder holder, int position) {
        ProductListModel vegetableModel = vegetableList.get(position);
        Glide.with(context)
                .load(vegetableModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(vegetableModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(vegetableModel.getPrice()+".00"));
        holder.textDevTime.setText(vegetableModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image",vegetableModel.getImage());
                intent.putExtra("productName",vegetableModel.getProductName());
                intent.putExtra("productCategory",vegetableModel.getProductCategoryCombo());
                intent.putExtra("price",vegetableModel.getPrice());
                intent.putExtra("productVariation",vegetableModel.getProductVariation());
                intent.putExtra("code",vegetableModel.getCodeCombo());
                intent.putExtra("status", vegetableModel.getStocks());
                intent.putExtra("preparationTime",vegetableModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vegetableList.size();
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
