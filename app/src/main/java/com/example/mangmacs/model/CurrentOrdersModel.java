package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class CurrentOrdersModel {
    @SerializedName("success")
    private String success;
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
    @SerializedName("recipient_name")
    private String recipientName;
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
    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("product_name")
    private String products;
    @SerializedName("productCategory")
    private String category;
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
    @SerializedName("delivery_fee")
    private String deliveryChange;
    //order time and date
    @SerializedName("payment_photo")
    private String paymentPhoto;
    @SerializedName("payment_type")
    private String paymentType;
    @SerializedName("product_image")
    private String imgProduct;
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("completed_time")
    private String completedTime;
    @SerializedName("waiting_time")
    private String waitingTime;

    public CurrentOrdersModel(String success, String id, String productCode, String orderedDate, String requiredDate, String requiredTime, String recipientName, String customerName, String customerAddress, String labelAddress, String email, String contactNumber, String orderNumber, String orderId, String products, String category, String variations, String quantities, String addOns, String subTotal, String price, String totalAmount, String deliveryChange, String paymentPhoto, String paymentType, String imgProduct, String orderType, String orderStatus, String completedTime, String waitingTime) {
        this.success = success;
        this.id = id;
        this.productCode = productCode;
        this.orderedDate = orderedDate;
        this.requiredDate = requiredDate;
        this.requiredTime = requiredTime;
        this.recipientName = recipientName;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.labelAddress = labelAddress;
        this.email = email;
        this.contactNumber = contactNumber;
        this.orderNumber = orderNumber;
        this.orderId = orderId;
        this.products = products;
        this.category = category;
        this.variations = variations;
        this.quantities = quantities;
        this.addOns = addOns;
        this.subTotal = subTotal;
        this.price = price;
        this.totalAmount = totalAmount;
        this.deliveryChange = deliveryChange;
        this.paymentPhoto = paymentPhoto;
        this.paymentType = paymentType;
        this.imgProduct = imgProduct;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.completedTime = completedTime;
        this.waitingTime = waitingTime;
    }

    public String getSuccess() {
        return success;
    }

    public String getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getLabelAddress() {
        return labelAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProducts() {
        return products;
    }

    public String getCategory() {
        return category;
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

    public String getDeliveryChange() {
        return deliveryChange;
    }

    public String getPaymentPhoto() {
        return paymentPhoto;
    }

    public String getPaymentType() {
        return paymentType;
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

    public String getCompletedTime() {
        return completedTime;
    }

    public String getWaitingTime() {
        return waitingTime;
    }
}
