package com.easy.fly.flyeasy.db.models;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Bonuse {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("userId")
    @Expose
    private long userId;
    @SerializedName("percent")
    @Expose
    private long percent;
    @SerializedName("expiredDate")
    @Expose
    private String expiredDate;
    @SerializedName("isUsed")
    @Expose
    private boolean isUsed;

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

    public long getPercent() {
        return percent;
    }

    public void setPercent(long percent) {
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

}
