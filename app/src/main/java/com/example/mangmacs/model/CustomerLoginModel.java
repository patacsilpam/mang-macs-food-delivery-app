package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CustomerLoginModel {
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("email_address")
    private String email_address;
    @SerializedName("gender")
    private String gender;
    @SerializedName("customer_id")
    private String customerID;

    public CustomerLoginModel(String success, String message, String fname,String lname, String email_address,String gender,String customerID) {
        this.success = success;
        this.message = message;
        this.fname = fname;
        this.lname = lname;
        this.email_address = email_address;
        this.gender = gender;
        this.customerID = customerID;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getGender() {
        return gender;
    }

    public String getCustomerID() {
        return customerID;
    }
}
