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
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.BilaoListDetail;

import java.util.List;

public class PopularAdatper  extends RecyclerView.Adapter<PopularAdatper.ProductViewHolder> {
    private Context context;
    private List<PopularListModel> popularList;
    public PopularAdatper(Context context,List<PopularListModel> popularList){
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularAdatper.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.bilao_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdatper.ProductViewHolder holder, int position) {
        PopularListModel popularListModel = popularList.get(position);
        Glide.with(context)
                .load(popularListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(popularListModel.getProductName());
        holder.textProductPrice.setText(String.valueOf(popularListModel.getPrice()));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BilaoListDetail.class);
                intent.putExtra("image",popularListModel.getImage());
                intent.putExtra("productName",popularListModel.getProductName());
                intent.putExtra("price",popularListModel.getPrice());
                intent.putExtra("status",popularListModel.getStatus());
                intent.putExtra("productVariation",popularListModel.getProductVariation());
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
