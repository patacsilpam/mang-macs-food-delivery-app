package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class SoupListModel {
    @SerializedName("productName")
    private String productNameSoup;
    @SerializedName("productVariation")
    private String productVariationSoup;
    @SerializedName("status")
    private String statusSoup;
    @SerializedName("productImage")
    private String imageSoup;
    @SerializedName("price")
    private int priceSoup;
    // private float rating;
    public SoupListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNameSoup = productName;
        this.productVariationSoup = productVariation;
        this.statusSoup = status;
        this.imageSoup = image;
        this.priceSoup = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNameSoup;
    }
    public String getProductVariation(){
        return productVariationSoup;
    }
    public String getStatus(){
        return statusSoup;
    }
    public String getImage(){
        return imageSoup;
    }
    public int getPrice(){
        return priceSoup;
    }
}
