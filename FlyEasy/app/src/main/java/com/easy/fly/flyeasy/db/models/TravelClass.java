package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class TravelClass implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TravelClass createFromParcel(Parcel in) {
            return new TravelClass(in);
        }

        public TravelClass[] newArray(int size) {
            return new TravelClass[size];
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("maxSeats")
    @Expose
    private long maxSeats;
    @SerializedName("travelClass")
    @Expose
    private String travelClass;

    @SerializedName("price")
    @Expose
    private BigDecimal price;

    public TravelClass(Parcel in) {
        this.id = in.readLong();
        this.maxSeats = in.readLong();
        this.travelClass = in.readString();
        this.price = BigDecimal.valueOf(in.readDouble());
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.maxSeats);
        dest.writeString(this.travelClass);
        dest.writeDouble(this.price.doubleValue());
    }
}
