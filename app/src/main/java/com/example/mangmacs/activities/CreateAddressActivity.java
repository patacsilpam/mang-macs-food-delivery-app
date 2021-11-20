package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.mangmacs.R;

public class CreateAddressActivity extends AppCompatActivity {
    private EditText province,city,phoneNo;
    private Spinner spinnerUrdaneta;
    private   RadioGroup radioGroup;
    RadioButton radioButton;
    private Button btnCreateAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);
        province = findViewById(R.id.province);
        city = findViewById(R.id.city);
        spinnerUrdaneta = findViewById(R.id.barangay);
       // radioGroup = findViewById(R.id.radioGroup);
        btnCreateAddress = findViewById(R.id.createAddress);
        //list of urdaneta spinner
        ArrayAdapter<CharSequence> ad_urdaneta = ArrayAdapter.createFromResource(this,R.array.urdaneta, android.R.layout.simple_spinner_item);
        ad_urdaneta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrdaneta.setAdapter(ad_urdaneta);
    }
}