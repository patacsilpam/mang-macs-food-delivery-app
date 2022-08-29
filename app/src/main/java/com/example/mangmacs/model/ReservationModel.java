package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class ReservationModel {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("guests")
    private String guests;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("scheduled_date")
    private String scheduled_date;
    @SerializedName("scheduled_time")
    private String scheduled_time;
    @SerializedName("refNumber")
    private String refNumber;
    @SerializedName("notif_date")
    private String notifDate;
    @SerializedName("payment_photo")
    private String paymentPhoto;
    @SerializedName("success")
    private String success;
    //table order details list
    @SerializedName("product_name")
    private String products;
    @SerializedName("productCategory")
    private String category;
    @SerializedName("price")
    private String price;
    @SerializedName("product_variation")
    private String variations;
    @SerializedName("quantity")
    private String quantities;
    @SerializedName("add_ons")
    private String addOns;
    @SerializedName("status")
    private String status;
    @SerializedName("product_image")
    private String imgProduct;
    @SerializedName("completed_time")
    private String completedTime;
    @SerializedName("totalAmount")
    private String totalAmount;

    public ReservationModel(String id, String email, String fname, String lname, String guests, String createdAt, String scheduled_date, String scheduled_time, String refNumber, String notifDate, String paymentPhoto, String success, String products, String category, String price, String variations, String quantities, String addOns, String status, String imgProduct, String completedTime,String totalAmount) {
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.guests = guests;
        this.createdAt = createdAt;
        this.scheduled_date = scheduled_date;
        this.scheduled_time = scheduled_time;
        this.refNumber = refNumber;
        this.notifDate = notifDate;
        this.paymentPhoto = paymentPhoto;
        this.success = success;
        this.products = products;
        this.price = price;
        this.category = category;
        this.variations = variations;
        this.quantities = quantities;
        this.addOns = addOns;
        this.status = status;
        this.imgProduct = imgProduct;
        this.completedTime = completedTime;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getGuests() {
        return guests;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getScheduled_date() {
        return scheduled_date;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public String getNotifDate() {
        return notifDate;
    }

    public String getPaymentPhoto() {
        return paymentPhoto;
    }

    public String getSuccess() {
        return success;
    }

    public String getProducts() {
        return products;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getVariations() {
        return variations;
    }

    public String getQuantities() {
        return quantities;
    }

    public String getAddOns() {
        return addOns;
    }

    public String getStatus() {
        return status;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }
}
