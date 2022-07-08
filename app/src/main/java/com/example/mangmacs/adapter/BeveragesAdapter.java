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
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class BeveragesAdapter extends RecyclerView.Adapter<BeveragesAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> beveragesList;
    public BeveragesAdapter(Context context,List<ProductListModel> beveragesList){
        this.context = context;
        this.beveragesList = beveragesList;
    }
    @NonNull
    @Override
    public BeveragesAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,parent,false);
        return new BeveragesAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeveragesAdapter.ProductViewHolder holder, int position) {
        ProductListModel beveragesModel = beveragesList.get(position);
        Glide.with(context)
                .load(beveragesModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(beveragesModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(beveragesModel.getPrice()+".00"));
        holder.textDevTime.setText(beveragesModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GrilledListDetail.class);
                intent.putExtra("image", beveragesModel.getImage());
                intent.putExtra("productName", beveragesModel.getProductName());
                intent.putExtra("productCategory",beveragesModel.getProductCategoryCombo());
                intent.putExtra("price", beveragesModel.getPrice());
                intent.putExtra("productVariation", beveragesModel.getProductVariation());
                intent.putExtra("code", beveragesModel.getCodeCombo());
                intent.putExtra("status", beveragesModel.getStocks());
                intent.putExtra("preparationTime",beveragesModel.getPreparationTime());
                intent.putExtra("mainIngredients",beveragesModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beveragesList.size();
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
