package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class PastaListModel {
    @SerializedName("productName")
    private String productNamePasta;
    @SerializedName("productVariation")
    private String productVariationPasta;
    @SerializedName("status")
    private String statusPasta;
    @SerializedName("productImage")
    private String imagePasta;
    @SerializedName("price")
    private int pricePasta;
    // private float rating;
    public PastaListModel(String productName,String productVariation,String status,String image,int price,  float rating){
        this.productNamePasta = productName;
        this.productVariationPasta = productVariation;
        this.statusPasta = status;
        this.imagePasta = image;
        this.pricePasta = price;
        //this.rating = rating;
    }
    public String getProductName(){
        return productNamePasta;
    }
    public String getProductVariation(){
        return productVariationPasta;
    }
    public String getStatus(){
        return statusPasta;
    }
    public String getImage(){
        return imagePasta;
    }
    public int getPrice(){
        return pricePasta;
    }
}
