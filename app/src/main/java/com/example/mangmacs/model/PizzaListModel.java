package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PizzaListModel {
    @SerializedName("productName")
    private String productName;
    @SerializedName("productCategory")
    private String productCategory;
    @SerializedName("productVariation")
    private String productVariation;
    @SerializedName("productImage")
    private String image;
    @SerializedName("price")
    private int price;
    @SerializedName("groupPrice")
    private String groupPrice;
    @SerializedName("groupCode")
    private String code;
    @SerializedName("mediumItemStock")
    private int mediumItemStock;
    @SerializedName("largeItemStock")
    private int largeItemStock;
    @SerializedName("mediumItemStockCode")
    private String mediumItemStockCode;
    @SerializedName("largeItemStockCode")
    private String largeItemStockCode;
    @SerializedName("preparationTime")
    private String preparationTime;
    @SerializedName("groupPreparationTime")
    private String groupPreparationTime;
    @SerializedName("mainIngredients")
    private String mainIngredients;
    @SerializedName("groupAddOns")
    private String groupAddOns;
    @SerializedName("groupAddOnsPrice")
    private String groupAddOnsPrice;
    public PizzaListModel(String productName, String productCategory, String productVariation, String image, int price, String groupPrice, String code, int mediumItemStock,int largeItemStock, String mediumItemStockCode,String largeItemStockCode,String preparationTime, String groupPreparationTime,String mainIngredients, String groupAddOns,String groupAddOnsPrice) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productVariation = productVariation;
        this.image = image;
        this.price = price;
        this.groupPrice = groupPrice;
        this.code = code;
        this.mediumItemStock = mediumItemStock;
        this.largeItemStock = largeItemStock;
        this.mediumItemStockCode = mediumItemStockCode;
        this.largeItemStockCode = largeItemStockCode;
        this.preparationTime = preparationTime;
        this.groupPreparationTime = groupPreparationTime;
        this.mainIngredients = mainIngredients;
        this.groupAddOns = groupAddOns;
        this.groupAddOnsPrice = groupAddOnsPrice;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public String getProductVariation() {
        return productVariation;
    }
    public String getImage() {
        return image;
    }
    public int getPrice() {
        return price;
    }
    public String getGroupPrice() {
        return groupPrice;
    }
    public String getCode() {
        return code;
    }

    public int getMediumItemStock() {
        return mediumItemStock;
    }

    public int getLargeItemStock() {
        return largeItemStock;
    }

    public String getMediumItemStockCode() {
        return mediumItemStockCode;
    }

    public String getLargeItemStockCode() {
        return largeItemStockCode;
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

    public String getGroupAddOns() {
        return groupAddOns;
    }

    public String getGroupAddOnsPrice() {
        return groupAddOnsPrice;
    }
}

