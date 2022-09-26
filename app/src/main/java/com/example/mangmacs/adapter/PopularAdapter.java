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
import com.example.mangmacs.model.PizzaListModel;
import com.example.mangmacs.model.PopularListModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ProductViewHolder> {
    private Context context;
    private List<PizzaListModel> popularList;
    public PopularAdapter(Context context,List<PizzaListModel> popularList){
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
        PizzaListModel popularListModel = popularList.get(position);
        Glide.with(context)
                .load(popularListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(popularListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(popularListModel.getPrice()).concat(".00"));
        holder.textDevTime.setText(popularListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PopularDetailActivity.class);
                intent.putExtra("image",popularListModel.getImage());
                intent.putExtra("productName",popularListModel.getProductName());
                intent.putExtra("productCategory",popularListModel.getProductCategory());
                intent.putExtra("stocks",popularListModel.getStocks());
                intent.putExtra("groupPrice",popularListModel.getGroupPrice());
                intent.putExtra("productVariation",popularListModel.getProductVariation());
                intent.putExtra("groupCode",popularListModel.getCode());
                intent.putExtra("preparationTime",popularListModel.getPreparationTime());
                intent.putExtra("mainIngredients",popularListModel.getMainIngredients());
                intent.putExtra("groupAddOns",popularListModel.getGroupAddOns());
                intent.putExtra("groupAddOnsPrice",popularListModel.getGroupAddOnsPrice());
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
