package com.example.mangmacs;

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

import java.util.List;

public class ComboMealAdapter extends RecyclerView.Adapter<ComboMealAdapter.ProductViewHolder> {
    private Context context;
    private List<ComboMealListModel>  comboMealList;
    public ComboMealAdapter(Context context, List<ComboMealListModel> comboMealList){
        this.context = context;
        this.comboMealList = comboMealList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.combo_meal_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ComboMealListModel comboMealListModel = comboMealList.get(position);
        Glide.with(context)
                .load(comboMealListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(comboMealListModel.getProductName());
        holder.textProductPrice.setText(String.valueOf("₱ "+comboMealListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ComboBudgetListDetail.class);
                intent.putExtra("image",comboMealListModel.getImage());
                intent.putExtra("productName",comboMealListModel.getProductName());
                intent.putExtra("price",comboMealListModel.getPrice());
                intent.putExtra("status",comboMealListModel.getStatus());
                intent.putExtra("productVariation",comboMealListModel.getProductVariation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comboMealList.size();
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
