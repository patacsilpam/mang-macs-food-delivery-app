package com.example.mangmacs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String PREF_NAME = "user_details";
    public static final String FIRST_NAME = "fname";
    public static final String ID = "id";
    public static final String EMAIL = "email";
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
    public void storeUser(String email){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL,email);
        //editor.putString(EMAIL,email);
        editor.apply();

    }

    public boolean isLoggedIn(){
           SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
          return sharedPreferences.getString(EMAIL,null) != null;
    }
    public String LoggedInUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL,null);
    }
    public String UserProfile(){
        SharedPreferences userProfile = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return  userProfile.getString(ID,null);
    }
    public void logout(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context,sign_up_activity.class));
    }
}
