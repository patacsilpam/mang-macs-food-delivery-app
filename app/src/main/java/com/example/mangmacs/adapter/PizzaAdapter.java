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
import com.example.mangmacs.activities.PizzaListDetaill;
import com.example.mangmacs.model.PizzaListModel;
import com.example.mangmacs.R;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ProductViewHolder> {
    private Context context;
    private List<PizzaListModel> pizzaList;
    public PizzaAdapter(Context context,List<PizzaListModel> pizzaList){
        this.context = context;
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PizzaListModel pizzaListModel = pizzaList.get(position);
        Glide.with(context)
                .load(pizzaListModel.getImage())
                .into(holder.image);
        holder.textProductPrice.setText("₱ "+String.valueOf(pizzaListModel.getPrice()+".00"));
        holder.textProductName.setText(pizzaListModel.getProductName());
        holder.textDevTime.setText(pizzaListModel.getPreparationTime().concat("min"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PizzaListDetaill.class);
                intent.putExtra("image",pizzaListModel.getImage());
                intent.putExtra("productName",pizzaListModel.getProductName());
                intent.putExtra("productCategory",pizzaListModel.getProductCategory());
                intent.putExtra("stocks",pizzaListModel.getStocks());
                intent.putExtra("groupPrice",pizzaListModel.getGroupPrice());
                intent.putExtra("productVariation",pizzaListModel.getProductVariation());
                intent.putExtra("groupCode",pizzaListModel.getCode());
                intent.putExtra("preparationTime",pizzaListModel.getPreparationTime());
                intent.putExtra("mainIngredients",pizzaListModel.getMainIngredients());
                intent.putExtra("groupAddOns",pizzaListModel.getGroupAddOns());
                intent.putExtra("groupAddOnsPrice",pizzaListModel.getGroupAddOnsPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
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
