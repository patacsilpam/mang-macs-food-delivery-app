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
import com.example.mangmacs.activities.PastaListDetail;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class PastaAdapter extends RecyclerView.Adapter<PastaAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> pastaLists;
    public PastaAdapter(Context context, List<ProductListModel> pastaList){
        this.context = context;
        this.pastaLists = pastaList;
    }
    @NonNull
    @Override
    public PastaAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastaAdapter.ProductViewHolder holder, int position) {
        ProductListModel pastaListModel = pastaLists.get(position);
        Glide.with(context)
                .load(pastaListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(pastaListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(pastaListModel.getPrice()+".00"));
        holder.textDevTime.setText(pastaListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PastaListDetail.class);
                intent.putExtra("image",pastaListModel.getImage());
                intent.putExtra("productName",pastaListModel.getProductName());
                intent.putExtra("productCategory",pastaListModel.getProductCategoryCombo());
                intent.putExtra("price",pastaListModel.getPrice());
                intent.putExtra("productVariation",pastaListModel.getProductVariation());
                intent.putExtra("code",pastaListModel.getCodeCombo());
                intent.putExtra("status", pastaListModel.getStocks());
                intent.putExtra("preparationTime",pastaListModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastaLists.size();
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
