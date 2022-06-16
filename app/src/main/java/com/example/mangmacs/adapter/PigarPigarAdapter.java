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

public class PigarPigarAdapter extends RecyclerView.Adapter<PigarPigarAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> pigarPigarList;
    public PigarPigarAdapter(Context context, List<ProductListModel> pigarPigarList){
        this.context = context;
        this.pigarPigarList = pigarPigarList;
    }
    @NonNull
    @Override
    public PigarPigarAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new PigarPigarAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PigarPigarAdapter.ProductViewHolder holder, int position) {
        ProductListModel pigarPigarModel = pigarPigarList.get(position);
        Glide.with(context)
                .load(pigarPigarModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(pigarPigarModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(pigarPigarModel.getPrice()+".00"));
        holder.textDevTime.setText(pigarPigarModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image", pigarPigarModel.getImage());
                intent.putExtra("productName", pigarPigarModel.getProductName());
                intent.putExtra("productCategory",pigarPigarModel.getProductCategoryCombo());
                intent.putExtra("price", pigarPigarModel.getPrice());
                intent.putExtra("productVariation", pigarPigarModel.getProductVariation());
                intent.putExtra("code", pigarPigarModel.getCodeCombo());
                intent.putExtra("status", pigarPigarModel.getStocks());
                intent.putExtra("preparationTime",pigarPigarModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pigarPigarList.size();
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
