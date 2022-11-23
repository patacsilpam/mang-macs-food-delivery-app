package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
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
import com.example.mangmacs.activities.DimsumListDetail;
import com.example.mangmacs.activities.PromoListDetails;
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.model.PromoListModel;
import java.util.List;


public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {
    private List<PromoListModel> promoLists;
    private Context context;
    public PromoAdapter(Context context, List<PromoListModel> promoLists){
        this.context = context;
        this.promoLists = promoLists;
    }
    @NonNull
    @Override
    public PromoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_lists,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.ViewHolder holder, int position) {
        PromoListModel promoListModel = promoLists.get(position);
        Glide.with(context)
                .load(promoListModel.getProductImgPromo())
                .into(holder.image);
         holder.textProductName.setText(promoListModel.getProductNamePromo());
         holder.textProductPrice.setText("₱ "+String.valueOf(promoListModel.getPricePromo()+".00"));
         holder.textDevTime.setText(String.valueOf(promoListModel.getPreparationTime()).concat(" mins"));
        holder.productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PromoListDetails.class);
                intent.putExtra("image",promoListModel.getProductImgPromo());
                intent.putExtra("productName",promoListModel.getProductNamePromo());
                intent.putExtra("productCategory",promoListModel.getProductCategoryPromo());
                intent.putExtra("price",promoListModel.getPricePromo());
                intent.putExtra("productVariation",promoListModel.getProductVariationPromo());
                intent.putExtra("code",promoListModel.getProductPromoCode());
                intent.putExtra("variation",promoListModel.getProductVariationPromo());
                intent.putExtra("status", promoListModel.getStocks());
                intent.putExtra("preparationTime",promoListModel.getPreparationTime());
                intent.putExtra("mainIngredients",promoListModel.getMainIngredients());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return promoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textProductName,textProductPrice,textVariation,textDevTime;
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
