package com.example.mangmacs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mangmacs.model.UserModel;

public class SharedPreference {
    public static final String PREF_NAME = "user_details";
    public static final String FIRST_NAME = "fname";
    public static final String EMAIL = "email";
    public static final String LNAME = "lname";
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

    //store first name of the user
    public void storeFname(String fname){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_NAME,fname);
        editor.apply();
    }
    //display first name of the user
    public String setFname(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIRST_NAME,null);
    }
    //store email of the user
    public void storeEmail(String email){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL,email);
        editor.apply();
    }
    //display email of the user
    public String setEmail(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(EMAIL,null);
    }
    //store gender
    public void storeLname(String lname){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LNAME,lname);
        editor.apply();
    }
    //display gender of the user
    public String setLname(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(LNAME,null);
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL,null) != null;
    }

    public void logout(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
