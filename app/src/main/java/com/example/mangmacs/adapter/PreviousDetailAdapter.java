package com.example.mangmacs.adapter;

import android.content.Context;
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
import com.example.mangmacs.activities.PreviousOrderDetailsActivity;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousDetailAdapter extends RecyclerView.Adapter<PreviousDetailAdapter.ViewHolder> {
    private Context context;
    private List<CurrentOrdersModel> prevOrdersModelList;
    private OrdersListener ordersListener;
    public PreviousDetailAdapter(Context context,List<CurrentOrdersModel> prevOrdersModelList,OrdersListener ordersListener){
       this.context = context;
       this.prevOrdersModelList = prevOrdersModelList;
       this.ordersListener = ordersListener;
    }
    @NonNull
    @Override
    public PreviousDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_order_details,parent,false);
        return new PreviousDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousDetailAdapter.ViewHolder holder, int position) {
        CurrentOrdersModel previousOrderModel = prevOrdersModelList.get(position);
        Glide.with(context).load(previousOrderModel.getImgProduct()).into(holder.imgProduct);
        holder.textProduct.setText(previousOrderModel.getProducts());
        holder.textAddOns.setText(previousOrderModel.getAddOns());
        holder.textVariation.setText(previousOrderModel.getVariations());
        holder.items.setText(previousOrderModel.getQuantities());
        holder.textPrice.setText(previousOrderModel.getPrice());
        holder.textSpecialRequest.setText("\"" + previousOrderModel.getSpecialRequest() + "\"");
        //set customer details to add to cart
        String fname = SharedPreference.getSharedPreference(context).setFname();
        String lname = SharedPreference.getSharedPreference(context).setLname();
        holder.fname.setText(fname);
        holder.lname.setText(lname);
        String totalAmount = previousOrderModel.getTotalAmount();
        ordersListener.onTotalAmountChange(totalAmount);
        holder.buyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = previousOrderModel.getEmail();
                String productCode = previousOrderModel.getProductCode();
                String productName = previousOrderModel.getProducts();
                String productCategory = previousOrderModel.getCategory();
                String productVariation = previousOrderModel.getVariations();
                String fname = holder.fname.getText().toString();
                String lname = holder.lname.getText().toString();
                int price = Integer.parseInt(previousOrderModel.getPrice());
                int quantity = Integer.parseInt(previousOrderModel.getQuantities());
                String add_ons = previousOrderModel.getAddOns();
                int addOnsTotFee = Integer.parseInt(previousOrderModel.getAddOnsFee());
                String specialRequest = previousOrderModel.getSpecialRequest();
                String productImage = previousOrderModel.getImgProduct();
                String preparationTime = previousOrderModel.getPreparationTime();
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> callCart = apiInterface.addcart(email,productCode,productName,productCategory,productVariation,fname,lname,price,quantity,add_ons,addOnsTotFee,specialRequest,productImage,preparationTime);
                callCart.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if (response.body() != null){
                            String success = response.body().getSuccess();
                            if (success.equals("1")){
                                Intent intent = new Intent(context,CartActivity.class);
                                context.startActivity(intent);
                                ((PreviousOrderDetailsActivity)context).finish();
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });
        //fix the design
        if (previousOrderModel.getAddOns().equals("")){
            holder.textAddOns.setVisibility(View.GONE);
        }
        if (previousOrderModel.getVariations().equals("")){
            holder.textVariation.setVisibility(View.GONE);
        }
        if (previousOrderModel.getSpecialRequest().equals("")){
            holder.textSpecialRequest.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return prevOrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView textProduct,textAddOns,textVariation,textSpecialRequest,items,textPrice,fname,lname;
        private Button buyAgain;
        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textProduct = itemView.findViewById(R.id.textProduct);
            textAddOns = itemView.findViewById(R.id.textAddOns);
            textVariation = itemView.findViewById(R.id.textVariation);
            textSpecialRequest = itemView.findViewById(R.id.textSpecialRequest);
            textPrice = itemView.findViewById(R.id.textPrice);
            items = itemView.findViewById(R.id.items);
            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);
            buyAgain = itemView.findViewById(R.id.buyAgain);
        }
    }
}
