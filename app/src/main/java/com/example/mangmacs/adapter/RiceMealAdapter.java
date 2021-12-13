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
import com.example.mangmacs.activities.RiceMealListDetail;
import com.example.mangmacs.model.RiceListModel;

import java.util.List;

public class RiceMealAdapter extends RecyclerView.Adapter<RiceMealAdapter.ProductViewHolder>{
    private Context context;
    private List<RiceListModel> riceMealList;
    public RiceMealAdapter(Context context,List<RiceListModel> riceMealList){
        this.context = context;
        this.riceMealList = riceMealList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rice_meal_list,null);
        return new RiceMealAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        RiceListModel riceListModel = riceMealList.get(position);
        Glide.with(context)
                .load(riceListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(riceListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(riceListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RiceMealListDetail.class);
                intent.putExtra("image",riceListModel.getImage());
                intent.putExtra("productName",riceListModel.getProductName());
                intent.putExtra("price",riceListModel.getPrice());
                intent.putExtra("status",riceListModel.getStatus());
                intent.putExtra("productVariation",riceListModel.getProductVariation());
                intent.putExtra("code",riceListModel.getCodeRiceMeal());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return riceMealList.size();
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
