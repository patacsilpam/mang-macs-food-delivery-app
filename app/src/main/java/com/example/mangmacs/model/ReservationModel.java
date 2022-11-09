package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class ReservationModel {
    @SerializedName("success")
    private String success;
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
    //table order details list
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("product_name")
    private String products;
    @SerializedName("product_category")
    private String category;
    @SerializedName("price")
    private String price;
    @SerializedName("product_variation")
    private String variations;
    @SerializedName("quantity")
    private String quantities;
    @SerializedName("add_ons")
    private String addOns;
    @SerializedName("add_ons_fee")
    private String addOnsFee;
    @SerializedName("special_request")
    private String specialRequest;
    @SerializedName("status")
    private String status;
    @SerializedName("product_image")
    private String imgProduct;
    @SerializedName("completed_time")
    private String completedTime;
    @SerializedName("totalAmount")
    private String totalAmount;
    @SerializedName("preparation_time")
    private String preparationTime;

    public ReservationModel(String id, String email, String fname, String lname, String guests, String createdAt, String scheduled_date, String scheduled_time, String refNumber, String notifDate, String paymentPhoto, String success, String productCode, String products, String category, String price, String variations, String quantities, String addOns, String addOnsFee,String specialRequest,String status, String imgProduct, String completedTime, String totalAmount, String preparationTime) {
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
        this.productCode = productCode;
        this.products = products;
        this.category = category;
        this.price = price;
        this.variations = variations;
        this.quantities = quantities;
        this.addOns = addOns;
        this.addOnsFee = addOnsFee;
        this.specialRequest = specialRequest;
        this.status = status;
        this.imgProduct = imgProduct;
        this.completedTime = completedTime;
        this.totalAmount = totalAmount;
        this.preparationTime = preparationTime;
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

    public String getProductCode() {
        return productCode;
    }

    public String getProducts() {
        return products;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
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

    public String getAddOnsFee() {
        return addOnsFee;
    }

    public String getSpecialRequest() {
        return specialRequest;
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

    public String getPreparationTime() {
        return preparationTime;
    }
}
