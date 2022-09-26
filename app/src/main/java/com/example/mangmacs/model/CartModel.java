package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("success")
    private String success;
    @SerializedName("email")
    private String emailCart;
    @SerializedName("id")
    private int productId;
    @SerializedName("productCode")
    private String productCodeCart;
    @SerializedName("productName")
    private String prooductNameCart;
    @SerializedName("productCategory")
    private String productCategory;
    @SerializedName("add_ons")
    private String addOns;
    @SerializedName("add_ons_fee")
    private int addOnsFee;
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
    @SerializedName("totalprice")
    private int totalprice;
    @SerializedName("deliveryChange")
    private int deliveryChange;
    @SerializedName("special_request")
    private String specialRequest;
    @SerializedName("totalCart")
    private String totalcart;
    @SerializedName("imageProduct")
    private String imageProduct;
    @SerializedName("preparation_time")
    private String preparedTime;

    public CartModel(String success, String emailCart, int productId, String productCodeCart,
                     String prooductNameCart, String productCategory,String addOns,int addOnsFee,
                     String variationCart, String fnameCart, String lnameCart, int priceCart,
                     int quantityCart, int totalprice, int deliveryChange, String specialRequest,
                     String totalcart, String imageProduct,String preparedTime) {
        this.success = success;
        this.emailCart = emailCart;
        this.productId = productId;
        this.productCodeCart = productCodeCart;
        this.prooductNameCart = prooductNameCart;
        this.productCategory = productCategory;
        this.addOns = addOns;
        this.addOnsFee = addOnsFee;
        this.variationCart = variationCart;
        this.fnameCart = fnameCart;
        this.lnameCart = lnameCart;
        this.priceCart = priceCart;
        this.quantityCart = quantityCart;
        this.totalprice = totalprice;
        this.deliveryChange = deliveryChange;
        this.specialRequest = specialRequest;
        this.totalcart = totalcart;
        this.imageProduct = imageProduct;
        this.preparedTime = preparedTime;
    }

    public String getSuccess() {
        return success;
    }

    public String getEmailCart() {
        return emailCart;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductCodeCart() {
        return productCodeCart;
    }

    public String getProoductNameCart() {
        return prooductNameCart;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getAddOns() {
        return addOns;
    }

    public int getAddOnsFee() {
        return addOnsFee;
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

    public int getTotalprice() {
        return totalprice;
    }

    public int getDeliveryChange() {
        return deliveryChange;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public String getTotalcart() {
        return totalcart;
    }

    public String getImageProduct() {
        return imageProduct;
    }

   public String getPreparedTime() {
        return preparedTime;
    }
}
