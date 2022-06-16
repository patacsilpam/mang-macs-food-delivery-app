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
import com.example.mangmacs.R;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class PorkAdapter extends RecyclerView.Adapter<PorkAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> porkList;
    public PorkAdapter(Context context, List<ProductListModel> porkList){
        this.context = context;
        this.porkList = porkList;
    }
    @NonNull
    @Override
    public PorkAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PorkAdapter.ProductViewHolder holder, int position) {
        ProductListModel porkModel = porkList.get(position);
        Glide.with(context)
                .load(porkModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(porkModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(porkModel.getPrice()+".00"));
        holder.textDevTime.setText(porkModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsGoodListDetail.class);
                intent.putExtra("image",porkModel.getImage());
                intent.putExtra("productName",porkModel.getProductName());
                intent.putExtra("productCategory",porkModel.getProductCategoryCombo());
                intent.putExtra("price",porkModel.getPrice());
                intent.putExtra("productVariation",porkModel.getProductVariation());
                intent.putExtra("code",porkModel.getCodeCombo());
                intent.putExtra("status", porkModel.getStocks());
                intent.putExtra("preparationTime",porkModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return porkList.size();
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
