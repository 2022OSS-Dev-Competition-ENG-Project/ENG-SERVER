package com.example.facilityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "manager-service")
public interface ManagerServiceClient {

    /* 매니저 검증 */
    @GetMapping("/valid/manager/{managerUuid}")
    Integer getValidManager(@PathVariable String managerUuid);

    /* 시설물 가입 - 매니저 검색 ( OpenFeign ) */
    @GetMapping("/join/find/manager/{managerName}/{managerPhoneNumber}")
    String findJoinManager(@PathVariable String managerName,
                           @PathVariable String managerPhoneNumber);

}
