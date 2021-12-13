package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.Config;
import com.example.mangmacs.R;
import com.example.mangmacs.activities.ForgotPasswordActivity;
import com.example.mangmacs.activities.VerificationActivity;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.UpdateAccountModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Properties;

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

public class VerifyEmailActivity extends AppCompatActivity {
    private Session session;
    private TextView backforgotPword,resendCode;
    private TextInputLayout code;
    private Button btnVerifyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        code = findViewById(R.id.code);
        resendCode = findViewById(R.id.resendCode);
        backforgotPword = findViewById(R.id.backforgotPword);
        btnVerifyCode = findViewById(R.id.btnVerifyCode);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String vercode = intent.getStringExtra("code");
        backforgotPword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerifyEmailActivity.this,sign_up_activity.class));
            }
        });
        //check if the code is true
        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificationCode = code.getEditText().getText().toString().trim();
                if(verificationCode.isEmpty()){
                    code.setError("Required");
                }
                else{
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<UpdateAccountModel> getEmailCall = apiInterface.getEmail(email,verificationCode);
                    getEmailCall.enqueue(new Callback<UpdateAccountModel>() {
                        @Override
                        public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                            if(response.body() != null){
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                   startActivity(new Intent(VerifyEmailActivity.this,LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(VerifyEmailActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateAccountModel> call, Throwable t) {
                            Toast.makeText(VerifyEmailActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        //resend verification code
        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                session = javax.mail.Session.getDefaultInstance(props,
                        new javax.mail.Authenticator(){
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(Config.EMAIL,Config.PASSWORD);
                            }
                        });
                try {
                    MimeMessage mimeMessage = new MimeMessage(session);
                    mimeMessage.setFrom(new InternetAddress(Config.EMAIL));
                    mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
                    mimeMessage.setSubject("Reset Password");
                    mimeMessage.setText("To reset your password. Enter this code "+vercode);
                    new SendEmail().execute(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private class SendEmail extends AsyncTask<Message,String,String> {

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}