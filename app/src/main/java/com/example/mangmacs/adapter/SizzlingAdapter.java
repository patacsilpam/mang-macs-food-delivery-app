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
import com.example.mangmacs.model.SizzlingListModel;

import java.util.List;

public class SizzlingAdapter extends RecyclerView.Adapter<SizzlingAdapter.ProductViewHolder> {
    private Context context;
    private List<SizzlingListModel> seafoodsList;
    public SizzlingAdapter(Context context, List<SizzlingListModel> seafoodsList){
        this.context = context;
        this.seafoodsList = seafoodsList;
    }
    @NonNull
    @Override
    public SizzlingAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.seafoods_list,null);
        return new SizzlingAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizzlingAdapter.ProductViewHolder holder, int position) {
        SizzlingListModel sizzlingListModel = seafoodsList.get(position);
        Glide.with(context)
                .load(sizzlingListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(sizzlingListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(sizzlingListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SizzlingListDetail.class);
                intent.putExtra("image", sizzlingListModel.getImage());
                intent.putExtra("productName", sizzlingListModel.getProductName());
                intent.putExtra("price", sizzlingListModel.getPrice());
                intent.putExtra("status", sizzlingListModel.getStatus());
                intent.putExtra("productVariation", sizzlingListModel.getProductVariation());
                intent.putExtra("code", sizzlingListModel.getCodeSeafoods());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seafoodsList.size();
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
