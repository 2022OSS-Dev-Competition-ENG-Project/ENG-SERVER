package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ManagerDto {
    private String managerUuid;
    private String managerEmail;
    private String managerPassword;
    private String managerName;
    private String managerNickname;
    private String managerPhoneNum;
    private Date managerJoinDate;
    private String facilityNo;
    private Integer facilityGrade;
    private Integer managerLoginKey;
    private Integer managerAccessType;
}
