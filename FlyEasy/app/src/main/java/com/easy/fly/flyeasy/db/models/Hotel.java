package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("hotelName")
    @Expose
    private String hotelName;
    @SerializedName("location")
    @Expose
    private LocationModel location;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pictures")
    @Expose
    private List<PictureResolution> pictures = null;
    @SerializedName("hotelRooms")
    @Expose
    private List<HotelRoom> hotelRooms = null;

    public Hotel(Parcel in) {
        this.id = in.readLong();
        this.hotelName = in.readString();
        this.location = (LocationModel) in.readParcelable(LocationModel.class.getClassLoader());
        this.description = in.readString();
        this.pictures = (List<PictureResolution>)in.readArrayList(PictureResolution.class.getClassLoader());
        this.hotelRooms = (List<HotelRoom>)in.readArrayList(HotelRoom.class.getClassLoader());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PictureResolution> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureResolution> pictures) {
        this.pictures = pictures;
    }

    public List<HotelRoom> getHotelRooms() {
        return hotelRooms;
    }

    public void setHotelRooms(List<HotelRoom> hotelRooms) {
        this.hotelRooms = hotelRooms;
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
        dest.writeString(this.description);
        dest.writeArray(this.pictures.toArray());
        dest.writeArray(this.hotelRooms.toArray());

    }
}
