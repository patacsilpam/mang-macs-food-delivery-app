package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class SeafoodsListModel {
    @SerializedName("productName")
    private String productNameSeafoods;
    @SerializedName("productVariation")
    private String productVariationSeafoods;
    @SerializedName("status")
    private String statusSeafoods;
    @SerializedName("productImage")
    private String imageSeafoods;
    @SerializedName("price")
    private int priceSeafoods;
    // private float rating;
    public SeafoodsListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNameSeafoods = productName;
        this.productVariationSeafoods = productVariation;
        this.statusSeafoods = status;
        this.imageSeafoods = image;
        this.priceSeafoods = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNameSeafoods;
    }
    public String getProductVariation(){
        return productVariationSeafoods;
    }
    public String getStatus(){
        return statusSeafoods;
    }
    public String getImage(){
        return imageSeafoods;
    }
    public int getPrice(){
        return priceSeafoods;
    }
}
