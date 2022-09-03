package com.example.userservice.dto;

import lombok.Data;
import java.time.LocalDate;


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
    private String userImg;
}