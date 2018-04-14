package com.easy.fly.flyeasy.db.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class Flight {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("airLine")
    @Expose
    private AirLine airLine;
    @SerializedName("flightName")
    @Expose
    private String flightName;
    @SerializedName("locationFrom")
    @Expose
    private LocationModel locationFrom;
    @SerializedName("locationTo")
    @Expose
    private LocationModel locationTo;
    @SerializedName("departDate")
    @Expose
    private String departDate;
    @SerializedName("arriveDate")
    @Expose
    private String arriveDate;
    @SerializedName("price")
    @Expose
    private BigDecimal price;
    @SerializedName("travelClasses")
    @Expose
    private List<TravelClass> travelClasses = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AirLine getAirLine() {
        return airLine;
    }

    public void setAirLine(AirLine airLine) {
        this.airLine = airLine;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public LocationModel getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(LocationModel locationFrom) {
        this.locationFrom = locationFrom;
    }

    public LocationModel getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(LocationModel locationTo) {
        this.locationTo = locationTo;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<TravelClass> getTravelClasses() {
        return travelClasses;
    }

    public void setTravelClasses(List<TravelClass> travelClasses) {
        this.travelClasses = travelClasses;
    }
}
