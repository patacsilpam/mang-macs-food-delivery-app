package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilaoListDetail extends AppCompatActivity {
    private ImageView imageView;
    private TextView txt_arrow_back;
    private TextView productName,status,customerId,fname,lname;
    private TextInputLayout bilaoAddOns;
    private Button btnAddtoCart;
    private RadioButton sevenToTen,tenToFifteen,fifteenToTwenty;
    private RadioGroup rdVariation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilao_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.bilaoproductName);
        bilaoAddOns = findViewById(R.id.bilaoadd_ons);
        status = findViewById(R.id.status);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        rdVariation = findViewById(R.id.bilaovariation);
        sevenToTen = findViewById(R.id.sevenToTen);
        tenToFifteen = findViewById(R.id.tenToFifteen);
        fifteenToTwenty = findViewById(R.id.fifteenToTwenty);
        btnAddtoCart = findViewById(R.id.btnBilao);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        String productprice = intent.getStringExtra("groupPriceBilao");
        String productvariation = intent.getStringExtra("productVariationBilao");
        String productstatus = intent.getStringExtra("status");
        String groupCode = intent.getStringExtra("groupCode");
        if(intent != null){
            Glide.with(BilaoListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            status.setText(productstatus);
            String[] splitPrice = productprice.split(",");
            String[] splitVariation = productvariation.split(",");
            String[] splitCode = groupCode.split(",");
            String product1 = splitVariation[0]+"          ₱ "+splitPrice[0]+".00 "+splitCode[0];
            SpannableString spannableCode1 = new SpannableString(product1);
            ForegroundColorSpan fcsWhite1 = new ForegroundColorSpan(Color.WHITE);
            spannableCode1.setSpan(fcsWhite1,30,40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sevenToTen.setText(spannableCode1);

            String product2 = splitVariation[1]+"          ₱ "+splitPrice[1]+".00 "+splitCode[1];
            SpannableString spannableCode2 = new SpannableString(product2);
            ForegroundColorSpan fcsWhite2 = new ForegroundColorSpan(Color.WHITE);
            spannableCode2.setSpan(fcsWhite2,30,41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tenToFifteen.setText(spannableCode2);

            String product3 = splitVariation[2]+"          ₱ "+splitPrice[2]+".00 "+splitCode[2];
            SpannableString spannableCode3 = new SpannableString(product3);
            ForegroundColorSpan fcsWhite3 = new ForegroundColorSpan(Color.WHITE);
            spannableCode3.setSpan(fcsWhite3,30,41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            fifteenToTwenty.setText(spannableCode3);

            String customerID = SharedPreference.getSharedPreference(BilaoListDetail.this).setEmail();
            String firstname = SharedPreference.getSharedPreference(BilaoListDetail.this).setFname();
            String lastname = SharedPreference.getSharedPreference(BilaoListDetail.this).setLname();
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
        }
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = customerId.getText().toString();
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                String product = productName.getText().toString();
                String add_ons = bilaoAddOns.getEditText().getText().toString();
                int selectedSize = rdVariation.getCheckedRadioButtonId();
                RadioButton size = findViewById(selectedSize);
                String getSize = size.getText().toString();
                String getCode = getSize.replace("7-10 Person","  ");
                String variation = getSize.replace("         ₱"," ");
                int price = 0;

                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,getCode,product,variation,firstName,lastName,price,add_ons);
                cartModelCall.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success =response.body().getSuccess();
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Added to cart Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });
        //a back button
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BilaoListDetail.this,BilaoActivity.class));
            }
        });
    }
}