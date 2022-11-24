 package com.example.mangmacs.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

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
    private TextInputLayout firstname,lastname,phoneNo,email,password;
    private TextView btnSignIn,termsConditions,errorMsg;
    private CheckBox checkTermsConditions;
    private Button btnSignUp;
    private ApiInterface apiInterface;
    private String token;
    private SendCode getCode;
    private int code;
    private String fname;
    public static final String CHANNEL_ID = "mang_macs";
    public static final String CHANNEL_NAME = "Mang Mac's";
    public static  final String CHANNEL_DESC = "Mang Mac's";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phoneNo = findViewById(R.id.phoneNo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        termsConditions = findViewById(R.id.termsConditions);
        checkTermsConditions = findViewById(R.id.checkTermsConditions);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setVisibility(View.GONE);
        SignIn();
        setFireBaseToken();
        TermsConditions();
        validateUserData();
        createNotification();
    }
    private void setFireBaseToken(){
        FirebaseMessaging.getInstance().subscribeToTopic("mangmacs");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token = task.getResult().getToken();
                            Log.d(TAG,"On complete " + token);
                        }
                        else{
                            Log.d(TAG,"Token not generated");
                        }
                    }
                });
    }
     private void TermsConditions() {
        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),TermsConditionsActivity.class));
            }
        });
     }

     private void SignIn() {
         btnSignIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(sign_up_activity.this, LoginActivity.class));
             }
         });
     }
     private void validateUserData(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode = new SendCode();
                code = getCode.getRandomCode();
                fname = firstname.getEditText().getText().toString();
                String mobileNumber = phoneNo.getEditText().getText().toString();
                String lname = lastname.getEditText().getText().toString();
                String emailAddress = email.getEditText().getText().toString();
                String pword = password.getEditText().getText().toString();
                //get random code
                //validate user data
                if(fname.isEmpty()){
                    firstname.setError("Required");
                    firstname.setErrorIconDrawable(null);
                }
                if (lname.isEmpty()){
                    lastname.setError("Required");
                    lastname.setErrorIconDrawable(null);
                }
                if (mobileNumber.isEmpty()){
                    phoneNo.setError("Required");
                    phoneNo.setErrorIconDrawable(null);
                }
                if (emailAddress.isEmpty()){
                    email.setError("Required");
                    email.setErrorIconDrawable(null);
                }
                if (pword.isEmpty()){
                    password.setError("Required");
                    password.setErrorIconDrawable(null);
                }
                if (pword.length() < 8 ){
                    password.setError("Password must be at least 8 characters.");
                    password.setErrorIconDrawable(null);
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    email.setError("Invalid email");
                    email.setErrorIconDrawable(null);
                }
                else if (!checkTermsConditions.isChecked()){
                    errorMsg.setVisibility(View.VISIBLE);
                    errorMsg.setTextColor(Color.RED);
                }
                else{
                    apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CustomerModel> customerModelCall = apiInterface.registerUser("token",fname,lname,mobileNumber,emailAddress,pword,code);
                    customerModelCall.enqueue(new Callback<CustomerModel>() {
                        @Override
                        public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                            if (response.body() != null) {
                                String success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success.equals("1")){
                                    SharedPreference.getSharedPreference(getApplicationContext()).storeFname(fname);
                                    SharedPreference.getSharedPreference(getApplicationContext()).storePhoneNo(mobileNumber);
                                    Intent intent = new Intent(sign_up_activity.this,VerifyEmailActivity.class);
                                    intent.putExtra("email",emailAddress);
                                    intent.putExtra("code",String.valueOf(code));
                                    startActivity(intent);
                                    verifyEmail(emailAddress);
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

     private void verifyEmail(String emailAddress) {
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
                     "<header style='display: flex; align-items: center;'>\n" +
                     "  <img src='https://i.ibb.co/CMq6CXs/logo.png' width='100' alt='mang-macs-logo'>\n" +
                     "  <h1 style='font-size: .9rem;  font-family: Arial, Helvetica, sans-serif;'> Mang Mac's Foodshop</h1>\n" +
                     "</header>\n" +
                     "<article>\n" +
                     "  <p style='font-size: 1rem; line-height: 1.3rem; font-family: Arial, Helvetica, sans-serif; color: #747474;'>\n Hi "+fname +
                     ",<br>Welcome to Mang Mac's Foodshop. Please use the mentioned code below to verify your account.\n" +
                     "  </p>\n" +
                     "</article>\n" +
                     "<article style='display: flex; justify-content: center;'>\n" +
                     "  <strong style='width:100%; text-align:center; background: #E7E7E7; padding: 2rem; font-size: 2rem; letter-spacing: 3px;'>\n" +code+"</strong>"+
                     "</article>\n" +
                     "<footer style='text-align: center; margin-top: 30px;'>\n" +
                     " <p style='margin: 10px 0 5px 0; font-family: Arial, Helvetica, sans-serif; color: #747474;'>from</p>\n" +
                     " <strong style='font-family: Arial, Helvetica, sans-serif;'>MangMac's Foodshop</strong>\n" +
                     " <p style='margin: 7px 0 0 0; font-family: Arial, Helvetica, sans-serif; color: #747474;'>Zone 5, Brgy. Sta. Lucia Bypass Road,<br>Urdaneta Philippines</p>\n" +
                     "</footer>\n" +
                     "</main>", "text/html");
             new SendEmail().execute(mimeMessage);
         } catch (MessagingException e) {
             e.printStackTrace();
         }
     }

     private void createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
}



