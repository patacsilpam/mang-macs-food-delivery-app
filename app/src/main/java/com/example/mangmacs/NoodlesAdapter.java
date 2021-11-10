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

public class NoodlesAdapter extends RecyclerView.Adapter<NoodlesAdapter.ProductViewHolder> {
    private Context context;
    private List<NoodlesListModel> noodleList;
    public NoodlesAdapter(Context context, List<NoodlesListModel> noodleList){
        this.context = context;
        this.noodleList = noodleList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.noodles_list,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        NoodlesListModel noodlesListModel = noodleList.get(position);
        Glide.with(context)
                .load(noodlesListModel.getImage())
                .into(holder.image);
        holder.textProductName.setText(noodlesListModel.getProductName());
        holder.textProductPrice.setText("₱ "+String.valueOf(noodlesListModel.getPrice()+".00"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,NoodlesListDetail.class);
                intent.putExtra("image",noodlesListModel.getImage());
                intent.putExtra("productName",noodlesListModel.getProductName());
                intent.putExtra("price",noodlesListModel.getPrice());
                intent.putExtra("status",noodlesListModel.getStatus());
                intent.putExtra("productVariation",noodlesListModel.getProductVariation());
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
