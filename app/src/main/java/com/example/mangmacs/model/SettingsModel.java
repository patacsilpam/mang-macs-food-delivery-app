package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class SettingsModel {
    @SerializedName("code")
    private String code;
    @SerializedName("stocks")
    private int stocks;
    @SerializedName("preparationTime")
    private String preparationTime;

    public SettingsModel(String code, int stocks, String preparationTime) {
        this.code = code;
        this.stocks = stocks;
        this.preparationTime = preparationTime;
    }

    public String getWaitingTime() {
        return code;
    }

    public int getStocks() {
        return stocks;
    }


    public String getPreparationTime() {
        return preparationTime;
    }
}
