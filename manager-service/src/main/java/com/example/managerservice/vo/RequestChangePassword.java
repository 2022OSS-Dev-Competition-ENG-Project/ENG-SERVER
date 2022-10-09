package com.example.managerservice.vo;

import lombok.Data;

@Data
public class RequestChangePassword {
    private String managerUuid;
    private String currentPassword;
    private String changePassword;
}
