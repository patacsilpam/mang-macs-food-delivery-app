package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class NoodlesListModel {
    @SerializedName("productName")
    private String productNameNoodles;
    @SerializedName("productVariation")
    private String productVariationNoodles;
    @SerializedName("status")
    private String statusNoodles;
    @SerializedName("productImage")
    private String imageNoodles;
    @SerializedName("price")
    private int priceNoodles;
    @SerializedName("code")
    private String codeNoodles;
    public NoodlesListModel(String productName,String productVariation,String status,String image,int price,  String codeNoodles){
        this.productNameNoodles = productName;
        this.productVariationNoodles = productVariation;
        this.statusNoodles = status;
        this.imageNoodles = image;
        this.priceNoodles = price;
        this.codeNoodles = codeNoodles;
    }
    public String getProductName(){
        return productNameNoodles;
    }
    public String getProductVariation(){
        return productVariationNoodles;
    }
    public String getStatus(){
        return statusNoodles;
    }
    public String getImage(){
        return imageNoodles;
    }
    public int getPrice(){
        return priceNoodles;
    }

    public String getCodeNoodles() {
        return codeNoodles;
    }
}
