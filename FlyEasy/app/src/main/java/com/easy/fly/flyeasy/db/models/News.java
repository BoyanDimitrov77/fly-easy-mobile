package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private long totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

    public News(Parcel in) {

        this.status = in.readString();
        this.totalResults = in.readLong();
        this.articles = (List<Article>) in.readArrayList(Article.class.getClassLoader());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeLong(this.totalResults);
        dest.writeArray(this.articles.toArray());
    }
}
