package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class DimsumListModel {
    @SerializedName("productName")
    private String productNameDimsum;
    @SerializedName("productVariation")
    private String productVariationDimsum;
    @SerializedName("status")
    private String statusDimsum;
    @SerializedName("productImage")
    private String imageDimsum;
    @SerializedName("price")
    private int priceDimsum;
    // private float rating;
    public DimsumListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNameDimsum = productName;
        this.productVariationDimsum = productVariation;
        this.statusDimsum = status;
        this.imageDimsum = image;
        this.priceDimsum = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNameDimsum;
    }
    public String getProductVariation(){
        return productVariationDimsum;
    }
    public String getStatus(){
        return statusDimsum;
    }
    public String getImage(){
        return imageDimsum;
    }
    public int getPrice(){
        return priceDimsum;
    }
}
