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
    @SerializedName("code")
    private String codeSeafoods;
    public SeafoodsListModel(String productName,String productVariation,String status,String image,int price, String codeSeafoods){
        this.productNameSeafoods = productName;
        this.productVariationSeafoods = productVariation;
        this.statusSeafoods = status;
        this.imageSeafoods = image;
        this.priceSeafoods = price;
        this.codeSeafoods = codeSeafoods;
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

    public String getCodeSeafoods() {
        return codeSeafoods;
    }
}
