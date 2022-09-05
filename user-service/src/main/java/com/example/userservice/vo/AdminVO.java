package com.example.userservice.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AdminVO {
    private String managerUuid;
    private String managerName;
    private String managerPhoneNum;
    private Integer facilityGrade;
}
