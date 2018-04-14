package com.easy.fly.flyeasy.db.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirLine {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("airlineName")
    @Expose
    private String airlineName;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("logo")
    @Expose
    private Logo logo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

}
