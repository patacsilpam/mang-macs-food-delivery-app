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
import com.example.mangmacs.activities.NoodlesListDetail;
import com.example.mangmacs.activities.PopularDetailActivity;
import com.example.mangmacs.activities.SizzlingListDetail;
import com.example.mangmacs.activities.SoupListDetail;
import com.example.mangmacs.activities.PastaListDetail;
import com.example.mangmacs.model.PopularListModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ProductViewHolder> {
    private Context context;
    private List<PopularListModel> popularList;
    public PopularAdapter(Context context,List<PopularListModel> popularList){
        this.context = context;
        this.popularList = popularList;
    }
    @NonNull
    @Override
    public PopularAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ProductViewHolder holder, int position) {
        PopularListModel popularListModel = popularList.get(position);
        Glide.with(context)
                .load(popularListModel.getImagePopular())
                .into(holder.image);
        holder.textProductName.setText(popularListModel.getProductNamePopular());
        holder.textProductPrice.setText("₱ "+String.valueOf(popularListModel.getPricePopular()).concat(".00"));
        holder.textDevTime.setText(popularListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PopularDetailActivity.class);
                intent.putExtra("image",popularListModel.getImagePopular());
                intent.putExtra("productName",popularListModel.getProductNamePopular());
                intent.putExtra("productCategory",popularListModel.getProductCategoryPopular());
                intent.putExtra("price",popularListModel.getPricePopular());
                intent.putExtra("productVariation",popularListModel.getProductVariationPopular());
                intent.putExtra("code",popularListModel.getProductCodePopular());
                intent.putExtra("status", popularListModel.getStocks());
                intent.putExtra("preparationTime",popularListModel.getPreparationTime());
                intent.putExtra("mainIngredients",popularListModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
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
