package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class AppetizerModel {
    @SerializedName("productName")
    private String productNameRice;
    @SerializedName("productVariation")
    private String productVariationRice;
    @SerializedName("status")
    private String statusRice;
    @SerializedName("productImage")
    private String imageRice;
    @SerializedName("price")
    private int priceRice;
    @SerializedName("code")
    private String codeRiceMeal;
    public AppetizerModel(String productName, String productVariation, String status, String image, int price, String codeRiceMeal){
        this.productNameRice = productName;
        this.productVariationRice = productVariation;
        this.statusRice = status;
        this.imageRice = image;
        this.priceRice = price;
        this.codeRiceMeal = codeRiceMeal;
    }
    public String getProductName(){
        return productNameRice;
    }
    public String getProductVariation(){
        return productVariationRice;
    }
    public String getStatus(){
        return statusRice;
    }
    public String getImage(){
        return imageRice;
    }
    public int getPrice(){
        return priceRice;
    }

    public String getCodeRiceMeal() {
        return codeRiceMeal;
    }
}
