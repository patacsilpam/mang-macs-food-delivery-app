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
import com.example.mangmacs.activities.SoupListDetail;
import com.example.mangmacs.activities.WineListDetailActivity;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class WineAdapter extends RecyclerView.Adapter<WineAdapter.ProductViewHolder> {
    private List<ProductListModel> wineList;
    private Context context;
    public WineAdapter(Context context, List<ProductListModel> wineList){
        this.context = context;
        this.wineList = wineList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductListModel wineListModel = wineList.get(position);
        Glide.with(context)
                .load(wineListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(wineListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(wineListModel.getPrice()+".00"));
        holder.textDevTime.setText(wineListModel.getPreparationTime().concat(" mins"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WineListDetailActivity.class);
                intent.putExtra("image", wineListModel.getImage());
                intent.putExtra("productName", wineListModel.getProductName());
                intent.putExtra("productCategory",wineListModel.getProductCategoryCombo());
                intent.putExtra("price", wineListModel.getPrice());
                intent.putExtra("productVariation", wineListModel.getProductVariation());
                intent.putExtra("code", wineListModel.getCodeCombo());
                intent.putExtra("status", wineListModel.getStocks());
                intent.putExtra("preparationTime",wineListModel.getPreparationTime());
                intent.putExtra("mainIngredients",wineListModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wineList.size();
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
