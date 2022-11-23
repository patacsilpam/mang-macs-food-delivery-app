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
import com.example.mangmacs.activities.WineActivity;
import com.example.mangmacs.activities.WineListDetailActivity;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class BeerBucketAdapter extends RecyclerView.Adapter<BeerBucketAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> beerBucketList;
    public BeerBucketAdapter(Context context,List<ProductListModel> beerBucketList){
        this.context = context;
        this.beerBucketList = beerBucketList;
    }
    @NonNull
    @Override
    public BeerBucketAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,parent,false);
        return new BeerBucketAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerBucketAdapter.ProductViewHolder holder, int position) {
        ProductListModel beerBucketModel = beerBucketList.get(position);
        Glide.with(context)
                .load(beerBucketModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(beerBucketModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(beerBucketModel.getPrice()+".00"));
        holder.textDevTime.setText(beerBucketModel.getPreparationTime().concat(" mins"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WineListDetailActivity.class);
                intent.putExtra("image", beerBucketModel.getImage());
                intent.putExtra("productName", beerBucketModel.getProductName());
                intent.putExtra("productCategory",beerBucketModel.getProductCategoryCombo());
                intent.putExtra("price", beerBucketModel.getPrice());
                intent.putExtra("productVariation", beerBucketModel.getProductVariation());
                intent.putExtra("code", beerBucketModel.getCodeCombo());
                intent.putExtra("status", beerBucketModel.getStocks());
                intent.putExtra("preparationTime",beerBucketModel.getPreparationTime());
                intent.putExtra("mainIngredients",beerBucketModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beerBucketList.size();
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
