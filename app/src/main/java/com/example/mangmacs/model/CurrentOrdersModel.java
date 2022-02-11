package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CurrentOrdersModel {
    @SerializedName("id")
    private String id;
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("ordered_date")
    private String orderedDate;
    @SerializedName("required_date")
    private String requiredDate;
    @SerializedName("required_time")
    private String requiredTime;
    //customer information
    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("customer_address")
    private String customerAddress;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_no")
    private String contactNumber;
    //customer order details
    @SerializedName("product")
    private String products;
    @SerializedName("variation")
    private String variations;
    @SerializedName("quantity")
    private String quantities;
    @SerializedName("add_ons")
    private String addOns;
    @SerializedName("subtotal")
    private String subTotal;
    @SerializedName("price")
    private String price;
    @SerializedName("total_amount")
    private String totalAmount;
    //order time and date
    @SerializedName("payment_photo")
    private String paymentPhoto;
    @SerializedName("img_product")
    private String imgProduct;
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("order_status")
    private String orderStatus;

    public CurrentOrdersModel(String id,String productCode,String orderedDate, String requiredDate, String requiredTime, String customerName, String customerAddress, String email, String contactNumber, String products, String variations, String quantities, String addOns, String subTotal, String price, String totalAmount, String paymentPhoto, String imgProduct, String orderType, String orderStatus) {
        this.id = id;
        this.productCode = productCode;
        this.orderedDate = orderedDate;
        this.requiredDate = requiredDate;
        this.requiredTime = requiredTime;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.email = email;
        this.contactNumber = contactNumber;
        this.products = products;
        this.variations = variations;
        this.quantities = quantities;
        this.addOns = addOns;
        this.subTotal = subTotal;
        this.price = price;
        this.totalAmount = totalAmount;
        this.paymentPhoto = paymentPhoto;
        this.imgProduct = imgProduct;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }
    public String getProductCode(){return productCode;}

    public String getOrderedDate() {
        return orderedDate;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getProducts() {
        return products;
    }

    public String getVariations() {
        return variations;
    }

    public String getQuantities() {
        return quantities;
    }

    public String getAddOns() {
        return addOns;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getPrice() {
        return price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentPhoto() {
        return paymentPhoto;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
