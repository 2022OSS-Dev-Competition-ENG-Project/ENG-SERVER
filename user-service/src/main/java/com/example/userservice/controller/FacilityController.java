package com.example.userservice.controller;

import com.example.userservice.dto.FacilityJoinMyDto;
import com.example.userservice.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.userservice.constant.FacilityConstant.FACILITY_MY_JOIN_FAIL;
import static com.example.userservice.constant.FacilityConstant.FACILITY_MY_JOIN_SUCCESS;

@RestController
@RequestMapping("/user-service")
public class FacilityController {

    private FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /* 내가 사용할 시설물 등록 */
    @PostMapping("/facility/join")
    public ResponseEntity myFacilityJoin(@RequestBody String uuid, String facilityNo) {
        try{
            if(facilityNo == facilityService.findFacility(facilityNo)){
                throw new Exception(FACILITY_MY_JOIN_FAIL);
            }
        }catch (NullPointerException e) {
            FacilityJoinMyDto fa = new FacilityJoinMyDto();
            fa.setUserFacility(facilityNo);
            fa.setUserId(uuid);
            facilityService.facilityJoinMy(fa);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_MY_JOIN_SUCCESS);
    }
}
