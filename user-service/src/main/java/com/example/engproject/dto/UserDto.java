package com.example.engproject.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    String UserUuid;
    String UserEmail;
    String UserPassword;
    String UserVerifyPassword;
    String UserName;
    String UserNickname;
    String UserPhoneNum;
    Date UserBirth;
    String UserType;
    String UserLoginKey;
}
