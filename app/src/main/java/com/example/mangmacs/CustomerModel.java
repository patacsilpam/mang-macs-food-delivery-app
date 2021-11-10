package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    @SerializedName("fname")
    private String fname;
   @SerializedName("lname")
    private String lname;
   @SerializedName("email_address")
    private String email;
    @SerializedName("user_password")
    private String password;
   @SerializedName("contact")
    private String contact;
   @SerializedName("success")
    private String success;
   @SerializedName("message")
    private String message;
   @SerializedName("login")
   private String response;
    public CustomerModel(String fname, String lname,String email,String contact,String password,String success,String message,String response) {
        //this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.success = success;
        this.message = message;
        this.response = response;
    }

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
