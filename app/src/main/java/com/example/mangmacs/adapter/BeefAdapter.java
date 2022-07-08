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

public class BeefAdapter extends RecyclerView.Adapter<BeefAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> beeflist;
    public BeefAdapter(Context context, List<ProductListModel> beeflist){
        this.context = context;
        this.beeflist = beeflist;
    }
    @NonNull
    @Override
    public BeefAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new BeefAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeefAdapter.ProductViewHolder holder, int position) {
        ProductListModel beefModel = beeflist.get(position);
        Glide.with(context)
                .load(beefModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(beefModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(beefModel.getPrice()+".00"));
        holder.textDevTime.setText(beefModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image", beefModel.getImage());
                intent.putExtra("productName", beefModel.getProductName());
                intent.putExtra("productCategory",beefModel.getProductCategoryCombo());
                intent.putExtra("price", beefModel.getPrice());
                intent.putExtra("productVariation", beefModel.getProductVariation());
                intent.putExtra("code", beefModel.getCodeCombo());
                intent.putExtra("status", beefModel.getStocks());
                intent.putExtra("preparationTime",beefModel.getPreparationTime());
                intent.putExtra("mainIngredients",beefModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beeflist.size();
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
