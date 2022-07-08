package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PancitBilaoListModel {
    @SerializedName("productName")
    private String productNameBilao;
    @SerializedName("productCategory")
    private String productCategoryBilao;
    @SerializedName("productVariationBilao")
    private String productVariationBilao;
    @SerializedName("productImage")
    private String imageBilao;
    @SerializedName("price")
    private int priceBilao;
    @SerializedName("groupPriceBilao")
    private String groupPriceBilao;
    @SerializedName("groupCode")
    private String groupCode;
    @SerializedName("stocks")
    private String stocks;
    @SerializedName("preparationTime")
    private String preparationTime;
    @SerializedName("groupPreparationTime")
    private String groupPreparationTime;
    @SerializedName("mainIngredients")
    private String mainIngredients;
    // private float rating;

    public PancitBilaoListModel(String productNameBilao, String productCategoryBilao, String productVariationBilao, String imageBilao, int priceBilao, String groupPriceBilao, String groupCode, String stocks, String preparationTime, String groupPreparationTime,String mainIngredients) {
        this.productNameBilao = productNameBilao;
        this.productCategoryBilao = productCategoryBilao;
        this.productVariationBilao = productVariationBilao;
        this.imageBilao = imageBilao;
        this.priceBilao = priceBilao;
        this.groupPriceBilao = groupPriceBilao;
        this.groupCode = groupCode;
        this.stocks = stocks;
        this.preparationTime = preparationTime;
        this.groupPreparationTime = groupPreparationTime;
        this.mainIngredients = mainIngredients;
    }

    public String getProductName(){
        return productNameBilao;
    }
    public String getProductCategoryBilao() {
        return productCategoryBilao;
    }
    public String getProductVariation(){
        return productVariationBilao;
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
    public String getStocks() {
        return stocks;
    }
    public String getPreparationTime() {
        return preparationTime;
    }
    public String getGroupPreparationTime() {
        return groupPreparationTime;
    }
    public String getMainIngredients() {
        return mainIngredients;
    }
}
