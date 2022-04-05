package com.example.mangmacs.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.fragment.CurrentOrders;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.LTGRAY;

public class NewOrdersDetailAdapter extends RecyclerView.Adapter<NewOrdersDetailAdapter.ViewHolder> {
    private Context context;
    private List<CurrentOrdersModel> currentOrdersModelList;
    private OrdersListener ordersListener;
    ArrayList<Integer> totalamountList = new ArrayList<>();
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
        Glide.with(context).load(currentOrdersModels.getImgProduct()).into(holder.imgProduct);
        holder.textProduct.setText(currentOrdersModels.getProducts());
        holder.textVariation.setText(currentOrdersModels.getVariations());
        holder.items.setText(currentOrdersModels.getQuantities());
        holder.textPrice.setText(currentOrdersModels.getPrice());
        String status = currentOrdersModels.getOrderStatus();
        String orderId = currentOrdersModels.getId();
        int items = Integer.parseInt(holder.items.getText().toString());
        int price = Integer.parseInt(holder.textPrice.getText().toString());
        if (status.equals("Pending")){
            holder.cancelOrder.setEnabled(true);
            holder.cancelOrder.setVisibility(View.VISIBLE);
            holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setCancelable(false)
                            .setMessage("Are you sure you want to cancel this order?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                    Call<CurrentOrdersModel> callOrder = apiInterface.cancelOrder(orderId);
                                    callOrder.enqueue(new Callback<CurrentOrdersModel>() {
                                        @Override
                                        public void onResponse(Call<CurrentOrdersModel> call, Response<CurrentOrdersModel> response) {
                                            if(response.body() != null){
                                                String success = response.body().getSuccess();
                                                if (success.equals("1")){
                                                    Toast.makeText(context,"Cancelled Order Successfully",Toast.LENGTH_SHORT).show();
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CurrentOrdersModel> call, Throwable t) {

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
                    AlertDialog  alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
        else{
            holder.cancelOrder.setEnabled(false);
            holder.cancelOrder.setVisibility(View.GONE);
        }

    }
    public int getItemCount() {
        return currentOrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView textProduct,textVariation,items,textPrice;
        private Button cancelOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textProduct = itemView.findViewById(R.id.textProduct);
            textVariation = itemView.findViewById(R.id.textVariation);
            textPrice = itemView.findViewById(R.id.textPrice);
            items = itemView.findViewById(R.id.items);
            cancelOrder = itemView.findViewById(R.id.cancelOrder);
        }
    }
}
