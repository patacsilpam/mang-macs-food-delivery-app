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

public class ResetPasswordActivity extends AppCompatActivity {
    private TextView backVerification;
    private TextInputLayout newPword,confirmPword;
    private Button btnResetPword;
    private Session session;
    private String email,fname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        newPword = findViewById(R.id.resetNewPword);
        confirmPword = findViewById(R.id.resetConfirmPword);
        btnResetPword = findViewById(R.id.btnResetPword);
        backVerification = findViewById(R.id.backVerification);
        fname = SharedPreference.getSharedPreference(getApplicationContext()).setFname();
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
                    newPword.setErrorIconDrawable(null);
                }
                else if (!newPassword.equals(confirmPassword)){
                    newPword.setError("Password do not match");
                    confirmPword.setError("Password do not match");
                    newPword.setErrorIconDrawable(null);
                    confirmPword.setErrorIconDrawable(null);
                }
                else if (confirmPassword.isEmpty()){
                    confirmPword.setError("Required");
                    confirmPword.setErrorIconDrawable(null);
                }

                else if(confirmPassword.length()<8){
                    confirmPword.setError("Password must be at least 8 characters minimum");
                    confirmPword.setErrorIconDrawable(null);
                }
                else{
                    Intent intent = getIntent();
                     email = intent.getStringExtra("email");
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
                                    resetPassword();
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

    private void resetPassword() {
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
            //get current time
            Date newDate = new Date();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
            String getCurrentTime = String.valueOf(df.format(newDate));
            //
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(Config.EMAIL));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject("Your Mang Mac's Password has been change");
            mimeMessage.setContent("<main style='background: #ffffff; width: 350px; position: absolute; top: 50%; left: 50%; transform: translate(-50%,-50%); padding: 1rem;'>\n" +
                    "<header style='display: flex; align-items: center;'>\n" +
                    "  <img src='https://i.ibb.co/CMq6CXs/logo.png' width='100' alt='mang-macs-logo'>\n" +
                    "  <h1 style='font-size: .9rem;  font-family: Arial, Helvetica, sans-serif;'> Mang Mac's Foodshop</h1>\n" +
                    "</header>\n" +
                    "<article style=\"display: flex; justify-content:center; align-items:center; flex-direction:column;\">\n" +
                    "   <img src='https://i.ibb.co/1z7gB1f/checkmark.png' width='70' alt='checked'>" +
                    "   <p style='font-size: 1rem; line-height: 1.3rem; font-family: Arial, Helvetica, sans-serif; color: #747474;'>Hi "+ fname +",</p>" +
                    "   <p style='font-size: 1rem; line-height: 1.3rem; font-family: Arial, Helvetica, sans-serif; color: #747474;'>\nyour password has been changed on" +getCurrentTime+".</p>\n" +
                    "</article>"+
                    "<footer style='text-align: center; margin-top: 30px;'>\n" +
                    "   <p style='margin: 10px 0 5px 0; font-family: Arial, Helvetica, sans-serif; color: #747474;'>from</p>\n" +
                    "   <strong style='font-family: Arial, Helvetica, sans-serif;'>MangMac's Foodshop</strong>\n" +
                    "   <p style='margin: 7px 0 0 0; font-family: Arial, Helvetica, sans-serif; color: #747474;'>Zone 5, Brgy. Sta. Lucia Bypass Road,<br>Urdaneta Philippines</p>\n" +
                    "</footer>\n" +
                    "</main>", "text/html");
            new SendEmail().execute(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
    private void BackVerification() {
        backVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasswordActivity.this,VerificationActivity.class));
            }
        });
    }
}