package com.example.mangmacs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.model.AddressListModel;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.AddressViewHolder> {
    private Context context;
    private List<AddressListModel> addressLists;
    public DeliveryAddressAdapter(Context context, List<AddressListModel> addressLists){
        this.context = context;
        this.addressLists = addressLists;
    }
    public int selectedPosition = -1;
    @NonNull
    @Override
    public DeliveryAddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.address_lists,null);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAddressAdapter.AddressViewHolder holder, int position) {
      AddressListModel addressListModel = addressLists.get(position);
      String street = addressListModel.getStreet();
      String brgy = addressListModel.getBarangay();
      String city = addressListModel.getCity();
      String phoneNumber = addressListModel.getPhoneNumber();
      String categoryAddress = addressListModel.getLabelAddress();
      holder.name.setText(addressListModel.getFullname());
      holder.address.setText(street+" "+brgy+","+city);
      holder.number.setText(phoneNumber);
      holder.categoryaddress.setText(categoryAddress);
      holder.radioButton.setChecked(position == selectedPosition);
      holder.radioButton.setTag(position);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
            }
        });
    }

    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addressLists.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radioButton,radioGrp;
        private TextView name,address,number,categoryaddress;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radiobtn);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            number = itemView.findViewById(R.id.number);
            categoryaddress = itemView.findViewById(R.id.categoryaddress);
        }
    }
}
