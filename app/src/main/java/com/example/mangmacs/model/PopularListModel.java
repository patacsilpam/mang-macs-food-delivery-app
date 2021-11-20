package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PopularListModel {
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
    public PopularListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNamePopular = productName;
        this.productVariationPopular = productVariation;
        this.statusPopular = status;
        this.imagePopular = image;
        this.pricePopular = price;
        //this.rating = rating;
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
