package com.example.mangmacs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mangmacs.model.CustomerLoginModel;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask {
    private ProgressDialog progressDialog;
    private Session session;
    private Context context;
    private String email;
    private String subject;
    private String message;

    public SendMail(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = ProgressDialog.show(context, "Sending message", "Please wait...", false, false);
    }

    protected void onPostExecute(Void o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Object... objects) {
        SendCode getCode = new SendCode();
        int code = getCode.getRandomCode();
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
            mimeMessage.setText("To reset your password. Enter this code"+code);
            Transport.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
