package com.example.mangmacs.model;

public class ProductModal {
    private String productName;
    private String productPrice;
    public ProductModal(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }
    public String getProductName(){
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice(){
        return productPrice;
    }
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
