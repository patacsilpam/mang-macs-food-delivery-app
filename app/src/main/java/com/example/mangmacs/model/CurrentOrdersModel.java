package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CurrentOrdersModel {
    @SerializedName("id")
    private String id;
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("created_at")
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
    @SerializedName("label_address")
    private String labelAddress;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String contactNumber;
    //customer order details
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("product_name")
    private String products;
    @SerializedName("product_variation")
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
    @SerializedName("product_image")
    private String imgProduct;
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("order_status")
    private String orderStatus;

    public CurrentOrdersModel(String id, String productCode, String orderedDate, String requiredDate, String requiredTime, String customerName, String customerAddress, String labelAddress, String email, String contactNumber, String orderId,String products, String variations, String quantities, String addOns, String subTotal, String price, String totalAmount, String paymentPhoto, String imgProduct, String orderType, String orderStatus) {
        this.id = id;
        this.productCode = productCode;
        this.orderedDate = orderedDate;
        this.requiredDate = requiredDate;
        this.requiredTime = requiredTime;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.orderId = orderId;
        this.labelAddress = labelAddress;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getLabelAddress() {
        return labelAddress;
    }

    public void setLabelAddress(String labelAddress) {
        this.labelAddress = labelAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getVariations() {
        return variations;
    }

    public void setVariations(String variations) {
        this.variations = variations;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public String getAddOns() {
        return addOns;
    }

    public void setAddOns(String addOns) {
        this.addOns = addOns;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentPhoto() {
        return paymentPhoto;
    }

    public void setPaymentPhoto(String paymentPhoto) {
        this.paymentPhoto = paymentPhoto;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
