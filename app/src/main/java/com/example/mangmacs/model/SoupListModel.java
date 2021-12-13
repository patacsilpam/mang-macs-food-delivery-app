package com.example.mangmacs.model;

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
    @SerializedName("code")
    private String codeSoup;
    public SoupListModel(String productName,String productVariation,String status,String image,int price, String codeSoup){
        this.productNameSoup = productName;
        this.productVariationSoup = productVariation;
        this.statusSoup = status;
        this.imageSoup = image;
        this.priceSoup = price;
        this.codeSoup = codeSoup;
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

    public String getCodeSoup() {
        return codeSoup;
    }
}
