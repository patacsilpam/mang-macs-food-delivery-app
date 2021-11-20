package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CustomerLoginModel;
import com.example.mangmacs.model.CustomerModel;
import com.example.mangmacs.model.UpdateAccountModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.parseColor;

public class MyAccountActivity extends AppCompatActivity {
    private EditText birthdate;
    private TextView emailAddress;
    private TextInputLayout firstname,lastname;
    private RadioGroup rdGender;
    private RadioButton radioButton;
    private Button btnSaveAccount;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        firstname = findViewById(R.id.acc_firstname);
        lastname = findViewById(R.id.acc_lastname);
        emailAddress = findViewById(R.id.acc_email);
        birthdate = findViewById(R.id.acc_birthdate);
        rdGender = findViewById(R.id.rdGender);
        btnSaveAccount = findViewById(R.id.btnSaveAccount);
        sharedPreference = new SharedPreference(this);
        //set on click calendar
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }
            private void updateCalendar() {
                String Format = "yy/MM/dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.TAIWAN);
                birthdate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MyAccountActivity.this, setDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //convert to string
        String email = SharedPreference.getSharedPreference(this).setEmail();
        //call api for select account
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<UpdateAccountModel> selectAccountCall = apiInterface.selectAccount(email);
        selectAccountCall.enqueue(new Callback<UpdateAccountModel>() {
            @Override
            public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                if(response.body() != null){
                    String success = response.body().getSuccess();
                    if(success.equals("1")){
                        String fname = response.body().getFname();
                        String lname = response.body().getLname();
                        String bdate = response.body().getBirthdate();
                        String gender = response.body().getGender();
                        firstname.getEditText().setText(fname);
                        lastname.getEditText().setText(lname);
                        birthdate.setText(bdate);
                        emailAddress.setText(email);
                        if(gender.equalsIgnoreCase("Male")){
                            rdGender.check(R.id.male);
                        }
                        else if(gender.equalsIgnoreCase("Female")){
                            rdGender.check(R.id.female);
                        }
                        else{
                            rdGender.check(R.id.male);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateAccountModel> call, Throwable t) {

            }
        });
        btnSaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstname.getEditText().getText().toString();
                String lname = lastname.getEditText().getText().toString();
                String bdate = birthdate.getText().toString();
                int selectedGender = rdGender.getCheckedRadioButtonId();
                radioButton = findViewById(selectedGender);
                String gender = radioButton.getText().toString();

               if(fname.isEmpty()){
                   firstname.setError("Required");
                   firstname.setBackgroundColor(Color.WHITE);
                   firstname.setBoxStrokeErrorColor(ColorStateList.valueOf(Color.RED));
               }
               else if (lname.isEmpty()){
                   lastname.setError("Required");
               }
               else if (bdate.isEmpty()){
                   birthdate.setError("Required");
               }
               else{
                   String saveEmail = SharedPreference.getSharedPreference(getApplicationContext()).setEmail();
                   ApiInterface apiInterface1 = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                   Call<UpdateAccountModel> updateAccountModelCall = apiInterface1.updateAccount(saveEmail,fname,lname,gender,bdate);
                   updateAccountModelCall.enqueue(new Callback<UpdateAccountModel>() {
                       @Override
                       public void onResponse(Call<UpdateAccountModel> call, Response<UpdateAccountModel> response) {
                           if(response.body() != null){
                               String success = response.body().getSuccess();
                               String message = response.body().getMessage();
                               if(success.equals("1")){
                                   Toast.makeText(MyAccountActivity.this,message,Toast.LENGTH_SHORT).show();
                                   restartData();
                               }
                               else{
                                   Toast.makeText(MyAccountActivity.this,message,Toast.LENGTH_SHORT).show();
                               }
                           }
                       }

                       private void restartData() {
                           SharedPreferences sharedPreferences = getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
                           SharedPreferences.Editor editor = sharedPreferences.edit();
                           editor.putString(SharedPreference.FIRST_NAME,fname);
                           editor.putString(SharedPreference.LNAME,lname);
                           editor.apply();
                           Intent intent = getIntent();
                           finish();
                           startActivity(intent);
                       }

                       @Override
                       public void onFailure(Call<UpdateAccountModel> call, Throwable t) {

                       }
                   });
               }
            }
        });

    }



}