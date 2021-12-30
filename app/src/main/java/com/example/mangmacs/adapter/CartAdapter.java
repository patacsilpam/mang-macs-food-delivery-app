package com.example.mangmacs.adapter;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.model.CartModel;

import java.util.ArrayList;
import java.util.List;

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
        holder.productName.setText(cartModel.getProoductNameCart());
        holder.quantity.setText(String.valueOf(cartModel.getQuantityCart()));
        String variation = String.valueOf(cartModel.getVariationCart());
        String[] splitVariation = variation.split(",");
        holder.productVariation.setText(splitVariation[0]);
        holder.productPrice.setText(String.valueOf(cartModel.getPriceCart()));
        final int[] count = {Integer.parseInt(String.valueOf((cartModel.getQuantityCart())))};
        //increment quantity
        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                holder.quantity.setText(String.valueOf(count[0]));
            }
        });
        //decrement quantity
        holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]--;
                holder.quantity.setText(String.valueOf(count[0]));
            }
        });
        //disabling the decrement button if the quantity is equal to one
       holder.quantity.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               String quantity = holder.quantity.getText().toString();
               if(quantity.equals("1")){
                   holder.btnDecrement.setEnabled(false);
               }
               else{
                   holder.btnDecrement.setEnabled(true);
               }
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });
        holder.checkBoxCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               int price = 0;
                List<Integer> totalList = new ArrayList<Integer>();
                totalList.add(1);
                totalList.add(2);
                totalList.add(price);
                int sum = 0;
                final int[] quantity = {0};
                if(isChecked){
                   price= Integer.parseInt(String.valueOf(cartModel.getPriceCart()));
                    quantity[0] = Integer.parseInt(String.valueOf(cartModel.getPriceCart()));
                  //totalList.add(price);
                    holder.amount.setText(String.valueOf(totalList));
                    SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(context),Context.MODE_PRIVATE);
                }
                else{
                   totalList.remove(String.valueOf(totalList));
                  // holder.amount.setText(totalList.remove(totalList));

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxCart;
        ImageView imageView;
        TextView productName,productVariation,productPrice,amount;
        EditText quantity;
        Button btnIncrement,btnDecrement,addtocart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.total);
            checkBoxCart = itemView.findViewById(R.id.checkboxCart);
            imageView = itemView.findViewById(R.id.imageView);
            productName = itemView.findViewById(R.id.txtProductName);
            productVariation = itemView.findViewById(R.id.txtProductVariation);
            productPrice = itemView.findViewById(R.id.txtProductPrice);
            quantity = itemView.findViewById(R.id.textQuantity);
            btnIncrement = itemView.findViewById(R.id.btnIncrement);
            btnDecrement = itemView.findViewById(R.id.btnDecrement);
            addtocart = itemView.findViewById(R.id.trashIcon);
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
