package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class ComboMealListModel {
    @SerializedName("productName")
    private String productNameCombo;
    @SerializedName("productVariation")
    private String productVariationCombo;
    @SerializedName("status")
    private String statusCombo;
    @SerializedName("productImage")
    private String imageCombo;
    @SerializedName("price")
    private int priceCombo;
    @SerializedName("code")
    private String codeCombo;
    // private float rating;
    public ComboMealListModel(String productName,String productVariation,String status,String image,int price,  String codeCombo){
        this.productNameCombo = productName;
        this.productVariationCombo = productVariation;
        this.statusCombo = status;
        this.imageCombo = image;
        this.priceCombo = price;
        this.codeCombo = codeCombo;
    }
    public String getProductName(){
        return productNameCombo;
    }
    public String getProductVariation(){
        return productVariationCombo;
    }
    public String getStatus(){
        return statusCombo;
    }
    public String getImage(){
        return imageCombo;
    }
    public int getPrice(){
        return priceCombo;
    }

    public String getCodeCombo() {
        return codeCombo;
    }
}
