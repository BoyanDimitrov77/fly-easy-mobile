package com.easy.fly.flyeasy.dto;

import lombok.Data;

@Data
public class PassengerDto {

    private String passengerName;
    private String identificationNumber;
    private String email;

    public PassengerDto(String passengerName,String identificationNumber,String email){
        this.passengerName = passengerName;
        this.identificationNumber = identificationNumber;
        this.email = email;
    }
}
