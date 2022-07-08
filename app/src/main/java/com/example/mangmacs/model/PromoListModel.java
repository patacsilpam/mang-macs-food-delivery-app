package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class PromoListModel {
    @SerializedName("code")
    private String productPromoCode;
    @SerializedName("productName")
    private String productNamePromo;
    @SerializedName("productCategory")
    private String productCategoryPromo;
    @SerializedName("productVariation")
    private String productVariationPromo;
    @SerializedName("price")
    private int pricePromo;
    @SerializedName("productImage")
    private String productImgPromo;
    @SerializedName("stocks")
    private String stocks;
    @SerializedName("preparationTime")
    private String preparationTime;
    @SerializedName("mainIngredients")
    private String mainIngredients;

    public PromoListModel(String productPromoCode, String productNamePromo, String productCategoryPromo, String productVariationPromo, int pricePromo, String productImgPromo, String stocks, String preparationTime,String mainIngredients) {
        this.productPromoCode = productPromoCode;
        this.productNamePromo = productNamePromo;
        this.productCategoryPromo = productCategoryPromo;
        this.productVariationPromo = productVariationPromo;
        this.pricePromo = pricePromo;
        this.productImgPromo = productImgPromo;
        this.stocks = stocks;
        this.preparationTime = preparationTime;
        this.mainIngredients = mainIngredients;
    }

    public String getProductPromoCode() {
        return productPromoCode;
    }
    public String getProductNamePromo() {
        return productNamePromo;
    }
    public String getProductCategoryPromo() {
        return productCategoryPromo;
    }
    public String getProductVariationPromo() {
        return productVariationPromo;
    }
    public int getPricePromo() {
        return pricePromo;
    }
    public String getProductImgPromo() {
        return productImgPromo;
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
}
