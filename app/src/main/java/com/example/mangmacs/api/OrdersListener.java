package com.example.mangmacs.api;

import java.util.ArrayList;

public interface OrdersListener {
    void onProductsChange(ArrayList<String> products);
    void onProductCategoryChange(ArrayList<String> category);
    void onProductCodeChange(ArrayList<String> productCode);
    void onVariationChange(ArrayList<String> variations);
    void onQuantityChange(ArrayList<String> quantity);
    void onAddOnsChange(ArrayList<String> addOns);
    void onSubTotalChange(ArrayList<String> subTotal);
    void onPriceChange(ArrayList<String> price);
    void onImgProductChange(ArrayList<String> imgProduct);
    void onCustomerIdChange(String customerId);
    void onTotalAmountChange(String amount);
}
