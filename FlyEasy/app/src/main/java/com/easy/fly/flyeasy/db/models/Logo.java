package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Logo createFromParcel(Parcel in) {
            return new Logo(in);
        }

        public Logo[] newArray(int size) {
            return new Logo[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orignalPicture")
    @Expose
    private Picture orignalPicture;
    @SerializedName("thumbnailPicture")
    @Expose
    private Picture thumbnailPicture;
    @SerializedName("fullScreenPicture")
    @Expose
    private Picture fullScreenPicture;

    public Logo(Parcel in) {
        this.id = in.readString();
        this.orignalPicture = (Picture) in.readParcelable(Picture.class.getClassLoader());
        this.thumbnailPicture = (Picture) in.readParcelable(Picture.class.getClassLoader());
        this.fullScreenPicture = (Picture) in.readParcelable(Picture.class.getClassLoader());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Picture getOrignalPicture() {
        return orignalPicture;
    }

    public void setOrignalPicture(Picture orignalPicture) {
        this.orignalPicture = orignalPicture;
    }

    public Picture getThumbnailPicture() {
        return thumbnailPicture;
    }

    public void setThumbnailPicture(Picture thumbnailPicture) {
        this.thumbnailPicture = thumbnailPicture;
    }

    public Picture getFullScreenPicture() {
        return fullScreenPicture;
    }

    public void setFullScreenPicture(Picture fullScreenPicture) {
        this.fullScreenPicture = fullScreenPicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.thumbnailPicture,flags);
        dest.writeParcelable(this.orignalPicture,flags);
        dest.writeParcelable(this.fullScreenPicture,flags);
    }
}
