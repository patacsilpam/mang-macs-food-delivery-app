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
import com.example.mangmacs.activities.DrinksListDetail;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> drinksList;
    public DrinksAdapter(Context context, List<ProductListModel> drinksList){
        this.context = context;
        this.drinksList = drinksList;
    }
    @NonNull
    @Override
    public DrinksAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksAdapter.ProductViewHolder holder, int position) {
        ProductListModel drinksListModel = drinksList.get(position);
        Glide.with(context)
                .load(drinksListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(drinksListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(drinksListModel.getPrice()+".00"));
        holder.textDevTime.setText(drinksListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DrinksListDetail.class);
                intent.putExtra("image",drinksListModel.getImage());
                intent.putExtra("productName",drinksListModel.getProductName());
                intent.putExtra("productCategory",drinksListModel.getProductCategoryCombo());
                intent.putExtra("price",drinksListModel.getPrice());
                intent.putExtra("productVariation",drinksListModel.getProductVariation());
                intent.putExtra("code",drinksListModel.getCodeCombo());
                intent.putExtra("status", drinksListModel.getStocks());
                intent.putExtra("preparationTime",drinksListModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
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
