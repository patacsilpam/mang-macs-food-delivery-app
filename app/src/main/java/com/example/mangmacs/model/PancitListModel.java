package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PancitListModel {
    @SerializedName("productName")
    private String productNamePancit;
    @SerializedName("productVariation")
    private String productVariationPancit;
    @SerializedName("status")
    private String statusPancit;
    @SerializedName("productImage")
    private String imagePancit;
    @SerializedName("price")
    private int pricePancit;
    // private float rating;
    public PancitListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNamePancit = productName;
        this.productVariationPancit = productVariation;
        this.statusPancit = status;
        this.imagePancit = image;
        this.pricePancit = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNamePancit;
    }
    public String getProductVariation(){
        return productVariationPancit;
    }
    public String getStatus(){
        return statusPancit;
    }
    public String getImage(){
        return imagePancit;
    }
    public int getPrice(){
        return pricePancit;
    }
}
