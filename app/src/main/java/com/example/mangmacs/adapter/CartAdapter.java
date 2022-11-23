package com.example.mangmacs.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.TotalPrice;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.CartInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<CartModel> cartList;
    private CartInterface cartInterface;
    private String totalPriceList = "0";
    private ArrayList<String> productList = new ArrayList<>();
    private ArrayList<Integer> preparedList = new ArrayList<Integer>();
    HashMap<String,String> map = new HashMap<>();
    public CartAdapter(Context context, List<CartModel> cartList, CartInterface cartInterface){
        this.context = context;
        this.cartList = cartList;
        this.cartInterface = cartInterface;
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
        int totalAmount = (cartModel.getPriceCart() + cartModel.getAddOnsFee()) *  cartModel.getQuantityCart();
        //display user cart lists detail
        Glide.with(context).load(cartModel.getImageProduct()).into(holder.imageView);
        holder.productID.setText(String.valueOf(cartModel.getProductId()));
        holder.productName.setText(cartModel.getProoductNameCart());
        holder.quantity.setText(String.valueOf(cartModel.getQuantityCart()));
        holder.productAddOns.setText(cartModel.getAddOns());
        holder.productSpecialRequest.setText("\"" +cartModel.getSpecialRequest().toLowerCase() + "\"");
        holder.productVariation.setText(cartModel.getVariationCart());
        holder.productPrice.setText(String.valueOf(cartModel.getPriceCart() + cartModel.getAddOnsFee()));
        holder.totalPrice.setText(String.valueOf(totalAmount));
        holder.btnDecrement.setEnabled(false);
        final int[] count = {Integer.parseInt(String.valueOf((holder.quantity.getText().toString())))};
        String totalprice = holder.totalPrice.getText().toString();
        final String[] quantityCart = {holder.quantity.getText().toString()};
        //increase  order product activity
        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                holder.quantity.setText(String.valueOf(count[0]));
                int id = Integer.parseInt(holder.productID.getText().toString());
                int orderQuantity = Integer.parseInt(holder.quantity.getText().toString());
                int prodPrice = Integer.parseInt(holder.productPrice.getText().toString());
                int totalPrice = prodPrice  *  orderQuantity;
                holder.totalPrice.setText(String.valueOf(totalPrice));

                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> updateOrderQuantity = apiInterface.updateQuantity(id,orderQuantity);
                updateOrderQuantity.enqueue(new Callback<CartModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success = response.body().getSuccess();
                            if(success.equals("1")){
                                //productList.set(Integer.parseInt("1"),String.valueOf(totalprice));
                                //Toast.makeText(context, String.valueOf(productList), Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(context,CartActivity.class);
                                //context.startActivity(intent);
                                String total = holder.totalPrice.getText().toString();
                                map.put(String.valueOf(id),total);
                                cartInterface.onTotalPriceChange(String.valueOf(map.values()));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });

        //disable minus button  if equals to 1 quantity
        if (!quantityCart.equals("1")){
            holder.btnDecrement.setEnabled(true);
            holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(count[0] == 1)){
                        count[0]--;
                        holder.quantity.setText(String.valueOf(count[0]));
                   } else {
                       holder.quantity.setText("1");
                   }
                    int id = Integer.parseInt(holder.productID.getText().toString());
                    int orderQuantity = Integer.parseInt(holder.quantity.getText().toString());
                    int prodPrice = Integer.parseInt(holder.productPrice.getText().toString());
                    int totalPrice = prodPrice *  orderQuantity;
                    holder.totalPrice.setText(String.valueOf(totalPrice));

                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> updateOrderQuantity = apiInterface.updateQuantity(id,orderQuantity);
                    updateOrderQuantity.enqueue(new Callback<CartModel>() {
                        @Override
                        public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                            if(response.body() != null){
                                String success = response.body().getSuccess();
                                if(success.equals("1")){
                                    String total = holder.totalPrice.getText().toString();
                                    map.put(String.valueOf(id), total);
                                    cartInterface.onTotalPriceChange(String.valueOf(map.values()));
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
        else{
            holder.btnDecrement.setEnabled(false);
        }



        //delete cart item button functionality
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
                                                String total = holder.totalPrice.getText().toString();
                                                map.put(String.valueOf(id),total);
                                                cartInterface.onTotalPriceChange(String.valueOf(map.values()));
                                                Intent intent = new Intent(context,CartActivity.class);
                                                context.startActivity(intent);
                                                /*((CartActivity)context).overridePendingTransition(R.anim.nav_default_enter_anim,R.anim.nav_default_enter_anim);*/

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
        String strTotalPrice = holder.totalPrice.getText().toString();
        String id = holder.productID.getText().toString();
        preparedList.add(Integer.valueOf(cartModel.getPreparedTime()));
        int maxTime = Collections.max(preparedList);
        //productList.add(strTotalPrice);
        map.put(id,strTotalPrice);
        cartInterface.onTotalPriceChange(String.valueOf(cartModel.getTotalprice()));

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
