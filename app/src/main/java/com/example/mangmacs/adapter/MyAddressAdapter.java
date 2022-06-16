package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.activities.EditAddressActivity;
import com.example.mangmacs.R;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.AddressListModel;
import com.example.mangmacs.model.UpdateAccountModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyAddressHolder> {
    private Context context;
    private List<AddressListModel> addressList;
    public MyAddressAdapter(Context context, List<AddressListModel> addressList){
        this.context = context;
        this.addressList = addressList;
    }
    @NonNull
    @Override
    public MyAddressAdapter.MyAddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.address_list,null);
        return new MyAddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressAdapter.MyAddressHolder holder, int position) {
        AddressListModel addressListModel = addressList.get(position);
        holder.ID.setText(String.valueOf(addressListModel.getId()));
        holder.fullname.setText(addressListModel.getFullname());
        holder.street.setText(addressListModel.getStreet());
        holder.brgy.setText(addressListModel.getBarangay()+", ");
        holder.city.setText(addressListModel.getCity());
        holder.province.setText(addressListModel.getProvince());
        holder.labelAddress.setText(addressListModel.getLabelAddress());
        holder.phoneNumber.setText(addressListModel.getPhoneNumber());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                intent.putExtra("id",String.valueOf(addressListModel.getId()));
                intent.putExtra("fullname",addressListModel.getFullname());
                intent.putExtra("street",addressListModel.getStreet());
                intent.putExtra("brgy",addressListModel.getBarangay()+" ");
                intent.putExtra("city",addressListModel.getCity()+" ");
                intent.putExtra("province",addressListModel.getProvince());
                intent.putExtra("labelAddress",addressListModel.getLabelAddress());
                intent.putExtra("phoneNumber",addressListModel.getPhoneNumber());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class MyAddressHolder extends RecyclerView.ViewHolder {
        private TextView edit,ID,fullname,street,brgy,city,province,phoneNumber,labelAddress;
        public MyAddressHolder(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit);
            ID = itemView.findViewById(R.id.ID);
            fullname = itemView.findViewById(R.id.afullname);
            street = itemView.findViewById(R.id.street);
            brgy = itemView.findViewById(R.id.brgy);
            city = itemView.findViewById(R.id.city);
            province = itemView.findViewById(R.id.aprovince);
            phoneNumber = itemView.findViewById(R.id.phoneNo);
            labelAddress = itemView.findViewById(R.id.labelAddress);
        }
    }
}
