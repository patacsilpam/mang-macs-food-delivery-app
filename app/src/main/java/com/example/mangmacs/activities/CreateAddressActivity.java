package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.UpdateAccountModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAddressActivity extends AppCompatActivity {
    private TextView customerId;
    private EditText rfullname,province,city,street,phoneNo;
    private Spinner spinnerUrdaneta;
    private RadioGroup rdAddress;
    private RadioButton radioButton;
    private ProgressBar progressBar;
    private Button btnCreateAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);
        progressBar = findViewById(R.id.spin_kit);
        customerId = findViewById(R.id.customerId);
        rfullname = findViewById(R.id.rfullname);
        province = findViewById(R.id.province);
        city = findViewById(R.id.city);
        spinnerUrdaneta = findViewById(R.id.barangay);
        street = findViewById(R.id.streetname);
        rdAddress = findViewById(R.id.rdAddress);
        phoneNo = findViewById(R.id.phoneNumber);
        btnCreateAddress = findViewById(R.id.createAddress);
        //list of urdaneta spinner
        selectAddress();
        getCustomer();
        insertAddress();
    }

    private void insertAddress() {
        btnCreateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = SharedPreference.getSharedPreference(getApplicationContext()).setEmail();
                String customerID = customerId.getText().toString();
                String fullname = rfullname.getText().toString();
                String sprovince = province.getText().toString();
                String scity = city.getText().toString();
                String getBrgy = spinnerUrdaneta.getSelectedItem().toString();
                String sstreet = street.getText().toString();
                String phoneNumber = phoneNo.getText().toString();
                int selectedAddress = rdAddress.getCheckedRadioButtonId();
                radioButton = findViewById(selectedAddress);
                String labelAddress = radioButton.getText().toString();
                if(fullname.isEmpty()) {
                    rfullname.setError("Required");
                }
                if(sstreet.isEmpty()){
                    street.setError("Required");
                }
                if(phoneNumber.isEmpty()){
                    phoneNo.setError("Required");
                }
                else{
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<UpdateAccountModel> insertAddress = apiInterface.createAddress(customerID,fullname,email,sstreet,getBrgy,scity,sprovince,labelAddress,phoneNumber);
                    insertAddress.enqueue(new Callback<UpdateAccountModel>() {
                        @Override
                        public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                            if(response.body() != null){
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                    startActivity(new Intent(CreateAddressActivity.this,MyAddressActivity.class));
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
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

    private void getCustomer() {
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.VISIBLE);
        String email = SharedPreference.getSharedPreference(getApplicationContext()).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<UpdateAccountModel> getCustomerId = apiInterface.selectAccount(email);
        getCustomerId.enqueue(new Callback<UpdateAccountModel>() {
            @Override
            public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                if(response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    String success = response.body().getSuccess();
                    if(success.equals("1")){
                        String customerID = response.body().getCustomerID();
                        customerId.setText(customerID);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateAccountModel> call, Throwable t) {

            }
        });
    }

    private void selectAddress() {
        ArrayAdapter<CharSequence> ad_urdaneta = ArrayAdapter.createFromResource(this,R.array.urdaneta, android.R.layout.simple_spinner_item);
        ad_urdaneta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrdaneta.setAdapter(ad_urdaneta);
    }
}