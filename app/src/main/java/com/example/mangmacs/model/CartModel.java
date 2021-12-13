package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("success")
    private String success;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;

    public CartModel(String success, String customerId, String fname, String lname) {
        this.success = success;
        this.customerId = customerId;
        this.fname = fname;
        this.lname = lname;
    }

    public String getSuccess() {
        return success;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
