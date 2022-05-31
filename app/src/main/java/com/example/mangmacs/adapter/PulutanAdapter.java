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
import com.example.mangmacs.activities.PulutanListDetail;
import com.example.mangmacs.model.RicecupListModel;

import java.util.List;

public class PulutanAdapter extends RecyclerView.Adapter<PulutanAdapter.ProductViewHolder> {
    private Context context;
    private List<RicecupListModel> ricecupList;
    public PulutanAdapter(Context context, List<RicecupListModel> ricecupList){
        this.context = context;
        this.ricecupList = ricecupList;
    }
    @NonNull
    @Override
    public PulutanAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rice_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PulutanAdapter.ProductViewHolder holder, int position) {
        RicecupListModel ricecupListModel = ricecupList.get(position);
        Glide.with(context)
                .load(ricecupListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(ricecupListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(ricecupListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PulutanListDetail.class);
                intent.putExtra("image",ricecupListModel.getImage());
                intent.putExtra("productName",ricecupListModel.getProductName());
                intent.putExtra("price",ricecupListModel.getPrice());
                intent.putExtra("status",ricecupListModel.getStatus());
                intent.putExtra("productVariation",ricecupListModel.getProductVariation());
                intent.putExtra("code",ricecupListModel.getCodeRice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ricecupList.size();
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
