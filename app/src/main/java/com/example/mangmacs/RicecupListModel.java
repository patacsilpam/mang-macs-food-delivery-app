package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class RicecupListModel {
    @SerializedName("productName")
    private String productNameRice;
    @SerializedName("productVariation")
    private String productVariationRice;
    @SerializedName("status")
    private String statusRice;
    @SerializedName("productImage")
    private String imageRice;
    @SerializedName("price")
    private int priceRice;
    // private float rating;
    public RicecupListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNameRice = productName;
        this.productVariationRice = productVariation;
        this.statusRice = status;
        this.imageRice = image;
        this.priceRice = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNameRice;
    }
    public String getProductVariation(){
        return productVariationRice;
    }
    public String getStatus(){
        return statusRice;
    }
    public String getImage(){
        return imageRice;
    }
    public int getPrice(){
        return priceRice;
    }
}
