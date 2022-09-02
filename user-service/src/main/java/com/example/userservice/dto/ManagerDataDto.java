package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDataDto {
    private String managerUuid;
    private String managerName;

    public ManagerDataDto() {
        super();
        this.managerUuid = managerUuid;
        this.managerName = managerName;
    }

    public String getManagerUuid() {
        return managerUuid;
    }

    public String managerName() {
        return managerName;
    }

}