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
import com.example.mangmacs.model.GrilledListModel;

import java.util.List;

public class GrilledAdapter extends RecyclerView.Adapter<GrilledAdapter.ProductViewHolder> {
    private Context context;
    private List<GrilledListModel>  comboMealList;
    public GrilledAdapter(Context context, List<GrilledListModel> comboMealList){
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
        GrilledListModel grilledListModel = comboMealList.get(position);
        Glide.with(context)
                .load(grilledListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(grilledListModel.getProductName());
        holder.textProductPrice.setText(String.valueOf("₱ "+ grilledListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GrilledListDetail.class);
                intent.putExtra("image", grilledListModel.getImage());
                intent.putExtra("productName", grilledListModel.getProductName());
                intent.putExtra("price", grilledListModel.getPrice());
                intent.putExtra("status", grilledListModel.getStatus());
                intent.putExtra("productVariation", grilledListModel.getProductVariation());
                intent.putExtra("code", grilledListModel.getCodeCombo());
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
