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
import com.example.mangmacs.activities.DessertListDetail;
import com.example.mangmacs.activities.NoodlesActivity;
import com.example.mangmacs.activities.NoodlesListDetail;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

public class NoodlesAdapter extends RecyclerView.Adapter<NoodlesAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> noodleList;
    public NoodlesAdapter(Context context, List<ProductListModel> noodleList){
        this.context = context;
        this.noodleList = noodleList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductListModel noodlesListModel = noodleList.get(position);
        Glide.with(context)
                .load(noodlesListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(noodlesListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(noodlesListModel.getPrice()+".00"));
        holder.textDevTime.setText(noodlesListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoodlesListDetail.class);
                intent.putExtra("image",noodlesListModel.getImage());
                intent.putExtra("productName",noodlesListModel.getProductName());
                intent.putExtra("productCategory",noodlesListModel.getProductCategoryCombo());
                intent.putExtra("price",noodlesListModel.getPrice());
                intent.putExtra("productVariation",noodlesListModel.getProductVariation());
                intent.putExtra("code",noodlesListModel.getCodeCombo());
                intent.putExtra("status", noodlesListModel.getStocks());
                intent.putExtra("preparationTime",noodlesListModel.getPreparationTime());
                intent.putExtra("mainIngredients",noodlesListModel.getMainIngredients());
                intent.putExtra("groupAddOns",noodlesListModel.getAddOns());
                intent.putExtra("groupAddOnsFee",noodlesListModel.getAddOnsPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noodleList.size();
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
            textDevTime =itemView.findViewById(R.id.devTime);
        }
    }
}
