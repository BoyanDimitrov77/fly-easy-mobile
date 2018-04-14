package com.easy.fly.flyeasy.db.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TravelClass {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("maxSeats")
    @Expose
    private long maxSeats;
    @SerializedName("travelClass")
    @Expose
    private String travelClass;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(long maxSeats) {
        this.maxSeats = maxSeats;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

}
