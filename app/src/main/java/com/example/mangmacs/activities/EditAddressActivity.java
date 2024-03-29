package com.example.mangmacs.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.UpdateAccountModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity{
    private TextView id,streetName,barangay;
    private TextInputEditText fullname,phoneNumber;
    private TextInputLayout nameError,contactError;
    private Spinner spinnerBrgy;
    RadioGroup editRdAddress;
    RadioButton radioButton;
    private Button editAddress,btnDeleteAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        id = findViewById(R.id.editcustomerId);
        fullname = findViewById(R.id.editfullname);
        streetName = findViewById(R.id.editstreetname);
        phoneNumber = findViewById(R.id.editphoneNumber);
        barangay = findViewById(R.id.barangay);
        spinnerBrgy = findViewById(R.id.editbarangay);
        editRdAddress = findViewById(R.id.editrdAddress);
        editAddress = findViewById(R.id.editAddress);
        nameError = findViewById(R.id.nameError);
        contactError = findViewById(R.id.contactError);
        btnDeleteAddress = findViewById(R.id.btnDeleteAddress);
        AddressAdapter();
        DeleteAddress();
    }

    private void DeleteAddress() {
        btnDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditAddressActivity.this);
                alertDialog.setMessage("Are you sure you want to delete this address?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String addressId = id.getText().toString();
                                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                                Call<UpdateAccountModel> deleteAddressCall = apiInterface.deleteAddress(addressId);
                                deleteAddressCall.enqueue(new Callback<UpdateAccountModel>() {
                                    @Override
                                    public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                                        String success = response.body().getSuccess();
                                        String message = response.body().getMessage();
                                        if(response.body() != null){
                                            if(success.equals("1")){
                                               startActivity(new Intent(getApplicationContext(),MyAddressActivity.class));
                                            }
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UpdateAccountModel> call, Throwable t) {

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
    }

    private void AddressAdapter() {
        Intent intent = getIntent();
        String customerID = intent.getStringExtra("id");
        String rfullname = intent.getStringExtra("fullname");
        String street = intent.getStringExtra("street");
        String phoneNo = intent.getStringExtra("phoneNumber");
        String brgy = intent.getStringExtra("brgy");
        String labelAddress = intent.getStringExtra("labelAddress");
        barangay.setText(brgy);
        if(intent != null){
            id.setText(String.valueOf(customerID));
            fullname.setText(rfullname);
            streetName.setText(street);
            phoneNumber.setText(phoneNo);
            if(labelAddress.equalsIgnoreCase("Home")){
                editRdAddress.check(R.id.edithome);
            }
            else{
                editRdAddress.check(R.id.editoffice);
            }
            ArrayAdapter<CharSequence> ad_urdaneta = ArrayAdapter.createFromResource(this,R.array.urdaneta, android.R.layout.simple_spinner_item);
            ad_urdaneta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBrgy.setAdapter(ad_urdaneta);
            int selectedPosition = ad_urdaneta.getPosition(String.valueOf(brgy).concat(""));
            spinnerBrgy.setSelection(selectedPosition);


        }
        updateAddress();
    }

    private void updateAddress() {
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID = Integer.parseInt(id.getText().toString());
                String sfullname = fullname.getText().toString();
                String street = streetName.getText().toString();
                String phoneNo = phoneNumber.getText().toString();
                String barangay = spinnerBrgy.getSelectedItem().toString();
                int address = editRdAddress.getCheckedRadioButtonId();
                radioButton = findViewById(address);
                String getAddress = radioButton.getText().toString();
                if(sfullname.isEmpty()) {
                    nameError.setError("Required");
                    nameError.setErrorIconDrawable(null);
                }
                else if(phoneNo.isEmpty()){
                    contactError.setError("Required");
                    contactError.setErrorIconDrawable(null);
                }
                else{
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<UpdateAccountModel> updateAddressCall = apiInterface.updateCustomerAddress(ID,sfullname,street,barangay,getAddress,phoneNo);
                    updateAddressCall.enqueue(new Callback<UpdateAccountModel>() {
                        @Override
                        public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                            if(response.body() != null){
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                    startActivity(new Intent(EditAddressActivity.this, MyAddressActivity.class));
                                }
                                else{
                                    Toast.makeText(EditAddressActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateAccountModel> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
}