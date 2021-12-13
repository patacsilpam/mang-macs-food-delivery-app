package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class MealsGoodListModel{
    @SerializedName("productName")
    private String productNameMealsGood;
    @SerializedName("productVariation")
    private String productVariationMealsGood;
    @SerializedName("status")
    private String statusMealsGood;
    @SerializedName("productImage")
    private String imageMealsGood;
    @SerializedName("price")
    private int priceMealsGood;
    @SerializedName("code")
    private String codeMealsGood;
    public MealsGoodListModel(String productName,String productVariation,String status,String image,int price,  String codeMealsGood){
        this.productNameMealsGood = productName;
        this.productVariationMealsGood = productVariation;
        this.statusMealsGood = status;
        this.imageMealsGood = image;
        this.priceMealsGood = price;
        this.codeMealsGood = codeMealsGood;
    }
    public String getProductName(){
        return productNameMealsGood;
    }
    public String getProductVariation(){
        return productVariationMealsGood;
    }
    public String getStatus(){
        return statusMealsGood;
    }
    public String getImage(){
        return imageMealsGood;
    }
    public int getPrice(){
        return priceMealsGood;
    }
    public String getCodeMealsGood() {
        return codeMealsGood;
    }
}
