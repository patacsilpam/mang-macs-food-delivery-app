package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SizzlingListDetail extends AppCompatActivity {
    private LinearLayout ingredientsLayout,lnrLayoutToppings,lnrCboxToppings;
    private CardView baseCardview;
    private ImageView imageView,showIngredients;
    private TextView txt_arrow_back;
    private TextView productName,productPrice,ingredients,status,customerId,fname,lname;
    private TextInputLayout drinksAddons;
    private EditText quantity;
    private Button btnAddtoCart,btnIncrement,btnDecrement;
    private Intent intent;
    private String image,category;
    private CheckBox[] cboxToppings;
    private TextView[] addOnsFee;
    private int count = 1;
    private ArrayList<Integer> addOnsFeeList = new ArrayList<Integer>();
    private ArrayList<String> addOnsList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sizzling_list_detail);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.seafoodsproductName);
        productPrice = findViewById(R.id.seafoodsproductPrice);
        drinksAddons = findViewById(R.id.seafoodsadd_ons);
        status = findViewById(R.id.status);
        customerId = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        btnAddtoCart = findViewById(R.id.btnSeafoods);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        quantity = findViewById(R.id.quantity);
        ingredients = findViewById(R.id.ingredients);
        showIngredients = findViewById(R.id.showIngredients);
        ingredientsLayout = findViewById(R.id.ingredientLayout);
        lnrLayoutToppings = findViewById(R.id.lnrLayoutToppings);
        lnrCboxToppings = findViewById(R.id.lnrCboxToppings);
        baseCardview = findViewById(R.id.baseCardview);
        btnIncrement = findViewById(R.id.increment);
        btnDecrement = findViewById(R.id.decrement);
        btnDecrement.setEnabled(false); //set button decrement not clickable
        ShowIngredients();
        IncrementDecrement();
        DisplayProductDetails();
        Back();
    }
    private void ShowIngredients(){
        showIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingredientsLayout.getVisibility() == View.GONE){
                    ingredientsLayout.setVisibility(View.VISIBLE);
                    showIngredients.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else{
                    ingredientsLayout.setVisibility(View.GONE);
                    showIngredients.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });
    }
    private void IncrementDecrement() {
        //button increment
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                quantity.setText(String.valueOf(count));
            }
        });
        //button decrement
        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                quantity.setText(String.valueOf(count));
            }
        });
        //disable button decrement to edit quantity if it is equal to one
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number = quantity.getText().toString();
                if(number.equals("1")){
                    btnDecrement.setEnabled(false);
                }else{
                    btnDecrement.setEnabled(true);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number = quantity.getText().toString();
                if(number.equals("1")){
                    btnDecrement.setEnabled(false);
                }else{
                    btnDecrement.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    private void DisplayProductDetails() {
        //get the value from its adapter
        intent = getIntent();
        image = intent.getStringExtra("image");
        String productname = intent.getStringExtra("productName");
        category = intent.getStringExtra("productCategory");
        int productprice = intent.getIntExtra("price",0);
        String productstatus = intent.getStringExtra("preparationTime");
        String newIngredients =intent.getStringExtra("mainIngredients");
        String newGroupAddOns = intent.getStringExtra("groupAddOns");
        String newGroupAddOnsFee = intent.getStringExtra("groupAddOnsFee");
        String firstname = SharedPreference.getSharedPreference(SizzlingListDetail.this).setFname();
        String lastname = SharedPreference.getSharedPreference(SizzlingListDetail.this).setLname();
        String customerID = SharedPreference.getSharedPreference(SizzlingListDetail.this).setEmail();

        if(intent != null){
            Glide.with(SizzlingListDetail.this).load(image).into(imageView);
            productName.setText(productname);
            productPrice.setText(Integer.toString(productprice));
            ingredients.setText(newIngredients.toLowerCase(Locale.ROOT));
            status.setText(productstatus.concat(" min"));
            customerId.setText(customerID);
            fname.setText(firstname);
            lname.setText(lastname);
        }
        //show additional toppings and fee
        if (newGroupAddOns == null){
            lnrLayoutToppings.setVisibility(View.GONE);
        }
        else{
            String[] splitAddOns = newGroupAddOns.split(",");
            String[] splitAddOnsPrice = newGroupAddOnsFee.split(",");
            cboxToppings = new CheckBox[splitAddOns.length];
            addOnsFee = new TextView[splitAddOns.length];
            for (int c = 0; c<splitAddOns.length; c++){
                //create checkbox for toppings and textview for addonsfee
                cboxToppings[c] = new CheckBox(this);
                addOnsFee[c] = new TextView(this);
                int finalC = c;
                //set visibility to gone on every empty array index value of addOns
                if (splitAddOns[c].equals("")){
                    cboxToppings[c].setVisibility(View.GONE);
                    addOnsFee[c].setVisibility(View.GONE);
                }
                else{
                    cboxToppings[c].setText(splitAddOns[c]);
                    addOnsFee[c].setText("+ ₱" + splitAddOnsPrice[c] + ".00");
                    addOnsFee[c].setGravity(Gravity.RIGHT);
                    addOnsFee[c].setTextColor(Color.parseColor("#28292b"));
                    addOnsFee[c].setTextSize(14);
                }
                //set layout width,height and margin textview for addOnsFee
                LinearLayout.LayoutParams prmAddOnsFee = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                prmAddOnsFee.setMargins(0,-70,0,0);
                addOnsFee[c].setLayoutParams(prmAddOnsFee);
                //display to linear layout for additional toppings container
                lnrLayoutToppings.addView(cboxToppings[c]);
                lnrLayoutToppings.addView(addOnsFee[c]);

                //add or remove toppings and fee to arraylist of addOnsList  and addOnsFeeList variable
                cboxToppings[c].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (cboxToppings[finalC].isChecked()){
                            addOnsList.add(splitAddOns[finalC]);
                            addOnsFeeList.add(Integer.valueOf(splitAddOnsPrice[finalC]));

                        }
                        else{
                            addOnsList.remove(splitAddOns[finalC]);
                            addOnsFeeList.remove(Integer.valueOf(splitAddOnsPrice[finalC]));
                        }
                    }
                });
            }
        }
        AddToCart();
    }

    private void AddToCart() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = customerId.getText().toString();
                String code = intent.getStringExtra("code");
                String product = productName.getText().toString();
                String variation = "";
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                int price = Integer.parseInt(productPrice.getText().toString());
                int items = Integer.parseInt(quantity.getText().toString());
                String specialReq = drinksAddons.getEditText().getText().toString();
                String preparedTime = status.getText().toString();
                String addOns = String.valueOf(addOnsList).replace("[","").replace("]","");
                int addOnsTotFee=0;
                for (Integer tpgFeeList : addOnsFeeList){
                    addOnsTotFee += tpgFeeList;
                }
                addOnsTotFee *= items;
                ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                Call<CartModel> cartModelCall = apiComboInterface.addcart(id,code,product,category,variation,firstName,lastName,price,items,addOns,addOnsTotFee,specialReq,image,preparedTime);
                cartModelCall.enqueue(new Callback<CartModel>() {
                    @Override
                    public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                        if(response.body() != null){
                            String success =response.body().getSuccess();
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),home_activity.class));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartModel> call, Throwable t) {

                    }
                });
            }
        });
    }
    private void Back() {
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SizzlingListDetail.this, SizzlingActivity.class));
            }
        });
    }
}