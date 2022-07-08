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
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class RiceAdapter extends RecyclerView.Adapter<RiceAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> riceList;
    public RiceAdapter(Context context,List<ProductListModel> riceList){
        this.context = context;
        this.riceList = riceList;
    }
    @NonNull
    @Override
    public RiceAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new RiceAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiceAdapter.ProductViewHolder holder, int position) {
        ProductListModel riceModel = riceList.get(position);
        Glide.with(context)
                .load(riceModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(riceModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(riceModel.getPrice()+".00"));
        holder.textDevTime.setText(riceModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image",riceModel.getImage());
                intent.putExtra("productName",riceModel.getProductName());
                intent.putExtra("productCategory",riceModel.getProductCategoryCombo());
                intent.putExtra("price",riceModel.getPrice());
                intent.putExtra("productVariation",riceModel.getProductVariation());
                intent.putExtra("code",riceModel.getCodeCombo());
                intent.putExtra("status", riceModel.getStocks());
                intent.putExtra("preparationTime",riceModel.getPreparationTime());
                intent.putExtra("mainIngredients",riceModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return riceList.size();
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
