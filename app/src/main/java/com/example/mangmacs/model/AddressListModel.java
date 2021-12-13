package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class AddressListModel {
    @SerializedName("id")
    private int id;
    @SerializedName("success")
    private String success;
    @SerializedName("customer_id")
    private String customerID;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("street")
    private String street;
    @SerializedName("barangay")
    private String barangay;
    @SerializedName("city")
    private String city;
    @SerializedName("province")
    private String province;
    @SerializedName("label_address")
    private String labelAddress;
    @SerializedName("phone_no")
    private String phoneNumber;

    public AddressListModel(int id,String success, String customerID, String fullname, String email, String street, String barangay, String city, String province, String labelAddress, String phoneNumber) {
        this.id = id;
        this.success = success;
        this.customerID = customerID;
        this.fullname = fullname;
        this.email = email;
        this.street = street;
        this.barangay = barangay;
        this.city = city;
        this.province = province;
        this.labelAddress = labelAddress;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getSuccess() {
        return success;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getLabelAddress() {
        return labelAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
