package com.easy.fly.flyeasy.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */
@Data
public class UserDto {
    private int id;
    private String email;
    private String password;
    private String userName;
    private String fullName;
    private String birthDate;
    private String location;

    public UserDto(String email, String password, String userName, String fullName, String birthDate, String location) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.location = location;
    }
}
