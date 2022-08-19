package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String UserUuid;
    private String UserEmail;
    private String UserPassword;
    private String UserVerifyPassword;
    private String UserName;
    private String UserNickname;
    private int UserPhoneNum;
    private Date UserBirth;
    private String UserType;
    private String UserLoginKey;

}
