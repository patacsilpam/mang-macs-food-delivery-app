package com.example.mangmacs.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.CurrentOrderDetailsActivity;
import com.example.mangmacs.activities.MyOrdersActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOrdersDetailAdapter extends RecyclerView.Adapter<NewOrdersDetailAdapter.ViewHolder> {
    private Context context;
    private List<CurrentOrdersModel> currentOrdersModelList;
    private OrdersListener ordersListener;
    private ArrayList<String> productCodeList = new ArrayList<>();
    private ArrayList<String> productList = new ArrayList<>();
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<String> variationList = new ArrayList<>();
    public NewOrdersDetailAdapter(Context context, List<CurrentOrdersModel> currentOrdersModelList,OrdersListener ordersListener){
        this.context = context;
        this.currentOrdersModelList = currentOrdersModelList;
        this.ordersListener = ordersListener;
    }
    @NonNull
    @Override
    public NewOrdersDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order_details,parent,false);
        return new NewOrdersDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewOrdersDetailAdapter.ViewHolder holder, int position) {
        CurrentOrdersModel currentOrdersModels = currentOrdersModelList.get(position);
        int productPrice = Integer.parseInt(currentOrdersModels.getPrice());
        int addOnsPrice = Integer.parseInt(currentOrdersModels.getAddOnsFee());
        Glide.with(context).load(currentOrdersModels.getImgProduct()).into(holder.imgProduct);
        holder.textAddOns.setText(currentOrdersModels.getAddOns());
        holder.textProduct.setText(currentOrdersModels.getProducts());
        //holder.textProductPrice.setText(String.valueOf("( " + currentOrdersModels.getPrice() + ".00 )"));
        //holder.textAddOnsPrice.setText(String.valueOf("( " + currentOrdersModels.getAddOnsFee() +".00 )"));
        holder.textAddOns.setText(currentOrdersModels.getAddOns());
        holder.textVariation.setText(currentOrdersModels.getVariations());
        holder.items.setText(currentOrdersModels.getQuantities());
        holder.textPrice.setText(String.valueOf(productPrice + addOnsPrice));
        holder.textSpecialRequest.setText("\"" + currentOrdersModels.getSpecialRequest() + "\"");
        String totalAmount = currentOrdersModels.getTotalAmount();
        String orderNumber = currentOrdersModels.getOrderNumber();
        String products  = currentOrdersModels.getProducts();
        int quantity = Integer.parseInt(currentOrdersModels.getQuantities());
        String category = currentOrdersModels.getCategory();
        String variations = currentOrdersModels.getVariations();

        //Toast.makeText(context, currentOrdersModels.getQuantities(), Toast.LENGTH_SHORT).show();
        productCodeList.add(orderNumber);
        productList.add(products);
        quantityList.add(quantity);
        categoryList.add(category);
        variationList.add(variations);
        ordersListener.onProductCodeChange(productCodeList);
        ordersListener.onProductsChange(productList);
        ordersListener.onQuantityChange(quantityList);
        ordersListener.onProductCategoryChange(categoryList);
        ordersListener.onVariationChange(variationList);
        ordersListener.onTotalAmountChange(totalAmount);
        //fix the design
        if (currentOrdersModels.getAddOns().equals("")){
            holder.textAddOns.setVisibility(View.GONE);
            holder.textAddOnsPrice.setVisibility(View.GONE);
        }
        if (currentOrdersModels.getVariations().equals("")){
            holder.textVariation.setVisibility(View.GONE);
        }
        if (currentOrdersModels.getSpecialRequest().equals("")){
            holder.textSpecialRequest.setVisibility(View.GONE);
        }
    }
    public int getItemCount() {
        return currentOrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView textProduct,textProductPrice,textAddOns,textAddOnsPrice,textSpecialRequest,textVariation,items,textPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textProduct = itemView.findViewById(R.id.textProduct);
            textProductPrice = itemView.findViewById(R.id.textProductPrice);
            textAddOns = itemView.findViewById(R.id.textAddOns);
            textAddOnsPrice = itemView.findViewById(R.id.textAddOnsPrice);
            textSpecialRequest = itemView.findViewById(R.id.textSpecialRequest);
            textVariation = itemView.findViewById(R.id.textVariation);
            textPrice = itemView.findViewById(R.id.textPrice);
            items = itemView.findViewById(R.id.items);
        }
    }
}
