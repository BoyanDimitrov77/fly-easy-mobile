package com.easy.fly.flyeasy.db.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.easy.fly.flyeasy.dto.UserDto;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@Entity
@Builder
public class UserDB {
    @PrimaryKey
    @SerializedName("id")
    private int id;
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

    public UserDB(int id, String username, String email, String fullName, String profilePicture, String birthDate, String location){
        this.id=id;
        this.username=username;
        this.fullName=fullName;
        this.profilePicture = profilePicture;
        this.birthDate = birthDate;
        this.location = location;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
