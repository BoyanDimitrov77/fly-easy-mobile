package com.easy.fly.flyeasy.db.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.easy.fly.flyeasy.dto.UserDto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@Entity
@Builder
public class User  {
    @PrimaryKey
    private int id;
    private String username;
    private String email;
    private String fullName;
    private String profilePicture;
    private String birthDate;
    private String location;

    public User( int id,String username,String email,String fullName, String profilePicture,String birthDate,String location){
        this.id=id;
        this.username=username;
        this.fullName=fullName;
        this.profilePicture = profilePicture;
        this.birthDate = birthDate;
        this.location = location;
    }

    public static User of (UserDto user){
        return User.builder()
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
