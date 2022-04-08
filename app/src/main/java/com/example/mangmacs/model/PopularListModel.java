package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PopularListModel {
    @SerializedName("code")
    private String productCodePopular;
    @SerializedName("productName")
    private String productNamePopular;
    @SerializedName("productVariation")
    private String productVariationPopular;
    @SerializedName("status")
    private String statusPopular;
    @SerializedName("productImage")
    private String imagePopular;
    @SerializedName("price")
    private int pricePopular;
    // private float rating;
    public PopularListModel(String productCodePopular,String productName,String productVariation,String status,String image,int price){
        this.productNamePopular = productName;
        this.productVariationPopular = productVariation;
        this.statusPopular = status;
        this.imagePopular = image;
        this.pricePopular = price;
        this.productCodePopular = productCodePopular;
    }
    public String getProductCodePopular() {
        return productCodePopular;
    }
    public String getProductName(){
        return productNamePopular;
    }
    public String getProductVariation(){
        return productVariationPopular;
    }
    public String getStatus(){
        return statusPopular;
    }
    public String getImage(){
        return imagePopular;
    }
    public int getPrice(){
        return pricePopular;
    }
}
