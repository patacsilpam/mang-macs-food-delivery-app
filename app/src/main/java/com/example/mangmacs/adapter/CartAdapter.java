package com.example.mangmacs.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<CartModel> cartList;
    private ArrayList<Integer> preparedList = new ArrayList<Integer>();
    public CartAdapter(Context context, List<CartModel> cartList){
        this.context = context;
        this.cartList = cartList;
    }
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cart_list,null);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        CartModel cartModel = cartList.get(position);
        //display user cart lists detail
        Glide.with(context).load(cartModel.getImageProduct()).into(holder.imageView);
        holder.productID.setText(String.valueOf(cartModel.getProductId()));
        holder.productName.setText(cartModel.getProoductNameCart());
        holder.quantity.setText(String.valueOf(cartModel.getQuantityCart()));
        holder.productAddOns.setText(cartModel.getAddOns());
        holder.productSpecialRequest.setText("\"" +cartModel.getSpecialRequest() + "\"");
        String variation = String.valueOf(cartModel.getVariationCart());
        holder.productVariation.setText(cartModel.getVariationCart());
        holder.productPrice.setText(String.valueOf(cartModel.getPriceCart()));
        final int[] count = {Integer.parseInt(String.valueOf((cartModel.getQuantityCart())))};
        holder.totalPrice.setText(String.valueOf(cartModel.getTotalprice()));
        holder.btnDecrement.setEnabled(true);
        String quantityCart = holder.quantity.getText().toString();

        //increase  order product activity
        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                holder.quantity.setText(String.valueOf(count[0]));
                int id = Integer.parseInt(holder.productID.getText().toString());
                int orderQuantity = Integer.parseInt(holder.quantity.getText().toString());
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> updateOrderQuantity = apiInterface.updateQuantity(id,orderQuantity);
                updateOrderQuantity.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success = response.body().getSuccess();
                            if(success.equals("1")){
                                Intent intent = new Intent(context, CartActivity.class);
                                context.startActivity(intent);
                                ((CartActivity)context).finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });

        //disable decrement button  if equals to 1 quantity
       if (quantityCart.equals("1")){
           holder.btnDecrement.setEnabled(false);
           holder.btnDecrement.setBackground(ContextCompat.getDrawable(context, R.drawable.minus_btn));
       }
       //decrease  order product activity
       else{
           holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   count[0]--;
                   holder.quantity.setText(String.valueOf(count[0]));
                   int id = Integer.parseInt(holder.productID.getText().toString());
                   int orderQuantity = Integer.parseInt(holder.quantity.getText().toString());
                   ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                   Call<CartModel> updateOrderQuantity = apiInterface.updateQuantity(id,orderQuantity);
                   updateOrderQuantity.enqueue(new Callback<CartModel>() {
                       @Override
                       public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                           if(response.body() != null){
                               String success = response.body().getSuccess();
                               if(success.equals("1")){
                                   Intent intent = new Intent(context, CartActivity.class);
                                   context.startActivity(intent);
                                   ((CartActivity)context).finish();
                               }
                           }
                       }

                       @Override
                       public void onFailure(Call<CartModel> call, Throwable t) {

                       }
                   });
               }
           });
       }

        //delete cart item
        holder.deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setCancelable(false)
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int id = Integer.parseInt(String.valueOf(cartModel.getProductId()));
                                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                Call<CartModel> deleteCartItem = apiInterface.deleteCart(id);
                                deleteCartItem.enqueue(new Callback<CartModel>() {
                                    @Override
                                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                                        if(response.body() != null){
                                            String success = response.body().getSuccess();
                                            if(success.equals("1")){
                                                cartList.remove(holder.getBindingAdapterPosition());
                                                notifyDataSetChanged();
                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((CartActivity)context).finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CartModel> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });

       //fix the design
        if (cartModel.getAddOns().equals("")){
            holder.productAddOns.setVisibility(View.GONE);
        }
        if (cartModel.getVariationCart().equals("")){
            holder.productVariation.setVisibility(View.GONE);
        }
        if (cartModel.getSpecialRequest().equals("")){
            holder.productSpecialRequest.setVisibility(View.GONE);
        }
       //send total price to cart activity using localbroadcast manager
        preparedList.add(Integer.valueOf(cartModel.getPreparedTime()));
        int maxTime = Collections.max(preparedList);
        String totalprice = holder.totalPrice.getText().toString();
        Intent intent = new Intent("TotalPrice");
        intent.putExtra("totalprice",totalprice);
        intent.putExtra("maxTime",maxTime);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName,productVariation,productAddOns,productPrice,productSpecialRequest,productID,totalPrice;
        EditText quantity;
        Button btnIncrement,btnDecrement,deleteCart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProduct);
            productName = itemView.findViewById(R.id.textProduct);
            productVariation = itemView.findViewById(R.id.textVariation);
            productAddOns = itemView.findViewById(R.id.textAddOns);
            productPrice = itemView.findViewById(R.id.textPrice);
            productSpecialRequest = itemView.findViewById(R.id.specialRequest);
            quantity = itemView.findViewById(R.id.textQuantity);
            btnIncrement = itemView.findViewById(R.id.btnIncrement);
            btnDecrement = itemView.findViewById(R.id.btnDecrement);
            deleteCart = itemView.findViewById(R.id.deleteCart);
            productID = itemView.findViewById(R.id.productID);
            totalPrice = itemView.findViewById(R.id.totalprice);

        }
    }
}
