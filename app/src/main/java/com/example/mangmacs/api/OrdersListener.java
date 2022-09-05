package com.example.mangmacs.api;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface OrdersListener {
    void onProductsChange(ArrayList<String> products);
    void onProductCategoryChange(ArrayList<String> category);
    void onProductCodeChange(ArrayList<String> productCode);
    void onVariationChange(ArrayList<String> variations);
    void onQuantityChange(ArrayList<Integer> quantity);
    void onAddOnsChange(ArrayList<String> addOns);
    void onSubTotalChange(ArrayList<String> subTotal);
    void onPriceChange(ArrayList<String> price);
    void onImgProductChange(ArrayList<String> imgProduct);
    void onCustomerIdChange(String customerId);
    void onTotalAmountChange(String amount);
    void onPreparationTimeChange(ArrayList<String> preparationTime);
}
