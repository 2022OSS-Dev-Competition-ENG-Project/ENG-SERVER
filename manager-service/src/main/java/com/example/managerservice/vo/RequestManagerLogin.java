package com.example.managerservice.vo;

import lombok.Data;

@Data
public class RequestManagerLogin {
    private String managerEmail;
    private String managerPassword;
}
