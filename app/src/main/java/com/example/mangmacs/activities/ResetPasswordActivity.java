package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.UpdateAccountModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextView backVerification;
    private TextInputLayout newPword,confirmPword;
    private Button btnResetPword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        newPword = findViewById(R.id.resetNewPword);
        confirmPword = findViewById(R.id.resetConfirmPword);
        btnResetPword = findViewById(R.id.btnResetPword);
        backVerification = findViewById(R.id.backVerification);
        BackVerification();
        ResetPassword();
    }

    private void ResetPassword() {
        btnResetPword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = newPword.getEditText().getText().toString();
                String confirmPassword = confirmPword.getEditText().getText().toString();
                if (newPassword.isEmpty()){
                    newPword.setError("Required");
                }
                else if (!newPassword.equals(confirmPassword)){
                    newPword.setError("Password do not match");
                    confirmPword.setError("Password do not match");
                }
                else if (confirmPassword.isEmpty()){
                    confirmPword.setError("Required");
                }

                else if(confirmPassword.length()<8){
                    confirmPword.setError("Password must be at least 8 characters minimum");
                }
                else{
                    Intent intent = getIntent();
                    String email = intent.getStringExtra("email");
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<UpdateAccountModel> passwordCall = apiInterface.resetPassword(email,newPassword,confirmPassword);
                    passwordCall.enqueue(new Callback<UpdateAccountModel>() {
                        @Override
                        public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                            if(response.body() != null){
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                    startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
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

                    //startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void BackVerification() {
        backVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasswordActivity.this,VerificationActivity.class));
            }
        });
    }
}