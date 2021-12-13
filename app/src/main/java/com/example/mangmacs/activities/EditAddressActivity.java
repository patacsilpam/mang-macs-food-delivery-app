package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity {
    private TextView id,fullname,streetName,phoneNumber;
    private Spinner spinnerBrgy;
    RadioGroup editRdAddress;
    RadioButton radioButton;
    private Button editAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        id = findViewById(R.id.editcustomerId);
        fullname = findViewById(R.id.editfullname);
        streetName = findViewById(R.id.editstreetname);
        phoneNumber = findViewById(R.id.editphoneNumber);
        spinnerBrgy = findViewById(R.id.editbarangay);
        editRdAddress = findViewById(R.id.editrdAddress);
        editAddress = findViewById(R.id.editAddress);
        Intent intent = getIntent();
        String customerID = intent.getStringExtra("id");
        String rfullname = intent.getStringExtra("fullname");
        String street = intent.getStringExtra("street");
        String phoneNo = intent.getStringExtra("phoneNumber");
        String brgy = intent.getStringExtra("brgy");
        String labelAddress = intent.getStringExtra("labelAddress");
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
            int spinnerPosition = ad_urdaneta.getPosition(brgy);
            spinnerBrgy.setSelection(spinnerPosition);
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
                    fullname.setError("Required");
                }
                else  if(street.isEmpty()){
                    streetName.setError("Required");
                }
                else if(phoneNo.isEmpty()){
                    phoneNumber.setError("Required");
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
                                    Toast.makeText(EditAddressActivity.this,message,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditAddressActivity.this, AccountActivity.class));
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