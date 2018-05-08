package com.easy.fly.flyeasy.dto;

import lombok.Data;

@Data
public class UpdateUserInformationDto {

    private String fullName;

    private String location;

    private String email;

    private String birthDate;

    public UpdateUserInformationDto(String fullName,String location,String email,String birthDate){
        this.fullName = fullName;
        this.location = location;
        this.email = email;
        this.birthDate = birthDate;
    }

}
