package com.example.mangmacs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String PREF_NAME = "user_details";
    public static final String CUSTOMER_ID="customerId";
    public static final String FIRST_NAME = "fname";
    public static final String EMAIL = "email";
    public static final String LNAME = "lname";
    public static final String TOKEN = "token";
    public static final String PREP_TIME = "phoneNumber";
    public static final String PHONE_NO = "phone_no";
    public static SharedPreference sharedPreference;
    public static Context context;
    public SharedPreference(Context context1){
       context = context1;
    }
    public static  synchronized SharedPreference getSharedPreference(Context context){
        if (sharedPreference == null){
            sharedPreference = new SharedPreference(context);
        }
        return sharedPreference;
    }
    public void storeToken(String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN,token);
        editor.apply();
    }
    //store first name of the user
    public void storeFname(String fname){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_NAME,fname);
        editor.apply();
    }

    //store email of the user
    public void storeEmail(String email){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL,email);
        editor.apply();
    }
    public void storePhoneNo(String phoneNo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_NO,phoneNo);
        editor.apply();
    }
    public String setToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN,null);
    }
    //display first name of the user
    public String setFname(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIRST_NAME,null);
    }
    //display email of the user
    public String setEmail(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(EMAIL,null);
    }
    //store lname
    public void storeLname(String lname){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LNAME,lname);
        editor.apply();
    }
    //display lname of the user
    public String setLname(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(LNAME,null);
    }
    //store customerID
    public void storeID(String customerID){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUSTOMER_ID,customerID);
        editor.apply();
    }
    //display customerID of the user
    public String setID(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(CUSTOMER_ID,null);
    }
    //set total price
    public String setPhoneNo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_NO,null);
    }
    //check if the user already logged in
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL,null) != null;
    }
    public void prepTime(String prepTime){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREP_TIME,prepTime);
        editor.apply();
    }
    public String setPrepTime(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREP_TIME,null);
    }
    public void logout(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
