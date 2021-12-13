package com.example.mangmacs.model;

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
    @SerializedName("code")
    private String codePasta;
    public PastaListModel(String productName,String productVariation,String status,String image,int price,  String codePasta){
        this.productNamePasta = productName;
        this.productVariationPasta = productVariation;
        this.statusPasta = status;
        this.imagePasta = image;
        this.pricePasta = price;
        this.codePasta = codePasta;
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

    public String getCodePasta() {
        return codePasta;
    }
}
