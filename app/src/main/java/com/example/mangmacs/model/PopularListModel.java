package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PopularListModel {
    @SerializedName("code")
    private String productCodePopular;
    @SerializedName("productCategory")
    private String productCategoryPopular;
    @SerializedName("productName")
    private String productNamePopular;
    @SerializedName("productVariation")
    private String productVariationPopular;
    @SerializedName("status")
    private String statusPopular;
    @SerializedName("productImage")
    private String imagePopular;
    @SerializedName("price")
    private int pricePopular;
    @SerializedName("stocks")
    private String stocks;
    @SerializedName("preparationTime")
    private String preparationTime;
    @SerializedName("mainIngredients")
    private String mainIngredients;
    @SerializedName("add_ons")
    private String addOns;
    @SerializedName("add_ons_price")
    private String addOnsPrice;

    public PopularListModel(String productCodePopular, String productCategoryPopular, String productNamePopular, String productVariationPopular, String statusPopular, String imagePopular, int pricePopular, String stocks, String preparationTime,String mainIngredients,String addOns,String addOnsPrice) {
        this.productCodePopular = productCodePopular;
        this.productCategoryPopular = productCategoryPopular;
        this.productNamePopular = productNamePopular;
        this.productVariationPopular = productVariationPopular;
        this.statusPopular = statusPopular;
        this.imagePopular = imagePopular;
        this.pricePopular = pricePopular;
        this.stocks = stocks;
        this.preparationTime = preparationTime;
        this.mainIngredients = mainIngredients;
        this.addOns = addOns;
        this.addOnsPrice = addOnsPrice;
    }

    public String getProductCodePopular() {
        return productCodePopular;
    }
    public String getProductCategoryPopular() {
        return productCategoryPopular;
    }
    public String getProductNamePopular() {
        return productNamePopular;
    }
    public String getProductVariationPopular() {
        return productVariationPopular;
    }
    public String getStatusPopular() {
        return statusPopular;
    }
    public String getImagePopular() {
        return imagePopular;
    }
    public int getPricePopular() {
        return pricePopular;
    }
    public String getStocks() {
        return stocks;
    }
    public String getPreparationTime() {
        return preparationTime;
    }
    public String getMainIngredients() {
        return mainIngredients;
    }
    public String getAddOns() {
        return addOns;
    }

    public String getAddOnsPrice() {
        return addOnsPrice;
    }

}
