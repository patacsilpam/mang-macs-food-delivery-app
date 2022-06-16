package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class SettingsModel {
    @SerializedName("code")
    private String waitingTime;
    @SerializedName("stocks")
    private int stocks;
    public SettingsModel(String waitingTime, int stocks) {
        this.waitingTime = waitingTime;
        this.stocks = stocks;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public int getStocks() {
        return stocks;
    }
}
