package com.example.mangmacs.api;

import com.example.mangmacs.model.AddressListModel;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.ProductListModel;
import com.example.mangmacs.model.CurrentOrdersModel;
import com.example.mangmacs.model.CustomerLoginModel;
import com.example.mangmacs.model.CustomerModel;
import com.example.mangmacs.model.PancitBilaoListModel;
import com.example.mangmacs.model.PizzaListModel;
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.model.PromoListModel;
import com.example.mangmacs.model.ReservationModel;
import com.example.mangmacs.model.SettingsModel;
import com.example.mangmacs.model.UpdateAccountModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    //get pizza product
    @GET("getPizzaProduct.php")
    Call<List<PizzaListModel>> getPizza();
    //get appetizer product
    @GET("getAppetizerProduct.php")
    Call<List<ProductListModel>> getAppetizer();
    //get mang macs grilled menu
    @GET("getGrilledProduct.php")
    Call<List<ProductListModel>> getGrilled();
    //get barbeque menu
    @GET("getBarbequeProduct.php")
    Call<List<ProductListModel>> getBarbeque();
    //get add ons menu
    @GET("getAddOnsProduct.php")
    Call<List<ProductListModel>> getAddOns();
    //get pork product
    @GET("getPorkProduct.php")
    Call<List<ProductListModel>> getMealsGood();
    //get chicken product
    @GET("getChickenProduct.php")
    Call<List<ProductListModel>> getChicken();
    //get beef product
    @GET("getBeefProduct.php")
    Call<List<ProductListModel>> getBeef();
    //get pigar prigar product
    @GET("getPigarPigarProduct.php")
    Call<List<ProductListModel>> getPigarPigar();
    //get seafoods product
    @GET("getSeafoodsProduct.php")
    Call<List<ProductListModel>> getSeafoods();
    //get vegetable product
    @GET("getVegetableProduct.php")
    Call<List<ProductListModel>> getVegetable();
    //get seafoods product
    @GET("getRiceProduct.php")
    Call<List<ProductListModel>> getRice();
    //get sizzling plates product
    @GET("getSizzlingProduct.php")
    Call<List<ProductListModel>> getSizzling();
    //get noodles product
    @GET("getNoodlesProduct.php")
    Call<List<ProductListModel>> getNoodles();
    //get bilao products
    @GET("getPancitBilaoProduct.php")
    Call<List<PancitBilaoListModel>> getPancitBilao();
    //get pasta product
    @GET("getPastaProduct.php")
    Call<List<ProductListModel>> getPasta();
    //get dimsum product
    @GET("getDimsumProduct.php")
    Call<List<ProductListModel>> getDimsum();
    //get soup product
    @GET("getSoupProduct.php")
    Call<List<ProductListModel>> getSoup();
    @GET("getDrinksProduct.php")
    Call<List<ProductListModel>> getDrinks();
    //get dessert product
    @GET("getDessertProduct.php")
    Call<List<ProductListModel>> getDessert();
    //get mang macs pulutan product
    @GET("getPulutanProduct.php")
    Call<List<ProductListModel>> getPulutan();
    //get mang macs drinks product
    @GET("getWineProduct.php")
    Call<List<ProductListModel>> getWine();
    //get beer bucket product
    @GET("getBeerBucketProduct.php")
    Call<List<ProductListModel>> getBeerBucket();
    //get beverages product
    @GET("getBeveragesProduct.php")
    Call<List<ProductListModel>> getBeverages();
    //get best sellers mang  macs product
    @GET("getPopularProduct.php")
    Call<List<PizzaListModel>> getPopular();
    //get mang macs promo product
    @GET("getPromoProduct.php")
    Call<List<PromoListModel>> getPromos();
    //users login
    @GET("customerLogin.php")
    Call<CustomerLoginModel> userLogin(
            @Query("email_address") String email_address,
            @Query("user_password") String password
    );
    //users registration/sign up
    @POST("customer-Signup.php")
    @FormUrlEncoded
    Call<CustomerModel> registerUser(
            @Field("token") String token,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("phone_number") String phoneNumber,
            @Field("email_address") String email,
            @Field("user_password") String password,
            @Field("code") int code
    );
    //get user's email and other data
    @GET("selectCustomer.php")
    Call<UpdateAccountModel> selectAccount(
            @Query("email_address") String email
    );
    //updating users account
    @POST("customerUpdate.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> updateAccount(
            @Field("email_address") String email,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("gender") String gender,
            @Field("birthdate") String birthdate
    );
    //updating users password
    @POST("customerUpdatePword.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> updatePassword(
            @Field("email") String email,
            @Field("currentPassword") String oldPword,
            @Field("newPassword") String newPword
    );
    //recovering users forgot password by sending verification code
    @POST("recoverPassword.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> getEmail(
            @Field("email") String email,
            @Field("code") int code
    );
    //send verification code for verifying user's account
    @GET("verifyCode.php")
    Call<UpdateAccountModel> getCode(
      @Query("email") String email,
      @Query("code") String code
    );
    //resetting password
    @POST("resetPassword.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> resetPassword(
            @Field("email") String email,
            @Field("newPassword") String newPassword,
            @Field("confirmPassword") String confirmPassword
    );
    //verifying email account after user's registration
    @GET("verifyEmail.php")
    Call<UpdateAccountModel> getEmail(
            @Query("email") String email,
            @Query("code") String code
    );
    //creating user's address for delivery
    @POST("customerAddress.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> createAddress(
            @Field("customerID") String customerId,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("street") String street,
            @Field("barangay") String barangay,
            @Field("city") String city,
            @Field("province") String province,
            @Field("labelAddress") String labeladdress,
            @Field("phoneNo") String phoneNumber
    );
    //get user's address
    @GET("selectAddress.php")
    Call<List<AddressListModel>> getAddress(
            @Query("emailaddress") String email
    );
    //update specific address of user
    @POST("updateCustomerAddress.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> updateCustomerAddress(
            @Field("id") int id,
            @Field("fullname") String fullname,
            @Field("street") String street,
            @Field("barangay") String barangay,
            @Field("labelAddress") String labelAddress,
            @Field("phoneNumber") String phoneNumber
    );
    //delete specific user's address
    @POST("deleteAddress.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> deleteAddress(
            @Field("id") String id
    );
    //create or book a reservation table
    @POST("reservation.php")
    @FormUrlEncoded
    Call<ReservationModel> reservation(
            @Field("customerId") String customerId,
            @Field("token") String token,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("guests") String guests,
            @Field("email") String email,
            @Field("phone_no") String phoneNo,
            @Field("scheduled_date") String date,
            @Field("scheduled_time") String time,
            @Field("payment_number") String paymentNumber,
            @Field("payment_photo") String paymentPhoto,
            @Field("comments") String comments
    );
    //inserting user's cart
    @POST("customerCartProduct.php")
    @FormUrlEncoded
    Call<CartModel> addcart(
            @Field("email") String email,
            @Field("code") String productCode,
            @Field("stockCode") String stockCode,
            @Field("product") String productName,
            @Field("productCategory") String productCategory,
            @Field("variation") String variation,
            @Field("fname") String firstname,
            @Field("lname") String lastname,
            @Field("price") int amount,
            @Field("quantity") int quantity,
            @Field("addOns") String addOns,
            @Field("addOnsFee") int addOnsFee,
            @Field("specialRequest") String specialRequest,
            @Field("imageProduct") String image,
            @Field("preparedTime") String preparedTime
    );
    //get all user's add to cart product
    @GET("selectCart.php")
    Call<List<CartModel>> getCart(
            @Query("emailaddress") String email
    );
    //delete specific add to cart product
    @GET("deleteCart.php")
    Call<CartModel> deleteCart(
            @Query("id") int id
    );
    //update specific product quantity
    @POST("updateQuantity.php")
    @FormUrlEncoded
    Call<CartModel> updateQuantity(
            @Field("id") int id,
            @Field("quantity") int quantity
    );
    //send order(s) to mang macs foodshop
    @POST("customerOrders.php")
    @FormUrlEncoded
    Call<CartModel> insertOrder(
            @Field("productCode[]") ArrayList<String> productCode,
            @Field("customerId") String customerId,
            @Field("accountName") String accountName,
            @Field("recipientName") String recipientName,
            @Field("customer_address") String address,
            @Field("labelAddress") String labelAddress,
            @Field("token") String token,
            @Field("email") String email,
            @Field("phoneNumber") String phoneNumber,
            @Field("product[]") ArrayList<String> product,
            @Field("productCategory[]") ArrayList<String> productCategory,
            @Field("variation[]") ArrayList<String> variation,
            @Field("quantity[]") ArrayList<Integer> quantity,
            @Field("addOns[]") ArrayList<String> addOns,
            @Field("addOnsFee[]") ArrayList<Integer> addOnsFee,
            @Field("specialRequest[]") ArrayList<String> specialRequest,
            @Field("price[]") ArrayList<String> price,
            @Field("subTotal[]") ArrayList<String> subTotal,
            @Field("totalAmount") String totalAmount,
            @Field("paymentNumber") String paymentNumber,
            @Field("paymentPhoto") String paymentPhoto,
            @Field("paymentType") String paymentType,
            @Field("imgProduct[]") ArrayList<String> imgProduct,
            @Field("preparationTime[]") ArrayList<String > preparationTime,
            @Field("orderType") String orderType,
            @Field("orderStatus") String orderStatus,
            @Field("requiredDate") String requiredDate,
            @Field("requiredTime") String requiredTime,
            @Field("deliveryFee") int deliveryFee,
            @Field("waitingTime") String waitingTime
    );
    //insert dine in order method
    @POST("reservation.php")
    @FormUrlEncoded
    Call<CartModel> dineInOrder(
            @Field("productCode[]") ArrayList<String> productCodeList,
            @Field("customerId") String customerId,
            @Field("token") String token,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("guests") String guests,
            @Field("email") String email,
            @Field("scheduled_date") String date,
            @Field("scheduled_time") String time,
            @Field("diningArea") String diningArea,
            @Field("comments") String comments,
            @Field("product[]") ArrayList<String> product,
            @Field("productCategory[]") ArrayList<String> productCategory,
            @Field("variation[]") ArrayList<String> variation,
            @Field("quantity[]") ArrayList<Integer> quantity,
            @Field("addOns[]") ArrayList<String> addOns,
            @Field("addOnsFee[]") ArrayList<Integer> addOnsFee,
            @Field("specialRequest[]") ArrayList<String> specialRequest,
            @Field("price[]") ArrayList<String> price,
            @Field("subTotal[]") ArrayList<String> subTotal,
            @Field("totalAmount") String totalAmount,
            @Field("paymentPhoto") String paymentPhoto,
            @Field("paymentType") String paymentType,
            @Field("imgProduct[]") ArrayList<String> imgProduct,
            @Field("preparationTime[]") ArrayList<String > preparationTime,
            @Field("orderType") String orderType,
            @Field("orderStatus") String orderStatus
    );
    //get all the current orders
    @GET("selectNewOrders.php")
    Call<List<CurrentOrdersModel>> getCurrentOrders(
            @Query("emailAddress") String emailAddress
    );
    //get all delivered or completed orders
    @GET("selectDeliveredOrders.php")
    Call<List<CurrentOrdersModel>> getDeliveredOrders(
            @Query("emailAddress") String emailAddress
    );
    //get all the current reservation table
    @GET("selectNewReservation.php")
    Call<List<ReservationModel>> getCurrentBookings(
            @Query("emailAddress") String emailAddress
    );
    @GET("selectNewReservationDetails.php")
    Call<List<ReservationModel>> getCurrentBookingsDetails(
            @Query("emailAddress") String emailAddress,
            @Query("orderNumber") String orderNumber
    );
    //get all the completed reservation table
    @GET("selectProcessedReservation.php")
    Call<List<ReservationModel>> getPreviousBookings(
            @Query("emailAddress") String emailAddress
    );
    @GET("selectNewReservationDetails.php")
    Call<List<ReservationModel>> getPreviousBookingsDetails(
            @Query("emailAddress") String emailAddress,
            @Query("orderNumber") String orderNumber
    );
    //get the current order details
    @GET("selectNewOrderDetails.php")
    Call<List<CurrentOrdersModel>> getNewOrderDetails(
            @Query("emailAddress") String emailAddress,
            @Query("orderNumber") String orderNumber
    );
    //get the completed order details
    @GET("selectDeliveredOrderDetails.php")
    Call<List<CurrentOrdersModel>> getPreviousOrderDetails(
            @Query("emailAddress") String emailAddress,
            @Query("orderNumber") String orderNumber
    );
    //cancel orders
    @POST("cancelOrders.php")
    @FormUrlEncoded
    Call<CurrentOrdersModel> cancelOrder(
        @Field("id") String id,
        @Field("product[]") ArrayList<String> product,
        @Field("quantity[]") ArrayList<Integer> quantity,
        @Field("itemCategory[]") ArrayList<String> category,
        @Field("itemVariation[]") ArrayList<String> variation
    );
    //cancel booking
    @POST("cancelBookings.php")
    @FormUrlEncoded
    Call<CartModel> cancelBookings(
            @Field("id") String id
    );
    //get settings -- delivery time and charges
    @GET("getStocks.php")
    Call <List<SettingsModel>> getSettings(
            @Query("id[]") ArrayList<String> id,
            @Query("email") String email
    );
    @GET("getOrders.php")
    Call <List<CurrentOrdersModel>> getOrdersNotif(
            @Query("emailAddress") String email
    );
    @GET("getReservation.php")
    Call <List<ReservationModel>> getBookNotif(
            @Query("emailAddress") String email
    );
    //validate and change order status to order received by users
    @POST("orderReceived.php")
    @FormUrlEncoded
    Call<CartModel> changeOrderStatus(
            @Field("orderNumber") String orderNumber
    );
    @POST("orderReceivedReservation.php")
    @FormUrlEncoded
    Call<CartModel> changeBookingStatus(
            @Field("orderNumber") String orderNumber
    );
}
