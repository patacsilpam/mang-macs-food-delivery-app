package com.example.mangmacs.api;

import com.example.mangmacs.model.ComboMealListModel;
import com.example.mangmacs.model.CustomerLoginModel;
import com.example.mangmacs.model.CustomerModel;
import com.example.mangmacs.model.DimsumListModel;
import com.example.mangmacs.model.DrinksListModel;
import com.example.mangmacs.model.MealsGoodListModel;
import com.example.mangmacs.model.NoodlesListModel;
import com.example.mangmacs.model.PancitBilaoListModel;
import com.example.mangmacs.model.PancitListModel;
import com.example.mangmacs.model.PastaListModel;
import com.example.mangmacs.model.PizzaListModel;
import com.example.mangmacs.model.PopularListModel;
import com.example.mangmacs.model.ReservationModel;
import com.example.mangmacs.model.RiceListModel;
import com.example.mangmacs.model.RicecupListModel;
import com.example.mangmacs.model.SeafoodsListModel;
import com.example.mangmacs.model.SoupListModel;
import com.example.mangmacs.model.UpdateAccountModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("getPizzaProduct.php")
    Call<List<PizzaListModel>> getPizza();
    @GET("getRiceMealProduct.php")
    Call<List<RiceListModel>> getRiceMeal();
    @GET("getComboMealProduct.php")
    Call<List<ComboMealListModel>> getComboMeal();
    @GET("getMealsGoodProduct.php")
    Call<List<MealsGoodListModel>> getMealsGood();
    @GET("getSeafoodsProduct.php")
    Call<List<SeafoodsListModel>> getSeafoods();
    @GET("getSoupProduct.php")
    Call<List<SoupListModel>> getSoup();
    @GET("getRiceProduct.php")
    Call<List<RicecupListModel>> getRice();
    @GET("getPancitProduct.php")
    Call<List<PancitListModel>> getPancit();
    @GET("getPancitBilaoProduct.php")
    Call<List<PancitBilaoListModel>> getPancitBilao();
    @GET("getPastaProduct.php")
    Call<List<PastaListModel>> getPasta();
    @GET("getDimsumProduct.php")
    Call<List<DimsumListModel>> getDimsum();
    @GET("getDrinksProduct.php")
    Call<List<DrinksListModel>> getDrinks();
    @GET("getNoodlesProduct.php")
    Call<List<NoodlesListModel>> getNoodles();
    @GET("getPopularProduct.php")
    Call<List<PopularListModel>> getPopular();
    @GET("customerLogin.php")
    Call<CustomerLoginModel> userLogin(
            @Query("email_address") String email_address,
            @Query("user_password") String password
    );
    @POST("customer-Signup.php")
    @FormUrlEncoded
    Call<CustomerModel> registerUser(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email_address") String email,
            @Field("user_password") String password
    );
    @GET("selectCustomer.php")
    Call<UpdateAccountModel> selectAccount(
            @Query("email_address") String email
    );
    @POST("reservation.php")
    @FormUrlEncoded
    Call<ReservationModel> reservation(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("guests") String guests,
            @Field("email") String email,
            @Field("scheduled_date") String date,
            @Field("scheduled_time") String time
    );
    @POST("customerUpdate.php")
    @FormUrlEncoded
    Call<UpdateAccountModel> updateAccount(
            @Field("email_address") String email,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("gender") String gender,
            @Field("birthdate") String birthdate
    );
    @POST("customerUpdatePword.php")
   @FormUrlEncoded
    Call<UpdateAccountModel> updatePassword(
            @Field("email_address") String email,
            @Field("user_password") String oldPword
           // @Field("newPassword") String newPword
    );
}
