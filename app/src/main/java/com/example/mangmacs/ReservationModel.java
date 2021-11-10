package com.example.mangmacs;

import com.google.gson.annotations.SerializedName;

public class ReservationModel {
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("guests")
    private String guests;
    @SerializedName("scheduled_date")
    private String scheduled_date;
    @SerializedName("scheduled_time")
    private String scheduled_time;

    public ReservationModel(String fname, String lname, String guests, String scheduled_date, String scheduled_time) {
        this.fname = fname;
        this.lname = lname;
        this.guests = guests;
        this.scheduled_date = scheduled_date;
        this.scheduled_time = scheduled_time;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getScheduled_date() {
        return scheduled_date;
    }

    public void setScheduled_date(String scheduled_date) {
        this.scheduled_date = scheduled_date;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(String scheduled_time) {
        this.scheduled_time = scheduled_time;
    }
}
