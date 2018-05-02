package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("enabled")
    @Expose
    private boolean enabled;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("profilePicture")
    @Expose
    private PictureResolution profilePicture;
    @SerializedName("bonuses")
    @Expose
    private List<Bonuse> bonuses = null;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("location")
    @Expose
    private LocationModel location;
    @SerializedName("name")
    @Expose
    private String name;

    public User(Parcel in) {
        this.id = in.readLong();
        this.email = in.readString();
        this.userName = in.readString();
        this.fullName = in.readString();
        this.enabled = in.readInt() == 1;
        this.timestamp = in.readString();
        this.profilePicture = (PictureResolution) in.readParcelable(PictureResolution.class.getClassLoader());
        this.bonuses = (List<Bonuse>)in.readArrayList(Bonuse.class.getClassLoader());
        this.birthDate = in.readString();
        this.location = (LocationModel) in.readParcelable(LocationModel.class.getClassLoader());
        this.name = in.readString();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public PictureResolution getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(PictureResolution profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Bonuse> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonuse> bonuses) {
        this.bonuses = bonuses;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getEmail();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.id);
        dest.writeString(this.email);
        dest.writeString(this.userName);
        dest.writeString(this.fullName);
        dest.writeInt(this.enabled? 1 :0);
        dest.writeString(this.timestamp);
        dest.writeParcelable(this.profilePicture,flags);
        dest.writeArray(this.bonuses.toArray());
        dest.writeString(this.birthDate);
        dest.writeParcelable(this.location,flags);
        dest.writeString(this.name);

    }
}
