package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDataDto {
    private String userEmail;
    private String userNickname;
    private LocalDate userJoinDate;
    private String userProfileImage;

    public UserDataDto() {
        super();
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userJoinDate = userJoinDate;
        this.userProfileImage = userProfileImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserNickname(String userNickname) {
        return this.userNickname;
    }

    public LocalDate getUserJoinDate() {
        return userJoinDate;
    }

    public String getUserImg() {
        return userProfileImage;
    }
}
