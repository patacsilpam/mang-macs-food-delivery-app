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
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.CartActivity;
import com.example.mangmacs.activities.PreviousOrderDetailsActivity;
import com.example.mangmacs.activities.PreviousReservationActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.ReservationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousBookingDetailAdapter extends RecyclerView.Adapter<PreviousBookingDetailAdapter.ViewHolder> {
    private Context context;
    private List<ReservationModel> reservationList;

    public PreviousBookingDetailAdapter(Context context,List<ReservationModel> reservationList){
        this.context = context;
        this.reservationList = reservationList;
    }
    @NonNull
    @Override
    public PreviousBookingDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_order_details,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousBookingDetailAdapter.ViewHolder holder, int position) {
        ReservationModel reservationModel = reservationList.get(position);
        int productPrice = Integer.parseInt(reservationModel.getPrice());
        int addOnsPrice = Integer.parseInt(reservationModel.getAddOnsFee());
        Glide.with(context).load(reservationModel.getImgProduct()).into(holder.imgProduct);
        holder.textProduct.setText(reservationModel.getProducts());
        holder.textAddOns.setText(reservationModel.getAddOns());
        holder.textVariation.setText(reservationModel.getVariations());
        holder.items.setText(reservationModel.getQuantities());
        holder.textPrice.setText(String.valueOf(productPrice + addOnsPrice));
        holder.textSpecialRequest.setText(reservationModel.getSpecialRequest());
        //set customer details to add to cart
        String fname = SharedPreference.getSharedPreference(context).setFname();
        String lname = SharedPreference.getSharedPreference(context).setLname();
        holder.fname.setText(fname);
        holder.lname.setText(lname);
        holder.buyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = reservationModel.getEmail();
                String productCode = reservationModel.getProductCode();
                String productName = reservationModel.getProducts();
                String productCategory = reservationModel.getCategory();
                String productVariation = reservationModel.getVariations();
                String fname = holder.fname.getText().toString();
                String lname = holder.lname.getText().toString();
                int price = Integer.parseInt(reservationModel.getPrice());
                int quantity = Integer.parseInt(reservationModel.getQuantities());
                String add_ons = reservationModel.getAddOns();
                int addOnsTotFee = Integer.parseInt(reservationModel.getAddOnsFee());
                String specialRequest = reservationModel.getSpecialRequest();
                String productImage = reservationModel.getImgProduct();
                String preparationTime = reservationModel.getPreparationTime();
                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> callCart = apiInterface.addcart(email,productCode,"",productName,productCategory,productVariation,fname,lname,price,quantity,add_ons,addOnsTotFee,specialRequest,productImage,preparationTime);
                callCart.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if (response.body() != null){
                            String success = response.body().getSuccess();
                            if (success.equals("1")){
                                Intent intent = new Intent(context, CartActivity.class);
                                context.startActivity(intent);
                                ((PreviousReservationActivity)context).finish();
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
        if (reservationModel.getAddOns().equals("")){
            holder.textAddOns.setVisibility(View.GONE);
        }
        if (reservationModel.getVariations().equals("")){
            holder.textVariation.setVisibility(View.GONE);
        }
        if (reservationModel.getSpecialRequest().equals("")){
            holder.textSpecialRequest.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView textProduct,textAddOns,textVariation,textSpecialRequest,items,textPrice,fname,lname;
        private Button buyAgain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textProduct = itemView.findViewById(R.id.textProduct);
            textAddOns = itemView.findViewById(R.id.textAddOns);
            textVariation = itemView.findViewById(R.id.textVariation);
            textPrice = itemView.findViewById(R.id.textPrice);
            textSpecialRequest = itemView.findViewById(R.id.textSpecialRequest);
            items = itemView.findViewById(R.id.items);
            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);
            buyAgain = itemView.findViewById(R.id.buyAgain);
        }
    }
}
