package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView signup;
    private EditText email,password;
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signUp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.signIn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,sign_up_activity.class));
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email.getText().toString();
                String user_password = password.getText().toString();
                if(emailAddress.isEmpty()){
                    email.setError("Required");
                }
                if (user_password.isEmpty()){
                    password.setError("Required");
                }
                else{
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CustomerModel> customerModelCall = apiInterface.userLogin(emailAddress);
                    customerModelCall.enqueue(new Callback<CustomerModel>() {
                        @Override
                        public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                            if (response.body() != null) {
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                                }
                                else if(success.equals("2")){
                                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CustomerModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}