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
import com.example.mangmacs.activities.SeafoodsListDetail;
import com.example.mangmacs.model.SeafoodsListModel;

import java.util.List;

public class SeafoodsAdapter extends RecyclerView.Adapter<SeafoodsAdapter.ProductViewHolder> {
    private Context context;
    private List<SeafoodsListModel> seafoodsList;
    public SeafoodsAdapter(Context context,List<SeafoodsListModel> seafoodsList){
        this.context = context;
        this.seafoodsList = seafoodsList;
    }
    @NonNull
    @Override
    public SeafoodsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.seafoods_list,null);
        return new SeafoodsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeafoodsAdapter.ProductViewHolder holder, int position) {
        SeafoodsListModel seafoodsListModel = seafoodsList.get(position);
        Glide.with(context)
                .load(seafoodsListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(seafoodsListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(seafoodsListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeafoodsListDetail.class);
                intent.putExtra("image",seafoodsListModel.getImage());
                intent.putExtra("productName",seafoodsListModel.getProductName());
                intent.putExtra("price",seafoodsListModel.getPrice());
                intent.putExtra("status",seafoodsListModel.getStatus());
                intent.putExtra("productVariation",seafoodsListModel.getProductVariation());
                intent.putExtra("code",seafoodsListModel.getCodeSeafoods());
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
