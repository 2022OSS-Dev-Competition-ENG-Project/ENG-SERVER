package com.example.managerservice.vo;

import lombok.Data;

@Data
public class RequestFindManagerPassword {
    private String managerEmail;
    private String managerName;
}
