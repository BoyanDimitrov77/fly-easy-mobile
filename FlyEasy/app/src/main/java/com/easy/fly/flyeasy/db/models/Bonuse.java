package com.easy.fly.flyeasy.db.models;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

@Entity
public class Bonuse implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Bonuse createFromParcel(Parcel in) {
            return new Bonuse(in);
        }

        public Bonuse[] newArray(int size) {
            return new Bonuse[size];
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("userId")
    @Expose
    private long userId;
    @SerializedName("percent")
    @Expose
    private BigDecimal percent;
    @SerializedName("expiredDate")
    @Expose
    private String expiredDate;
    @SerializedName("isUsed")
    @Expose
    private boolean isUsed;

    public Bonuse(Parcel in) {

        this.id = in.readLong();
        this.userId = in.readLong();
        this.percent = BigDecimal.valueOf(in.readDouble());
        this.expiredDate = in.readString();
        this.isUsed = in.readInt() == 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.id);
        dest.writeLong(this.userId);
        dest.writeDouble(this.percent.doubleValue());
        dest.writeString(this.expiredDate);
        dest.writeInt(this.isUsed? 1: 0);
    }
}
