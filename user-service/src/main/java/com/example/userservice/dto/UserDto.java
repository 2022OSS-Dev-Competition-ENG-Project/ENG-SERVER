package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String userUuid;
    private String userEmail;
    private String userPassword;
    private String userVerifyPassword;
    private String userName;
    private String userNickname;
    private String userPhoneNum;
    private Date userBirth;
    private String userType;
    private Integer userLoginKey;
    private Integer userAccessType;
}
