package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mangmacs.Config;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.UpdateAccountModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputLayout currentPword,newPword,confirmPword;
    private Button updatePword;
    private Session session;
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
                    newPword.setErrorIconDrawable(null);
                }
                if (confirmPassword.isEmpty()){
                    confirmPword.setError("Required");
                    confirmPword.setErrorIconDrawable(null);
                }
                if(!newPassword.equals(confirmPassword)){
                    confirmPword.setError("Password do not match");
                    newPword.setError("Password do not match");
                    newPword.setErrorIconDrawable(null);
                    confirmPword.setErrorIconDrawable(null);
                }
                if (confirmPassword.length()<8){
                    confirmPword.setError("Password must be at least 8 characters minimum");
                    confirmPword.setErrorIconDrawable(null);
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
                            Toast.makeText(ChangePasswordActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}