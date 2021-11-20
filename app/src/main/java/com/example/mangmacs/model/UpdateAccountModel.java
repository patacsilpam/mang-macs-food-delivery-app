package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class UpdateAccountModel {
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("email_address")
    private String email;
    @SerializedName("birthdate")
    private String birthdate;
    @SerializedName("gender")
    private String gender;

    public UpdateAccountModel(String success,String message,String fname, String lname, String email,String birthdate, String gender) {
        this.success = success;
        this.message = message;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }
}
