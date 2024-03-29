package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CustomerLoginModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class OrderListsAdapter extends RecyclerView.Adapter<OrderListsAdapter.ProductViewHolder> {
    private Context context;
    private List<CartModel> orderList;
    OrdersListener ordersListener;
    ArrayList<String> productList = new ArrayList<>();
    ArrayList<String> productCodeList = new ArrayList<>();
    ArrayList<String> variationList = new ArrayList<>();
    ArrayList<Integer> quantityList = new ArrayList<>();
    ArrayList<String> addOnsList = new ArrayList<>();
    ArrayList<Integer> addOnsFeeList = new ArrayList<>();
    ArrayList<String> specialReqList = new ArrayList<>();
    ArrayList<String> priceList = new ArrayList<>();
    ArrayList<String> subTotalList = new ArrayList<>();
    ArrayList<String> imgProductList = new ArrayList<>();
    ArrayList<String> productCategoryList = new ArrayList<>();
    ArrayList<String> preparationTimeList = new ArrayList<>();
    public OrderListsAdapter(Context context, List<CartModel> orderList,OrdersListener ordersListener){
        this.context = context;
        this.orderList = orderList;
        this.ordersListener = ordersListener;
    }

    @NonNull
    @Override
    public OrderListsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order_lists,null);
        return new OrderListsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        CartModel orderModel = orderList.get(position);
        Glide.with(context).load(orderModel.getImageProduct()).into(holder.imgProduct);
        String variation = orderModel.getVariationCart();
        String[] splitVariation =  variation.split(",");
        holder.product.setText(orderModel.getProoductNameCart());
        holder.addOns.setText(orderModel.getAddOns());
        holder.variation.setText(splitVariation[0]);
        holder.items.setText(String.valueOf(orderModel.getQuantityCart()));
        holder.price.setText(String.valueOf(orderModel.getPriceCart()  + orderModel.getAddOnsFee()));
        holder.specialRequest.setText("\"" + orderModel.getSpecialRequest() + "\"");
        //
        String str_product = orderModel.getProoductNameCart();
        String str_productCode = orderModel.getProductCodeCart();
        String str_newVariation = orderModel.getVariationCart();
        int str_newQuantity = orderModel.getQuantityCart();
        String str_newAddons = orderModel.getAddOns();
        int addOnsFee = orderModel.getAddOnsFee();
        String str_newSpecialReq = orderModel.getSpecialRequest();
        String str_newPrice = String.valueOf(orderModel.getPriceCart());
        String str_newImgProduct = orderModel.getImageProduct();
        String str_subTotal = String.valueOf(orderModel.getQuantityCart() * orderModel.getPriceCart());
        String str_productCategory = orderModel.getProductCategory();
        String str_preparationTime = orderModel.getPreparedTime();

        productList.add(str_product);
        productCodeList.add(str_productCode);
        variationList.add(str_newVariation);
        quantityList.add(str_newQuantity);
        addOnsList.add(str_newAddons);
        addOnsFeeList.add(addOnsFee);
        specialReqList.add(str_newSpecialReq);
        subTotalList.add(str_subTotal);
        priceList.add(str_newPrice);
        imgProductList.add(str_newImgProduct);
        productCategoryList.add(str_productCategory);
        preparationTimeList.add(str_preparationTime);
        Intent intent = new Intent("TotalOrderPrice");
        intent.putExtra("totalorderprice",orderModel.getTotalprice());
        intent.putExtra("deliveryChange",orderModel.getDeliveryChange());
        intent.putExtra("waitingTime",orderModel.getPreparedTime());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        ordersListener.onProductsChange(productList);
        ordersListener.onProductCategoryChange(productCategoryList);
        ordersListener.onProductCodeChange(productCodeList);
        ordersListener.onVariationChange(variationList);
        ordersListener.onQuantityChange(quantityList);
        ordersListener.onAddOnsChange(addOnsList);
        ordersListener.onAddOnsFeeChange(addOnsFeeList);
        ordersListener.onSpecialRequest(specialReqList);
        ordersListener.onSubTotalChange(subTotalList);
        ordersListener.onPriceChange(priceList);
        ordersListener.onImgProductChange(imgProductList);
        ordersListener.onPreparationTimeChange(preparationTimeList);

        //fix the design
        if (orderModel.getAddOns().equals("")){
            holder.addOnsLayout.setVisibility(View.GONE);
        }
        if (orderModel.getVariationCart().equals("")){
            holder.variation.setVisibility(View.GONE);
        }
        if (orderModel.getSpecialRequest().equals("")){
            holder.specialRequest.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout addOnsLayout;
        private TextView product,addOns,variation,items,price,specialRequest;
        private ImageView imgProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            addOnsLayout = itemView.findViewById(R.id.addOnsLayout);
            product = itemView.findViewById(R.id.product);
            addOns = itemView.findViewById(R.id.addOns);
            variation = itemView.findViewById(R.id.variation);
            items = itemView.findViewById(R.id.items);
            price = itemView.findViewById(R.id.total);
            imgProduct = itemView.findViewById(R.id.image);
            specialRequest = itemView.findViewById(R.id.specialRequest);
        }
    }
}
