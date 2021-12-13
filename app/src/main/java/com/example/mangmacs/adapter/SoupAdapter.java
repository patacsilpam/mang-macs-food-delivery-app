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
import com.example.mangmacs.activities.SoupListDetail;
import com.example.mangmacs.model.SoupListModel;

import java.util.List;

public class SoupAdapter extends RecyclerView.Adapter<SoupAdapter.ProductViewHolder> {
    private Context context;
    private List<SoupListModel> soupList;
    public SoupAdapter(Context context,List<SoupListModel> soupList){
        this.context = context;
        this.soupList = soupList;
    }

    @NonNull
    @Override
    public SoupAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.soup_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoupAdapter.ProductViewHolder holder, int position) {
        SoupListModel soupListModel = soupList.get(position);
        Glide.with(context)
                .load(soupListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(soupListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(soupListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SoupListDetail.class);
                intent.putExtra("image",soupListModel.getImage());
                intent.putExtra("productName",soupListModel.getProductName());
                intent.putExtra("price",soupListModel.getPrice());
                intent.putExtra("status",soupListModel.getStatus());
                intent.putExtra("productVariation",soupListModel.getProductVariation());
                intent.putExtra("code",soupListModel.getCodeSoup());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return soupList.size();
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
