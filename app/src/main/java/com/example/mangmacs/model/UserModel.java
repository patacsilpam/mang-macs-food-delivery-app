package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    private int id;
    private String fname;
    private String email_address;

    public UserModel(String fname, String email_address) {
       // this.id = id;
        this.fname = fname;
        this.email_address = email_address;
    }


    public int getId() {
        return id;
    }
    public String getFname() {
        return fname;
    }
    public String getEmail_address() {
        return email_address;
    }

  
}
