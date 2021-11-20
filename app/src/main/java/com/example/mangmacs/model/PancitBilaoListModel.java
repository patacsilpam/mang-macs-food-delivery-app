package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PancitBilaoListModel {
    @SerializedName("productName")
    private String productNameBilao;
    @SerializedName("productVariation")
    private String productVariationBilao;
    @SerializedName("status")
    private String statusBilao;
    @SerializedName("productImage")
    private String imageBilao;
    @SerializedName("price")
    private int priceBilao;
    // private float rating;
    public PancitBilaoListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNameBilao = productName;
        this.productVariationBilao = productVariation;
        this.statusBilao = status;
        this.imageBilao = image;
        this.priceBilao = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNameBilao;
    }
    public String getProductVariation(){
        return productVariationBilao;
    }
    public String getStatus(){
        return statusBilao;
    }
    public String getImage(){
        return imageBilao;
    }
    public int getPrice(){
        return priceBilao;
    }
}
