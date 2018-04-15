package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Payment implements Parcelable{


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("amount")
    @Expose
    private BigDecimal amount;
    @SerializedName("discount")
    @Expose
    private BigDecimal discount;
    @SerializedName("status")
    @Expose
    private String status;

    public Payment(Parcel in) {

        this.id = in.readLong();
        this.user = (User) in.readParcelable(User.class.getClassLoader());
        this.amount = BigDecimal.valueOf(in.readDouble());
        this.discount = BigDecimal.valueOf(in.readDouble());
        this.status = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.user,flags);
        dest.writeDouble(this.amount.doubleValue());
        dest.writeDouble(this.discount.doubleValue());
        dest.writeString(this.status);
    }
}
