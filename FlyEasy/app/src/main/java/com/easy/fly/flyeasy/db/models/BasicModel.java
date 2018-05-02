package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicModel implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BasicModel createFromParcel(Parcel in) {
            return new BasicModel(in);
        }

        public BasicModel[] newArray(int size) {
            return new BasicModel[size];
        }
    };

    @SerializedName("data")
    @Expose
    private String data;

    public BasicModel(Parcel in) {
        this.data = in.readString();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
    }
}
