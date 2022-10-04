package com.example.facilityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "manager-service")
public interface ManagerServiceClient {

    /* 매니저 검증 */
    @GetMapping("/manager-service/valid/manager/{managerId}")
    int getValidManager(@PathVariable("managerId") String managerId);
}
