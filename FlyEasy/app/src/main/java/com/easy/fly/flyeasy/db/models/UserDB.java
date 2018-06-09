package com.easy.fly.flyeasy.db.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.easy.fly.flyeasy.dto.UserDto;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.ToString;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@ToString
@Entity
@Builder
public class UserDB implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserDB createFromParcel(Parcel in) {
            return new UserDB(in);
        }

        public UserDB[] newArray(int size) {
            return new UserDB[size];
        }
    };
    @PrimaryKey
    @SerializedName("id")
    private long id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("profilePicture")
    private String profilePicture;
    @SerializedName("birthDate")
    private String birthDate;
    @SerializedName("location")
    private String location;

    public UserDB(long id, String username, String email, String fullName, String profilePicture, String birthDate, String location){
        this.id=id;
        this.username=username;
        this.email = email;
        this.fullName= fullName;
        this.profilePicture = profilePicture;
        this.birthDate = birthDate;
        this.location = location;
    }

    public UserDB(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.email = in.readString();
        this.fullName = in.readString();
        this.profilePicture = in.readString();
        this.birthDate = in.readString();
        this.location = in.readString();
    }

    public static UserDB of (UserDto user){
        return UserDB.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .fullName(user.getFullName())
                //.profilePicture(user.getProfilePicture())
                .birthDate(user.getBirthDate())
                .location(user.getLocation())
                .build();
    }

    public static UserDB of(User user){
        return UserDB.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .profilePicture(user.getProfilePicture() !=null ? user.getProfilePicture().getThumbnailPicture().getValue() :"")
                .birthDate(user.getBirthDate() !=null ? user.getBirthDate() : "" )
                .location(user.getLocation() !=null ? user.getLocation().getName() : "")
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.fullName);
        dest.writeString(this.profilePicture);
        dest.writeString(this.birthDate);
        dest.writeString(this.location);



    }
}
