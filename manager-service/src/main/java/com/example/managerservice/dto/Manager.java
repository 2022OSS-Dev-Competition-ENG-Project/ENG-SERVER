package com.example.managerservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Manager {
    /* 매니저 고유 번호 */
    private String managerId;

    /* 매니저 이메일 */
    private String managerEmail;

    /* 매니저 비밀번호 */
    private String managerPassword;

    /* 매니저 이름 */
    private String managerName;

    /* 매니저 닉네임 */
    private String managerNickname;

    /* 매니저 휴대번호 */
    private String managerPhoneNumber;

    /* 매니저 가입 날짜 */
    private Date managerJoinDate;

    /* 매니저 로그인 키 */
    private Integer managerLoginKey;

    /* 매니저 Access 타입 */
    private Integer managerAccessType;
}
