package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PizzaListModel {
    @SerializedName("productName")
    private String productName;
    @SerializedName("productVariation")
    private String productVariation;
    @SerializedName("status")
    private String status;
    @SerializedName("productImage")
    private String image;
    @SerializedName("price")
    private int price;
    @SerializedName("groupPrice")
    private String groupPrice;
    @SerializedName("groupCode")
    private String code;
   // private float rating;
    public PizzaListModel(String productName,String productVariation,String status,String image,int price,String groupPrice,  String code){
        this.productName = productName;
        this.productVariation = productVariation;
        this.status = status;
        this.image = image;
        this.price = price;
        this.groupPrice = groupPrice;
        this.code = code;
        //this.rating = rating;
    }
    public String getProductName(){
        return productName;
    }
    public String getProductVariation(){
        return productVariation;
    }
    public String getStatus(){
        return status;
    }
    public String getImage(){
        return image;
    }
    public int getPrice(){
        return price;
    }

    public String getGroupPrice() {
        return groupPrice;
    }

    public String getCode() {
        return code;
    }
}
