package com.example.mangmacs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.model.AddressListModel;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.AddressViewHolder> {
    private Context context;
    private List<AddressListModel> addressLists;
    private String strFullName,strPhoneNumber,strAddress,strLabelAddress;
    private int isCheckButton = 0;
    public int selectedPosition = -1;
    public DeliveryAddressAdapter(Context context, List<AddressListModel> addressLists){
        this.context = context;
        this.addressLists = addressLists;
    }
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
      String customerIds = addressListModel.getCustomerID();
      holder.name.setText(addressListModel.getFullname());
      holder.address.setText(street+" "+brgy+","+city);
      holder.number.setText(phoneNumber);
      holder.categoryaddress.setText(categoryAddress);
      holder.radioButton.setChecked(position == selectedPosition);
      holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
              if (isChecked){

                  selectedPosition = holder.getBindingAdapterPosition();
                  strFullName = holder.name.getText().toString();
                  strAddress = holder.address.getText().toString();
                  strPhoneNumber = holder.number.getText().toString();
                  strLabelAddress = holder.categoryaddress.getText().toString();
                  isCheckButton = 1;
                  notifyDataSetChanged();

              }

          }
      });
      //holder.radioButton.setTag(position);
      /* holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
                strFullName = holder.name.getText().toString();
                strAddress = holder.address.getText().toString();
                strPhoneNumber = holder.number.getText().toString();
                strLabelAddress = holder.categoryaddress.getText().toString();
            }
        });*/

        Intent intent = new Intent("MyUserDetails");
        intent.putExtra("fullName", strFullName);
        intent.putExtra("phoneNumber",strPhoneNumber);
        intent.putExtra("address",strAddress);
        intent.putExtra("labelAddress",strLabelAddress);
        intent.putExtra("customerId",customerIds);
        intent.putExtra("checkedButton",isCheckButton);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


    }

    /*private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }*/
    @Override public long getItemId(int position)
    {
        // pass position
        return position;
    }
    @Override public int getItemViewType(int position)
    {

        // pass position
        return position;
    }


    @Override
    public int getItemCount() {
        return addressLists.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radioButton;
        private RadioGroup radioGroup;
        private TextView name,address,number,categoryaddress;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioGrp);
            //radioGroup = itemView.findViewById(R.id.radioGrp);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            number = itemView.findViewById(R.id.number);
            categoryaddress = itemView.findViewById(R.id.categoryaddress);
        }
    }
}
