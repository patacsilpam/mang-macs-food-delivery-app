 package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mangmacs.Config;
import com.example.mangmacs.R;
import com.example.mangmacs.SendCode;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CustomerModel;
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

 public class sign_up_activity extends AppCompatActivity {
     private Session session;
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
        validateUserData();
    }
    private void validateUserData(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstname.getEditText().getText().toString();
                String lname = lastname.getEditText().getText().toString();
                String emailAddress = email.getEditText().getText().toString();
                String pword = password.getEditText().getText().toString();
                //get random code
                SendCode getCode = new SendCode();
                int code = getCode.getRandomCode();
                //configure to send email
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
                    mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(emailAddress)));
                    mimeMessage.setSubject("Verify Email");
                    mimeMessage.setContent("<main style='background: #ffffff; width: 350px; position: absolute; top: 50%; left: 50%; transform: translate(-50%,-50%); padding: 1rem;'>\n" +
                            "                        <header style='display: flex; align-items: center;'>\n" +
                            "                            <img src='logo.png' width='100' alt='mang-macs-logo'>\n" +
                            "                            <h1 style='font-size: .9rem;  font-family: Arial, Helvetica, sans-serif;'> Mang Mac's Foodshop</h1>\n" +
                            "                        </header>\n" +
                            "                        <article>\n" +
                            "                            <p style='font-size: 1rem; line-height: 1.3rem; font-family: Arial, Helvetica, sans-serif; color: #747474;'>\n" +
                            "                                Hi,<br>Welcome to Mang Mac's Foodshop. Please use the mentioned code below to verify your account.\n" +
                            "                            </p>\n" +
                            "                        </article>\n" +
                            "                        <article style='display: flex; justify-content: center;'>\n" +
                            "                            <strong style='width:100%; text-align:center; background: #E7E7E7; padding: 2rem; font-size: 2rem; letter-spacing: 3px;'>\n" +code+"</strong>"+
                            "                        </article>\n" +
                            "                        <footer style='text-align: center; margin-top: 30px;'>\n" +
                            "                            <p style='margin: 10px 0 5px 0; font-family: Arial, Helvetica, sans-serif; color: #747474;'>from</p>\n" +
                            "                            <strong style='font-family: Arial, Helvetica, sans-serif;'>MangMac's Foodshop</strong>\n" +
                            "                            <p style='margin: 7px 0 0 0; font-family: Arial, Helvetica, sans-serif; color: #747474;'>Zone 5, Brgy. Sta. Lucia Bypass Road,<br>Urdaneta Philippines</p>\n" +
                            "                        </footer>\n" +
                            "                        </main>", "text/html");
                    new SendEmail().execute(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                //validate user data
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
                    Call<CustomerModel> customerModelCall = apiInterface.registerUser(fname,lname,emailAddress,pword,code);
                    customerModelCall.enqueue(new Callback<CustomerModel>() {
                        @Override
                        public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                            if (response.body() != null) {
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                    Intent intent = new Intent(sign_up_activity.this,VerifyEmailActivity.class);
                                    intent.putExtra("email",emailAddress);
                                    intent.putExtra("code",String.valueOf(code));
                                    startActivity(intent);
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



