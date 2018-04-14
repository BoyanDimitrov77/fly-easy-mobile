package com.easy.fly.flyeasy.db.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo {

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
}
