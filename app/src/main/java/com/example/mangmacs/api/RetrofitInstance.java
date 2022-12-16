package com.example.mangmacs.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    //http://192.168.1.70/mang-macs-app-back-end-script/
    //https://eee6-112-206-151-11.ap.ngrok.io/mang-macs-app-back-end-script/
        public static Retrofit retrofit = null;
        public static final String URL = "https://eee6-112-206-151-11.ap.ngrok.io/mang-macs-app-back-end-script/";
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
