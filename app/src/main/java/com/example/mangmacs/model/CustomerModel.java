package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {

   @SerializedName("success")
    private String success;
   @SerializedName("message")
    private String message;

    public CustomerModel(String success, String message) {
        this.success = success;
        this.message = message;
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
