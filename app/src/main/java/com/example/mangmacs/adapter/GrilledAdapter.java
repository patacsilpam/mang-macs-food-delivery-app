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
import com.example.mangmacs.activities.GrilledListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class GrilledAdapter extends RecyclerView.Adapter<GrilledAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel>  comboMealList;
    public GrilledAdapter(Context context, List<ProductListModel> comboMealList){
        this.context = context;
        this.comboMealList = comboMealList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductListModel productListModel = comboMealList.get(position);
        Glide.with(context)
                .load(productListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(productListModel.getProductName());
        holder.textProductPrice.setText(String.valueOf("₱ "+ productListModel.getPrice()+".00"));
        holder.textDevTime.setText(productListModel.getPreparationTime().concat(" mins"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GrilledListDetail.class);
                intent.putExtra("image", productListModel.getImage());
                intent.putExtra("productName", productListModel.getProductName());
                intent.putExtra("productCategory", productListModel.getProductCategoryCombo());
                intent.putExtra("price", productListModel.getPrice());
                intent.putExtra("productVariation", productListModel.getProductVariation());
                intent.putExtra("code", productListModel.getCodeCombo());
                intent.putExtra("status", productListModel.getStocks());
                intent.putExtra("preparationTime",productListModel.getPreparationTime());
                intent.putExtra("mainIngredients",productListModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comboMealList.size();
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
