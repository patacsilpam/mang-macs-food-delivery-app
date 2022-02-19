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
    @SerializedName("status")
    private String promoStatus;
    @SerializedName("price")
    private int pricePromo;
    @SerializedName("productImage")
    private String productImgPromo;

    public PromoListModel(String productPromoCode, String productNamePromo, String productCategoryPromo, String productVariationPromo, String promoStatus, int pricePromo, String productImgPromo) {
        this.productPromoCode = productPromoCode;
        this.productNamePromo = productNamePromo;
        this.productCategoryPromo = productCategoryPromo;
        this.productVariationPromo = productVariationPromo;
        this.promoStatus = promoStatus;
        this.pricePromo = pricePromo;
        this.productImgPromo = productImgPromo;
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

    public String getPromoStatus() {
        return promoStatus;
    }

    public int getPricePromo() {
        return pricePromo;
    }

    public String getProductImgPromo() {
        return productImgPromo;
    }
}
