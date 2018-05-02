package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class HotelRoom implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public HotelRoom createFromParcel(Parcel in) {
            return new HotelRoom(in);
        }

        public HotelRoom[] newArray(int size) {
            return new HotelRoom[size];
        }
    };

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("typeRoom")
    @Expose
    private String typeRoom;
    @SerializedName("price")
    @Expose
    private BigDecimal price;

    public HotelRoom(Parcel in) {

        this.id = in.readLong();
        this.typeRoom = in.readString();
        this.price = BigDecimal.valueOf(in.readDouble());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
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
        dest.writeString(this.typeRoom);
        dest.writeDouble(this.price.doubleValue());
    }
}
