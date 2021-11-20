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
import com.example.mangmacs.model.PastaListModel;
import com.example.mangmacs.R;

import java.util.List;

public class PastaAdapter extends RecyclerView.Adapter<PastaAdapter.ProductViewHolder> {
    private Context context;
    private List<PastaListModel> pastaLists;
    public PastaAdapter(Context context, List<PastaListModel> pastaList){
        this.context = context;
        this.pastaLists = pastaList;
    }
    @NonNull
    @Override
    public PastaAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pasta_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastaAdapter.ProductViewHolder holder, int position) {
        PastaListModel pastaListModel = pastaLists.get(position);
        Glide.with(context)
                .load(pastaListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(pastaListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(pastaListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PastaListDetail.class);
                intent.putExtra("image",pastaListModel.getImage());
                intent.putExtra("productName",pastaListModel.getProductName());
                intent.putExtra("price",pastaListModel.getPrice());
                intent.putExtra("status",pastaListModel.getStatus());
                intent.putExtra("productVariation",pastaListModel.getProductVariation());
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
