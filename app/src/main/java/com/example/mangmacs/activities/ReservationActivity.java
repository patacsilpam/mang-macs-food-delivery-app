package com.example.mangmacs.activities;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mangmacs.InputMinMax;
import com.example.mangmacs.R;
import com.example.mangmacs.model.ReservationModel;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {
    private Context context = this;
    private ProgressBar progressBar;
    private Spinner spinnerDiningArea;
    private TextInputEditText  paymentNumber,people,date, time, commentSuggestion;
    private TextInputLayout paymentRefError,guestsError, dateError, timeError;
    private TextView diningAreaError, textRequired, minGuestError,paymentError;
    private Button btnBookNow;
    //private ImageView imgPayment;
    private RelativeLayout bgDiningArea;
    private BottomNavigationView bottomNavigationView;
    private int hour, min;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private Bitmap bitmap;
    private Uri selectedImage;
    private String token, strDiningArea;
    private String[] diningAreaList = {"Please select dining area", "Resto", "Venue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        progressBar = findViewById(R.id.spin_kit);
        //paymentNumber = findViewById(R.id.paymentNumber);
        people = findViewById(R.id.people);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        commentSuggestion = findViewById(R.id.commentsSuggestions);
        //paymentRefError = findViewById(R.id.paymentRefError);
        guestsError = findViewById(R.id.guestsError);
        minGuestError = findViewById(R.id.minGuestError);
        dateError = findViewById(R.id.dateError);
        timeError = findViewById(R.id.timeError);
        //paymentError = findViewById(R.id.paymentRequired);
        textRequired = findViewById(R.id.textRequired);
        //imgPayment = findViewById(R.id.imgPayment);
        btnBookNow = findViewById(R.id.btnBookNow);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.book);
        textRequired.setVisibility(View.GONE);
        //paymentError.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        btnBookNow.setEnabled(false);
        //paymentNumber.addTextChangedListener(textWatcher);
        //people.addTextChangedListener(textWatcher);
        //diningAreaError = findViewById(R.id.diningAreaError);
        //bgDiningArea = findViewById(R.id.bgDiningArea);
        //spinnerDiningArea = findViewById(R.id.diningArea);
        //spinnerDiningArea.setOnItemSelectedListener(this);
        //setDiningArea();
        setFirebaseToken();
        validateGuests();
        //validatePaymentNumber();
        SetCalendar();
        //cameraPermission();
        Booking();
        BottomNav();
    }

    private void validateGuests(){
        people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String strGuests = people.getText().toString();
                try{
                    if(((Integer.parseInt(strGuests) <= 100)) && (Integer.parseInt(strGuests) != 0)){
                        btnBookNow.setEnabled(true);
                        minGuestError.setVisibility(View.GONE);
                    }
                    else{
                        btnBookNow.setEnabled(false);
                        minGuestError.setVisibility(View.VISIBLE);
                    }


                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strGuests = people.getText().toString();
                if(strGuests.matches("")) {
                    btnBookNow.setEnabled(false);
                    minGuestError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /*private void validatePaymentNumber(){
        paymentNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String strRefNumber = paymentNumber.getText().toString();
                if (strRefNumber.isEmpty()){
                    paymentRefError.setError("Required");
                    btnBookNow.setEnabled(false);
                }
                else{
                    btnBookNow.setEnabled(true);
                    paymentRefError.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strRefNumber = paymentNumber.getText().toString();
                if(strRefNumber.matches("")) {
                    btnBookNow.setEnabled(false);
                    paymentRefError.setError("Required");
                }
            }
        });
    }*/
    private void setFirebaseToken(){
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

    private void SetCalendar() {
        //date picker
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "yyyy/MM/dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.TAIWAN);
                date.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        date.setText(year + "/" + (month+1) + "/" + dayOfMonth);
                        try {
                            Date newDate = new Date();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/M/dd hh:mm aa");
                            df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                            String getCurrentTime = String.valueOf(df.format(newDate));
                            String getSelectedDate = date.getText().toString();
                            String getSelectedTime = time.getText().toString();
                            String getDateTime = getSelectedDate +" "+ getSelectedTime;
                            Date currentTime = df.parse(getCurrentTime);
                            Date selectedTime = df.parse(getDateTime);
                            if (selectedTime.after(currentTime)){
                                btnBookNow.setEnabled(true);
                                textRequired.setVisibility(View.GONE);
                            }
                            else{
                                btnBookNow.setEnabled(false);
                                textRequired.setVisibility(View.VISIBLE);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(today);
                datePickerDialog.show();
            }
        });
        //time picker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ReservationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                Calendar calendar1 = calendar.getInstance();
                                calendar1.set(0,0,0,hour,min);
                                time.setText(DateFormat.format("hh:mm aa",calendar1));
                                try {
                                    /*set the timezone in manila*/
                                    Date newDate = new Date();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/M/dd hh:mm aa");
                                    df.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                                    /*get the current time and selected time from date and time picker*/
                                    String getCurrentTime = String.valueOf(df.format(newDate));
                                    String getSelectedDate = date.getText().toString();
                                    String getSelectedTime = time.getText().toString();
                                    String getDateTime = getSelectedDate +" "+ getSelectedTime;
                                    /*Add two hours in current time*/
                                    Date currentTime = df.parse(getCurrentTime);
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(currentTime);
                                    calendar2.add(Calendar.HOUR,2);
                                    String getCurrentTime1 = df.format(calendar2.getTime());
                                    Date estTime = df.parse(getCurrentTime1);
                                    Date selectedTime = df.parse(getDateTime);
                                    /**/
                                    if (selectedTime.after(estTime)){
                                        btnBookNow.setEnabled(true);
                                        textRequired.setVisibility(View.GONE);
                                    }
                                    else{
                                        btnBookNow.setEnabled(false);
                                        textRequired.setVisibility(View.VISIBLE);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                //displayed previous selected time
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });
    }
   /* private void cameraPermission(){
        imgPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ReservationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                }
                else{
                    requestStoragePermission();
                }
            }
        });
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                if (data != null){
                    selectedImage = data.getData();
                    if (selectedImage != null){
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                            imgPayment.setImageBitmap(bitmap);
                            imgPayment.setVisibility(View.VISIBLE);
                            btnBookNow.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        }
        else{
            paymentError.setVisibility(View.VISIBLE);
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }

    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Album requires permission to access photos");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(ReservationActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                }
            });
            alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }*/
    private void Booking() {
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerId = SharedPreference.getSharedPreference(getApplicationContext()).setID();
                String fname = SharedPreference.getSharedPreference(getApplicationContext()).setFname();
                String lname = SharedPreference.getSharedPreference(getApplicationContext()).setLname();
                String email = SharedPreference.getSharedPreference(getApplicationContext()).setEmail();
                String phoneNo = SharedPreference.getSharedPreference(getApplicationContext()).setPhoneNo();
                //String strPaymentNumber = paymentNumber.getText().toString();
                String strGuest = people.getText().toString();
                String strDate = date.getText().toString();
                String strTime = time.getText().toString();
                //String imgPayment = imageToString();
                String comments = commentSuggestion.getText().toString();
                  if (strGuest.isEmpty()){
                      guestsError.setError("Required");
                  }
                  if (strDate.isEmpty()){
                      dateError.setError("Required");
                  }
                  if (strTime.isEmpty()) {
                      timeError.setError("Required");
                  }

                 /* if (bitmap == null){
                      paymentError.setVisibility(View.VISIBLE);
                  }*/

                  else{
                      Sprite circle = new Circle();
                      progressBar.setIndeterminateDrawable(circle);
                      progressBar.setVisibility(View.VISIBLE);
                      ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                      Call<ReservationModel> callReservation = apiInterface.reservation(customerId,token,fname,lname,strGuest,email,phoneNo,strDate,strTime,comments);
                      callReservation.enqueue(new Callback<ReservationModel>() {
                          @Override
                          public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                              //startActivity(new Intent(getApplicationContext(),home_activity.class));
                              if (response.body() != null){
                                  String success = response.body().getSuccess();
                                  if (success.equals("1")){
                                      final Dialog dialog = new Dialog(context);
                                      dialog.setContentView(R.layout.book_success_dialog);
                                      Button dialogButton = (Button) dialog.findViewById(R.id.okButton);
                                      dialogButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              dialog.dismiss();
                                              startActivity(new Intent(getApplicationContext(),home_activity.class));
                                          }
                                      });
                                      dialog.show();

                                  }

                              }
                          }

                          @Override
                          public void onFailure(Call<ReservationModel> call, Throwable t) {

                          }
                      });
                  }
            }
        });
    }
    private void BottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(),MenuActivty.class));
                        overridePendingTransition(0,0);
                    case R.id.book:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return true;
            }
        });
    }

        /*Intent intent = new Intent(ReservationActivity.this,DineInActivity.class);
       intent.putExtra("guests",guests);
       intent.putExtra("reserved_date",sched_date);
        intent.putExtra("reserved_time",sched_time);
       intent.putExtra("dining_area",selDineArea);
       intent.putExtra("comments",comments);
       startActivity(intent);*/
     /*String selDineArea = spinnerDiningArea.getSelectedItem().toString();
          if (selDineArea.equals("Please select dining area")){
            diningAreaError.setVisibility(View.VISIBLE);
            bgDiningArea.setBackground(getResources().getDrawable(R.drawable.stroke_red_square));
     }*/
      /* private void setDiningArea(){
       ArrayAdapter adptrDiningArea = new ArrayAdapter(this, android.R.layout.simple_spinner_item,diningAreaList);
       adptrDiningArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerDiningArea.setAdapter(adptrDiningArea);
   }*/
    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        strDiningArea = diningAreaList[position].toString();
        /*if(strDiningArea.equals("Venue")){
            people.setFilters(new InputFilter[]{ new InputMinMax(1,155)});
        }else {
            people.setFilters(new InputFilter[]{ new InputMinMax(1,99)});
        }}@Overridepublic void onNothingSelected(AdapterView<?> adapterView) {}*/
}