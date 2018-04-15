package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirLine implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AirLine createFromParcel(Parcel in) {
            return new AirLine(in);
        }

        public AirLine[] newArray(int size) {
            return new AirLine[size];
        }
    };
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("airlineName")
    @Expose
    private String airlineName;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("logo")
    @Expose
    private Logo logo;

    public AirLine(Parcel in){
        this.id = in.readLong();
        this.airlineName = in.readString();
        this.rating = in.readDouble();
        this.description = in.readString();
        this.logo =(Logo) in.readParcelable(Logo.class.getClassLoader());
    }

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.airlineName);
        dest.writeDouble(this.rating);
        dest.writeString(this.description);
        dest.writeParcelable(this.logo,flags);
    }
}
