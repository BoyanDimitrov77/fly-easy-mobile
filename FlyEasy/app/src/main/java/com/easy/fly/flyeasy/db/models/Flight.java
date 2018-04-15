package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class Flight implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

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

    public Flight(Parcel in) {
        this.id = in.readLong();
        this.airLine = (AirLine) in.readParcelable(AirLine.class.getClassLoader());
        this.flightName = in.readString();
        this.locationFrom = (LocationModel) in.readParcelable(LocationModel.class.getClassLoader());
        this.locationTo = (LocationModel) in.readParcelable(LocationModel.class.getClassLoader());
        this.departDate = in.readString();
        this.arriveDate = in.readString();
        this.price = BigDecimal.valueOf(in.readDouble());
        this.travelClasses = (List<TravelClass>)in.readArrayList(TravelClass.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.airLine,flags);
        dest.writeString(this.flightName);
        dest.writeParcelable(this.locationFrom,flags);
        dest.writeParcelable(this.locationTo,flags);
        dest.writeString(this.departDate);
        dest.writeString(this.arriveDate);
        dest.writeDouble(this.price.doubleValue());
        dest.writeArray(this.travelClasses.toArray());

    }
}
