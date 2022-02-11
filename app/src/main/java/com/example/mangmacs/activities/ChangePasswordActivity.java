package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.UpdateAccountModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputLayout currentPword,newPword,confirmPword;
    private Button updatePword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        currentPword = findViewById(R.id.currentPword);
        newPword = findViewById(R.id.newPword);
        confirmPword = findViewById(R.id.confirmPword);
        updatePword = findViewById(R.id.updatePword);
        UpdatePassword();
    }

    private void UpdatePassword() {
        updatePword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = currentPword.getEditText().getText().toString();
                String newPassword = newPword.getEditText().getText().toString();
                String confirmPassword = confirmPword.getEditText().getText().toString();
                if(newPassword.isEmpty()){
                    newPword.setError("Required");
                }
                if (confirmPassword.isEmpty()){
                    confirmPword.setError("Required");
                }
                if(!newPassword.equals(confirmPassword)){
                    confirmPword.setError("Password do not match");
                    newPword.setError("Password do not match");
                }
                if (confirmPassword.length()<8){
                    confirmPword.setError("Password must be at least 8 characters minimum");
                }
                else{
                    String saveEmail = SharedPreference.getSharedPreference(getApplicationContext()).setEmail();
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<UpdateAccountModel> updatePwordCall = apiInterface.updatePassword(saveEmail,currentPassword,newPassword);
                    updatePwordCall.enqueue(new Callback<UpdateAccountModel>() {
                        @Override
                        public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                            if(response.body() != null) {
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if (success.equals("1")) {
                                    Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangePasswordActivity.this,AccountActivity.class));
                                }
                                else if(success.equals("0")){
                                    Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<UpdateAccountModel> call, Throwable t) {
                            Toast.makeText(ChangePasswordActivity.this,"False",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}