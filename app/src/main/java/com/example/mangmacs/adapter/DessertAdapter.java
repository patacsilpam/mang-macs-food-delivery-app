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
import com.example.mangmacs.activities.DessertListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> dessertList;
    public DessertAdapter(Context context, List<ProductListModel> dessertList){
        this.context = context;
        this.dessertList = dessertList;
    }

    @NonNull
    @Override
    public DessertAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DessertAdapter.ProductViewHolder holder, int position) {
        ProductListModel dessertListModel = dessertList.get(position);
        Glide.with(context)
                .load(dessertListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(dessertListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(dessertListModel.getPrice()+".00"));
        holder.textDevTime.setText(dessertListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DessertListDetail.class);
                intent.putExtra("image", dessertListModel.getImage());
                intent.putExtra("productName", dessertListModel.getProductName());
                intent.putExtra("productCategory",dessertListModel.getProductCategoryCombo());
                intent.putExtra("price", dessertListModel.getPrice());
                intent.putExtra("productVariation", dessertListModel.getProductVariation());
                intent.putExtra("code", dessertListModel.getCodeCombo());
                intent.putExtra("status", dessertListModel.getStocks());
                intent.putExtra("preparationTime",dessertListModel.getPreparationTime());
                intent.putExtra("mainIngredients",dessertListModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dessertList.size();
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
