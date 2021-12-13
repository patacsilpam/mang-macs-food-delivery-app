package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PancitBilaoListModel {
    @SerializedName("productName")
    private String productNameBilao;
    @SerializedName("productVariationBilao")
    private String productVariationBilao;
    @SerializedName("status")
    private String statusBilao;
    @SerializedName("productImage")
    private String imageBilao;
    @SerializedName("price")
    private int priceBilao;
    @SerializedName("groupPriceBilao")
    private String groupPriceBilao;
    @SerializedName("groupCode")
    private String groupCode;
    // private float rating;
    public PancitBilaoListModel(String productName,String productVariation,String status,String image,int price,String groupPriceBilao,  String groupCode){
        this.productNameBilao = productName;
        this.productVariationBilao = productVariation;
        this.statusBilao = status;
        this.imageBilao = image;
        this.priceBilao = price;
        this.groupPriceBilao = groupPriceBilao;
        this.groupCode = groupCode;
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

    public String getGroupPriceBilao() {
        return groupPriceBilao;
    }

    public String getGroupCode() {
        return groupCode;
    }
}
