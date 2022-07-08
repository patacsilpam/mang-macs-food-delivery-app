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

public class BarbequeAdapter extends RecyclerView.Adapter<BarbequeAdapter.ViewHolder> {
    private Context context;
    private List<ProductListModel> grilledList;
    public BarbequeAdapter(Context context,List<ProductListModel> grilledList){
        this.context = context;
        this.grilledList = grilledList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductListModel grilledModel = grilledList.get(position);
        Glide.with(context)
                .load(grilledModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(grilledModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(grilledModel.getPrice()+".00"));
        holder.textDevTime.setText(grilledModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GrilledListDetail.class);
                intent.putExtra("image", grilledModel.getImage());
                intent.putExtra("productName", grilledModel.getProductName());
                intent.putExtra("productCategory",grilledModel.getProductCategoryCombo());
                intent.putExtra("price", grilledModel.getPrice());
                intent.putExtra("productVariation", grilledModel.getProductVariation());
                intent.putExtra("code", grilledModel.getCodeCombo());
                intent.putExtra("status", grilledModel.getStocks());
                intent.putExtra("preparationTime",grilledModel.getPreparationTime());
                intent.putExtra("mainIngredients",grilledModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return grilledList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textProductName,textProductPrice,textDevTime;
        CardView productContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textProductName = itemView.findViewById(R.id.productName);
            textProductPrice = itemView.findViewById(R.id.productPrice);
            productContainer = itemView.findViewById(R.id.productContainer);
            textDevTime = itemView.findViewById(R.id.devTime);
        }
    }
}
