package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class DrinksListModel {
    @SerializedName("productName")
    private String productNameDrinks;
    @SerializedName("productVariation")
    private String productVariationDrinks;
    @SerializedName("status")
    private String statusDrinks;
    @SerializedName("productImage")
    private String imageDrinks;
    @SerializedName("price")
    private int priceDrinks;
    // private float rating;
    public DrinksListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNameDrinks = productName;
        this.productVariationDrinks = productVariation;
        this.statusDrinks = status;
        this.imageDrinks = image;
        this.priceDrinks = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNameDrinks;
    }
    public String getProductVariation(){
        return productVariationDrinks;
    }
    public String getStatus(){
        return statusDrinks;
    }
    public String getImage(){
        return imageDrinks;
    }
    public int getPrice(){
        return priceDrinks;
    }
}
