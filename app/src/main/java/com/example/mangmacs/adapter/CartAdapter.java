package com.example.mangmacs.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.activities.BilaoListDetail;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.UpdateAccountModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<CartModel> cartList;
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
        holder.productID.setText(String.valueOf(cartModel.getProductId()));
        holder.productName.setText(cartModel.getProoductNameCart());
        holder.quantity.setText(String.valueOf(cartModel.getQuantityCart()));
        String variation = String.valueOf(cartModel.getVariationCart());
        String[] splitVariation = variation.split(",");
        holder.productVariation.setText(splitVariation[0]);
        holder.productPrice.setText(String.valueOf(cartModel.getPriceCart()));
        final int[] count = {Integer.parseInt(String.valueOf((cartModel.getQuantityCart())))};
        holder.totalPrice.setText(String.valueOf(cartModel.getTotalprice()));
        //disabling the decrement button if the quantity is equal to one

        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number = holder.quantity.getText().toString();
                if(number.equalsIgnoreCase("1")){
                    holder.btnDecrement.setEnabled(false);
                }else{
                    holder.btnDecrement.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //increment quantity
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
        //decrement quantity
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
        String totalprice = holder.totalPrice.getText().toString();
        Intent intent = new Intent("TotalPrice");
        intent.putExtra("totalprice",totalprice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName,productVariation,productPrice,productID,totalPrice;
        EditText quantity;
        Button btnIncrement,btnDecrement,deleteCart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProduct);
            productName = itemView.findViewById(R.id.textProduct);
            productVariation = itemView.findViewById(R.id.textVariation);
            productPrice = itemView.findViewById(R.id.textPrice);
            quantity = itemView.findViewById(R.id.textQuantity);
            btnIncrement = itemView.findViewById(R.id.btnIncrement);
            btnDecrement = itemView.findViewById(R.id.btnDecrement);
            deleteCart = itemView.findViewById(R.id.deleteCart);
            productID = itemView.findViewById(R.id.productID);
            totalPrice = itemView.findViewById(R.id.totalprice);

        }
    }
}
