package com.easy.fly.flyeasy.dto;

import lombok.Data;

@Data
public class ChangeUserPasswordDto {

    private String oldPassword;
    private String newPassword;

    public ChangeUserPasswordDto(String oldPassword,String newPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
