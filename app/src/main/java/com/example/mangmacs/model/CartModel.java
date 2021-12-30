package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("success")
    private String success;
    @SerializedName("email")
    private String emailCart;
    @SerializedName("productCode")
    private String productCodeCart;
    @SerializedName("productName")
    private String prooductNameCart;
    @SerializedName("variation")
    private String variationCart;
    @SerializedName("fname")
    private String fnameCart;
    @SerializedName("lname")
    private String lnameCart;
    @SerializedName("price")
    private int priceCart;
    @SerializedName("quantity")
    private int quantityCart;
    @SerializedName("add_ons")
    private String add_onsCart;

    public String getSuccess() {
        return success;
    }

    public String getEmailCart() {
        return emailCart;
    }

    public String getProductCodeCart() {
        return productCodeCart;
    }

    public String getProoductNameCart() {
        return prooductNameCart;
    }

    public String getVariationCart() {
        return variationCart;
    }

    public String getFnameCart() {
        return fnameCart;
    }

    public String getLnameCart() {
        return lnameCart;
    }

    public int getPriceCart() {
        return priceCart;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public String getAdd_onsCart() {
        return add_onsCart;
    }
}
