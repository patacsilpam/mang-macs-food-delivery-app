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
import com.example.mangmacs.activities.SizzlingListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class SizzlingAdapter extends RecyclerView.Adapter<SizzlingAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> sizzlingList;
    public SizzlingAdapter(Context context, List<ProductListModel> sizzlingList){
        this.context = context;
        this.sizzlingList = sizzlingList;
    }
    @NonNull
    @Override
    public SizzlingAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new SizzlingAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizzlingAdapter.ProductViewHolder holder, int position) {
        ProductListModel sizzlingListModel = sizzlingList.get(position);
        Glide.with(context)
                .load(sizzlingListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(sizzlingListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(sizzlingListModel.getPrice()+".00"));
        holder.textDevTime.setText(sizzlingListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SizzlingListDetail.class);
                intent.putExtra("image", sizzlingListModel.getImage());
                intent.putExtra("productName", sizzlingListModel.getProductName());
                intent.putExtra("productCategory",sizzlingListModel.getProductCategoryCombo());
                intent.putExtra("price", sizzlingListModel.getPrice());
                intent.putExtra("productVariation", sizzlingListModel.getProductVariation());
                intent.putExtra("code", sizzlingListModel.getCodeCombo());
                intent.putExtra("status", sizzlingListModel.getStocks());
                intent.putExtra("preparationTime",sizzlingListModel.getPreparationTime());
                intent.putExtra("mainIngredients",sizzlingListModel.getMainIngredients());
                intent.putExtra("groupAddOns",sizzlingListModel.getAddOns());
                intent.putExtra("groupAddOnsFee",sizzlingListModel.getAddOnsPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizzlingList.size();
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
