 package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CustomerModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class sign_up_activity extends AppCompatActivity {
    private TextInputLayout firstname,lastname,email,password;
    private TextView btnSignIn;
    private Button btnSignUp;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        //login
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_up_activity.this, LoginActivity.class));
            }
        });
        //register
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();
            }
        });
    }

    private void validateUserData(){
        String fname = firstname.getEditText().getText().toString();
        String lname = lastname.getEditText().getText().toString();
        String emailAddress = email.getEditText().getText().toString();
        String pword = password.getEditText().getText().toString();
        if(fname.isEmpty()){
            firstname.setError("Required");
        }
        if (lname.isEmpty()){
            lastname.setError("Required");
        }
        if (pword.isEmpty()){
            password.setError("Required");
        }
        if (emailAddress.isEmpty()){
            email.setError("Required");
        }
        if (pword.length() < 8 ){
            password.setError("Password must be at least 8 characters.");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            email.setError("Invalid email");
        }
        else{
            apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
            Call<CustomerModel> customerModelCall = apiInterface.registerUser(fname,lname,emailAddress,pword);
            customerModelCall.enqueue(new Callback<CustomerModel>() {
                @Override
                public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                    if (response.body() != null) {
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if(success.equals("1")){
                            Toast.makeText(sign_up_activity.this,message,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(sign_up_activity.this,LoginActivity.class));
                        }
                        else if(success.equals("2")){
                            Toast.makeText(sign_up_activity.this,message,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(sign_up_activity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomerModel> call, Throwable t) {
                    Toast.makeText(sign_up_activity.this,"Error!",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}



