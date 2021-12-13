package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.CustomerLoginModel;
import com.example.mangmacs.model.CustomerModel;
import com.example.mangmacs.model.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView signup,forgotPword;
    private TextInputLayout email,password;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signUp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.signIn);
        forgotPword = findViewById(R.id.forgotPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        signUp();
        signIn();
        forgotPword();
    }

    private void signIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email.getEditText().getText().toString().trim();
                String user_password = password.getEditText().getText().toString().trim();
                if(emailAddress.isEmpty()){
                    email.setError("Required");
                }
                if (user_password.isEmpty()){
                    password.setError("Required");
                }
                else{
                    progressDialog.show();
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CustomerLoginModel> customerModelCall = apiInterface.userLogin(emailAddress,user_password);
                    customerModelCall.enqueue(new Callback<CustomerLoginModel>() {
                        @Override
                        public void onResponse(Call<CustomerLoginModel> call, Response<CustomerLoginModel> response) {
                            if (response.body() != null) {
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                String fname = response.body().getFname();
                                String email = response.body().getEmail_address();
                                String lname = response.body().getLname();
                                String customer_id = response.body().getCustomerID();
                                if(success.equals("1")){
                                    progressDialog.dismiss();
                                    SharedPreference.getSharedPreference(LoginActivity.this).storeFname(fname);
                                    SharedPreference.getSharedPreference(LoginActivity.this).storeEmail(email);
                                    SharedPreference.getSharedPreference(LoginActivity.this).storeLname(lname);
                                    SharedPreference.getSharedPreference(LoginActivity.this).storeID(customer_id);
                                    Intent intent = new Intent(LoginActivity.this,home_activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(success.equals("2")){
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<CustomerLoginModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
    private void signUp() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, sign_up_activity.class));
            }
        });
    }
    private void forgotPword() {
        forgotPword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }
}