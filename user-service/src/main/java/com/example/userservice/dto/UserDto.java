package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserDto {
    private String userUuid;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userNickname;
    private String userPhoneNum;
    private LocalDate userJoinDate;
    private Integer userLoginKey;
    private Integer userAccessType;
}