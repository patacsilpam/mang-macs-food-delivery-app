package com.example.mangmacs.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    //http://192.168.1.70/mang-macs-app-back-end-script/
   //https://mangmacsmarinero.000webhostapp.com/mang-macs-app-back-end-script/
        public static Retrofit retrofit = null;
        public static final String URL = "https://f62c-112-211-201-146.ap.ngrok.io/mang-macs-app-back-end-script/";
        public static Retrofit getRetrofit() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
}
