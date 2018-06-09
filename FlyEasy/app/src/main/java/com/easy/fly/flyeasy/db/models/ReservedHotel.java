package com.easy.fly.flyeasy.db.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservedHotel implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ReservedHotel createFromParcel(Parcel in) {
            return new ReservedHotel(in);
        }

        public HotelBook[] newArray(int size) {
            return new HotelBook[size];
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("hotelName")
    @Expose
    private String hotelName;
    @SerializedName("location")
    @Expose
    private LocationModel location;
    @SerializedName("pictures")
    @Expose
    private List<PictureResolution> pictures = null;

    public ReservedHotel(Parcel in) {
        this.id = in.readLong();
        this.hotelName = in.readString();
        this.location = (LocationModel) in.readParcelable(LocationModel.class.getClassLoader());
        this.pictures = (List<PictureResolution>)in.readArrayList(PictureResolution.class.getClassLoader());

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public List<PictureResolution> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureResolution> pictures) {
        this.pictures = pictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.id);
        dest.writeString(this.hotelName);
        dest.writeParcelable(this.location,flags);
        dest.writeArray(this.pictures.toArray());
    }
}
