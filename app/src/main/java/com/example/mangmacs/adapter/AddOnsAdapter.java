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

public class AddOnsAdapter extends RecyclerView.Adapter<AddOnsAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductListModel> addOnsList;
    public AddOnsAdapter(Context context, List<ProductListModel> addOnsList){
        this.context = context;
        this.addOnsList = addOnsList;
    }
    @NonNull
    @Override
    public AddOnsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddOnsAdapter.ProductViewHolder holder, int position) {
        ProductListModel addOnsModel  = addOnsList.get(position);
        Glide.with(context)
                .load(addOnsModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(addOnsModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(addOnsModel.getPrice()+".00"));
        holder.textDevTime.setText(addOnsModel.getPreparationTime().concat(" mins"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GrilledListDetail.class);
                intent.putExtra("image", addOnsModel.getImage());
                intent.putExtra("productName", addOnsModel.getProductName());
                intent.putExtra("productCategory",addOnsModel.getProductCategoryCombo());
                intent.putExtra("price", addOnsModel.getPrice());
                intent.putExtra("productVariation", addOnsModel.getProductVariation());
                intent.putExtra("code", addOnsModel.getCodeCombo());
                intent.putExtra("status", addOnsModel.getStocks());
                intent.putExtra("preparationTime",addOnsModel.getPreparationTime());
                intent.putExtra("mainIngredients",addOnsModel.getMainIngredients());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addOnsList.size();
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
