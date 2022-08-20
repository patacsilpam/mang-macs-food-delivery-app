package com.example.mangmacs.model;

import com.google.gson.annotations.SerializedName;

public class ReservationModel {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("guests")
    private String guests;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("scheduled_date")
    private String scheduled_date;
    @SerializedName("scheduled_time")
    private String scheduled_time;
    @SerializedName("status")
    private String status;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("refNumber")
    private String refNumber;
    @SerializedName("notif_date")
    private String notifDate;
    @SerializedName("success")
    private String success;

    public ReservationModel(String id,String email,String fname, String lname, String guests, String createdAt, String scheduled_date, String scheduled_time, String status,String phoneNumber,String refNumber,String notifDate,String success) {
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.guests = guests;
        this.createdAt = createdAt;
        this.scheduled_date = scheduled_date;
        this.scheduled_time = scheduled_time;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.refNumber = refNumber;
        this.notifDate = notifDate;
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getGuests() {
        return guests;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getScheduled_date() {
        return scheduled_date;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public String getStatus() {
        return status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public String getNotifDate() {
        return notifDate;
    }

    public String getSuccess() {
        return success;
    }
}
