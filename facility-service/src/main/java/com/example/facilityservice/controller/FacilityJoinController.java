package com.example.facilityservice.controller;

import com.example.facilityservice.service.FacilityJoinService;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.RequestResignationFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class FacilityJoinController {

    private FacilityJoinService facilityJoinService;

    @Autowired
    public FacilityJoinController(FacilityJoinService facilityJoinService) {
        this.facilityJoinService = facilityJoinService;
    }

    /* 시설물 가입 - Manger */
    @PostMapping("/facility/join/manager")
    public ResponseEntity joinFacilityManager(@RequestBody RequestJoinFacility joinFacility){
        ResponseEntity responseEntity = facilityJoinService.joinFacility(joinFacility,"facility_join_manager");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 가입 - User */
    @PostMapping("/facility/join/user")
    public ResponseEntity joinFacilityUser(@RequestBody RequestJoinFacility joinFacility){
        ResponseEntity responseEntity = facilityJoinService.joinFacility(joinFacility,"facility_join_user");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 탈퇴 - Manger */
    @GetMapping("/facility/resignation/manager")
    public ResponseEntity resignationFacilityManager(@RequestBody RequestResignationFacility resignationFacility){
        ResponseEntity responseEntity = facilityJoinService.resignationFacility(resignationFacility,"facility_join_manager");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 탈퇴 - User */
    @GetMapping("/facility/resignation/user")
    public ResponseEntity resignationFacilityUser(@RequestBody RequestResignationFacility resignationFacility){
        ResponseEntity responseEntity = facilityJoinService.resignationFacility(resignationFacility,"facility_join_user");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 가입한 시설물 불러오기 - Manager */
    @GetMapping("/facility/join/{managerUuid}/list")
    public ResponseEntity getManagerFacilityList(@PathVariable("managerUuid")String managerUuid){
        ResponseEntity responseEntity = facilityJoinService.getMyFacilityList(managerUuid, "facility_join_manager");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 가입한 시설물 불러오기 - User */
    @GetMapping("/facility/join/{userUuid}/list")
    public ResponseEntity getUserFacilityList(@PathVariable("userUuid")String managerUuid){
        ResponseEntity responseEntity = facilityJoinService.getMyFacilityList(managerUuid, "facility_join_user");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 가입한 시설 좋아요 - User */
    @GetMapping("/facility/join/like/{userUuid}/{facilityNum}")
    public ResponseEntity myFacilityLike(@PathVariable("userUuid")String userUuid,
                                         @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityJoinService.myFacilityLike(userUuid, facilityNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


}
