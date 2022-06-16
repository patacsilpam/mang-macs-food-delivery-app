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
import com.example.mangmacs.activities.AppetizerListDetail;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class AppetizerAdapter extends RecyclerView.Adapter<AppetizerAdapter.ProductViewHolder>{
    private Context context;
    private List<ProductListModel> appetizerList;
    public AppetizerAdapter(Context context, List<ProductListModel> appetizerList){
        this.context = context;
        this.appetizerList = appetizerList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new AppetizerAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductListModel appetizerModel = appetizerList.get(position);
        Glide.with(context)
                .load(appetizerModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(appetizerModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(appetizerModel.getPrice()+".00"));
        holder.textDevTime.setText(appetizerModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppetizerListDetail.class);
                intent.putExtra("image", appetizerModel.getImage());
                intent.putExtra("productName", appetizerModel.getProductName());
                intent.putExtra("productCategory",appetizerModel.getProductCategoryCombo());
                intent.putExtra("price", appetizerModel.getPrice());
                intent.putExtra("productVariation", appetizerModel.getProductVariation());
                intent.putExtra("code", appetizerModel.getCodeCombo());
                intent.putExtra("status", appetizerModel.getStocks());
                intent.putExtra("preparationTime",appetizerModel.getPreparationTime());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return appetizerList.size();
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
