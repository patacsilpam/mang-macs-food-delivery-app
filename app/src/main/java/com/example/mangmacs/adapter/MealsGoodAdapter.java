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
import com.example.mangmacs.activities.MealsGoodListDetail;
import com.example.mangmacs.model.MealsGoodListModel;
import com.example.mangmacs.R;

import java.util.List;

public class MealsGoodAdapter extends RecyclerView.Adapter<MealsGoodAdapter.ProductViewHolder> {
    private Context context;
    private List<MealsGoodListModel> mealsGoodList;
    public MealsGoodAdapter(Context context,List<MealsGoodListModel> mealsGoodList){
        this.context = context;
        this.mealsGoodList = mealsGoodList;
    }
    @NonNull
    @Override
    public MealsGoodAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.meals_good_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsGoodAdapter.ProductViewHolder holder, int position) {
        MealsGoodListModel mealsGoodListModel = mealsGoodList.get(position);
        Glide.with(context)
                .load(mealsGoodListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(mealsGoodListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(mealsGoodListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image",mealsGoodListModel.getImage());
                intent.putExtra("productName",mealsGoodListModel.getProductName());
                intent.putExtra("price",mealsGoodListModel.getPrice());
                intent.putExtra("status",mealsGoodListModel.getStatus());
                intent.putExtra("productVariation",mealsGoodListModel.getProductVariation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsGoodList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textProductName,textProductPrice;
        CardView productContainer;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textProductName = itemView.findViewById(R.id.productName);
            textProductPrice = itemView.findViewById(R.id.productPrice);
            productContainer = itemView.findViewById(R.id.productContainer);
        }
    }
}
