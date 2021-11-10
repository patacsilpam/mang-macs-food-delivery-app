package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class CustomerLoginModel {
    @SerializedName("email_address")
    private String email_address;
    @SerializedName("user_password")
    private String user_password;
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;

    public CustomerLoginModel(String email_address, String user_password, String success, String message) {
        this.email_address = email_address;
        this.user_password = user_password;
        this.success = success;
        this.message = message;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
