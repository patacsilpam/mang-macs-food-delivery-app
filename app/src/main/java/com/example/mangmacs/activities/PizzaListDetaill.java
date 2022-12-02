package com.example.mangmacs.activities;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzaListDetaill extends AppCompatActivity {
    private LinearLayout ingredientsLayout,lnrLayoutToppings,lnrCboxToppings;
    private CardView baseCardview;
    private ImageView imageView,showIngredients;
    private RelativeLayout priceLayout;
    private TextView txt_arrow_back,singleVariation,price,productCode,itemStock;
    private TextView productName,status,ingredients,email,fname,lname;
    private TextInputLayout pizza_addons;
    private EditText quantity;
    private RadioGroup variation,rdCode;
    private RadioButton radioButton;
    private Button btnPizza,btnIncrement,btnDecrement;
    private CheckBox[] cboxToppings;
    private TextView[] addOnsFee;
    private int count = 1;
    private String image,category,stockCode;
    private ArrayList<Integer> addOnsFeeList = new ArrayList<Integer>();
    private ArrayList<String> addOnsList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list_detaill);
        priceLayout = findViewById(R.id.priceLayout);
        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.pizzaproductName);
        status = findViewById(R.id.status);
        itemStock = findViewById(R.id.itemStock);
        pizza_addons = findViewById(R.id.pizzaadd_ons);
        variation = findViewById(R.id.pizzavariation);
        rdCode = findViewById(R.id.rdCode);
        price = findViewById(R.id.price);
        productCode = findViewById(R.id.productCode);
        email = findViewById(R.id.customerId);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        txt_arrow_back = findViewById(R.id.txt_arrow_back);
        btnPizza = findViewById(R.id.btnAddtoCartPizza);
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
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                quantity.setText(String.valueOf(count));
                int numQty = Integer.parseInt(quantity.getText().toString());
                int numStocks = Integer.parseInt(itemStock.getText().toString());
                if (numQty > numStocks){
                    quantity.setText(String.valueOf(numStocks));
                }

            }
        });
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
        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        String newProductname = intent.getStringExtra("productName");
        category = intent.getStringExtra("productCategory");
        int mediumStock = intent.getIntExtra("mediumStock",0);
        int largeStock = intent.getIntExtra("largeStock",0);
        String mediumItemStockCode = intent.getStringExtra("mediumItemStockCode");
        String largeItemStockCode = intent.getStringExtra("largeItemStockCode");
        String newGroupPrice = intent.getStringExtra("groupPrice");
        String newGroupVariation = intent.getStringExtra("productVariation");
        String newProductStatus = intent.getStringExtra("preparationTime");
        String newGroupCode = intent.getStringExtra("groupCode");
        String newIngredients = intent.getStringExtra("mainIngredients");
        String newGroupAddOns = intent.getStringExtra("groupAddOns");
        String newGroupAddOnsPrice = intent.getStringExtra("groupAddOnsPrice");
        String newFirstName = SharedPreference.getSharedPreference(PizzaListDetaill.this).setFname();
        String newLastName = SharedPreference.getSharedPreference(PizzaListDetaill.this).setLname();
        String newEmailAddress = SharedPreference.getSharedPreference(PizzaListDetaill.this).setEmail();
        if(intent != null){
            String[] splitPrice = newGroupPrice.split(",");
            String[] splitVariation = newGroupVariation.split(",");
            String[] splitCode = newGroupCode.split(",");
            Glide.with(PizzaListDetaill.this).load(image).into(imageView);
            productName.setText(newProductname);
            ingredients.setText(newIngredients.toLowerCase(Locale.ROOT));
            fname.setText(newFirstName);
            lname.setText(newLastName);
            email.setText(newEmailAddress);
            status.setText(newProductStatus.concat(" mins"));
            price.setText(splitPrice[0]);
            productCode.setText(splitCode[0]);
            stockCode = mediumItemStockCode;
            if (mediumStock <= 0 ){
                itemStock.setText("Out of Stock");
                itemStock.setTextColor(Color.RED);
                btnIncrement.setEnabled(false);
                btnPizza.setEnabled(false);
            }
            else{
                itemStock.setText(String.valueOf(mediumStock));
            }

            for(int i = 0; i<splitVariation.length; i++){
                // radioButton.setId(i);
                radioButton = new RadioButton(this);
                radioButton.setText(splitVariation[i]);
                radioButton.setTextAppearance(this, android.R.style.TextAppearance);
                variation.addView(radioButton);
            }
            //check variation array length
            variation.check(variation.getChildAt(0).getId());

            variation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                   int selectedVariation = variation.getCheckedRadioButtonId();
                   radioButton = findViewById(selectedVariation);
                   String variation = radioButton.getText().toString();

                   if(variation.contains("Medium")){
                       stockCode = mediumItemStockCode;
                       price.setText(splitPrice[0]);
                       productCode.setText(splitCode[0]);
                       itemStock.setText(String.valueOf(mediumStock));
                       int numStocks = Integer.parseInt(itemStock.getText().toString());
                       if (numStocks <= 0){
                           itemStock.setText("Out of Stock");
                           itemStock.setTextColor(Color.RED);
                           btnPizza.setEnabled(false);
                           btnIncrement.setEnabled(false);
                       }
                       else{
                           itemStock.setText(String.valueOf(mediumStock));
                           itemStock.setTextColor(Color.parseColor("#676767"));
                           btnPizza.setEnabled(true);
                           btnIncrement.setEnabled(true);
                       }
                   }
                   else{
                       stockCode = largeItemStockCode;
                       price.setText(splitPrice[1]);
                       productCode.setText(splitCode[1]);
                       itemStock.setText(String.valueOf(largeStock));
                       int numStocks = Integer.parseInt(itemStock.getText().toString());
                       if (numStocks <= 0){
                           itemStock.setText("Out of Stock");
                           itemStock.setTextColor(Color.RED);
                           btnPizza.setEnabled(false);
                           btnIncrement.setEnabled(false);
                       }
                       else{
                           itemStock.setText(String.valueOf(numStocks));
                       }
                   }
                }
            });
            //show additional toppings and fee
            if (newGroupAddOns == null){
                lnrLayoutToppings.setVisibility(View.GONE);
            }
            else{
                String[] splitAddOns = newGroupAddOns.split(",");
                String[] splitAddOnsPrice = newGroupAddOnsPrice.split(",");
                cboxToppings = new CheckBox[splitAddOns.length];
                addOnsFee = new TextView[splitAddOns.length];
                for(int c = 0; c<splitAddOns.length; c++){
                    cboxToppings[c] = new CheckBox(this);
                    addOnsFee[c] = new TextView(this);
                    int finalC = c;
                    //set visibility to gone on every empty array index value of addOns
                    if(splitAddOns[c].equals("")){
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
                    prmAddOnsFee.setMargins(0, -70, 0, 0);
                    addOnsFee[c].setLayoutParams(prmAddOnsFee);
                    //display to linear layout for additional toppings container
                    lnrCboxToppings.addView(cboxToppings[c]);
                    lnrCboxToppings.addView(addOnsFee[c]);

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
        }
        AddToCart();
    }

    private void AddToCart() {

        btnPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(variation.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(),"Please select one variation",Toast.LENGTH_SHORT).show();
                }
                else{
                    String email_address = email.getText().toString();
                    String code = productCode.getText().toString();
                    String product = productName.getText().toString();
                    String specialReq = pizza_addons.getEditText().getText().toString();
                    int prices = Integer.parseInt(price.getText().toString());
                    int items = Integer.parseInt(quantity.getText().toString());
                    String addOns = String.valueOf(addOnsList).replace("[","").replace("]","");
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    int selectedSize = variation.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedSize);
                    String getVariation = radioButton.getText().toString();
                    String preparedTime = status.getText().toString();
                    int addOnsTotFee=0;
                    for (Integer tpgFeeList : addOnsFeeList){
                        addOnsTotFee += tpgFeeList;
                    }
                    addOnsTotFee *= items;
                    ApiInterface apiComboInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    Call<CartModel> cartModelCall = apiComboInterface.addcart(email_address,code,stockCode,product,category,getVariation,firstName,lastName,prices,items,addOns,addOnsTotFee,specialReq,image,preparedTime);
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
            }
        });
    }

    private void Back() {
        txt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PizzaListDetaill.this,PizzaActivity.class));
            }
        });
    }
}