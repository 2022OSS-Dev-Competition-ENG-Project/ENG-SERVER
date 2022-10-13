package com.example.facilityservice.controller;

import com.example.facilityservice.service.FacilityJoinService;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.RequestResignationFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class FacilityJoinController {

    private FacilityJoinService facilityJoinService;

    @Autowired
    public FacilityJoinController(FacilityJoinService facilityJoinService) {
        this.facilityJoinService = facilityJoinService;
    }

    /* 시설물 가입 - Manger */
    @PostMapping("/join/manager")
    public ResponseEntity joinFacilityManager(@RequestBody RequestJoinFacility joinFacility){
        ResponseEntity responseEntity = facilityJoinService.joinFacility(joinFacility,"facility_join_manager", "manager_uuid");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 가입 - User */
    @PostMapping("/join/user")
    public ResponseEntity joinFacilityUser(@RequestBody RequestJoinFacility joinFacility){
        ResponseEntity responseEntity = facilityJoinService.joinFacility(joinFacility,"facility_join_user", "user_uuid");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 탈퇴 - Manger */
    @PostMapping("/resignation/manager")
    public ResponseEntity resignationFacilityManager(@RequestBody RequestResignationFacility resignationFacility){
        ResponseEntity responseEntity = facilityJoinService.resignationFacility(resignationFacility,"facility_join_manager", "manager_uuid");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 탈퇴 - User */
    @GetMapping("/resignation/user")
    public ResponseEntity resignationFacilityUser(@RequestBody RequestResignationFacility resignationFacility){
        ResponseEntity responseEntity = facilityJoinService.resignationFacility(resignationFacility,"facility_join_user", "user_uuid");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 가입한 시설물 불러오기 - Manager */
    @GetMapping("/join/{managerUuid}/manager/list")
    public ResponseEntity getManagerFacilityList(@PathVariable("managerUuid") String managerUuid){
        ResponseEntity responseEntity = facilityJoinService.getMyFacilityList(managerUuid, "facility_join_manager", "manager_uuid","manager");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 가입한 시설물 불러오기 - User */
    @GetMapping("/join/{userUuid}/user/list")
    public ResponseEntity getUserFacilityList(@PathVariable("userUuid")String userUuid){
        ResponseEntity responseEntity = facilityJoinService.getMyFacilityList(userUuid, "facility_join_user", "user_uuid","user");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 가입한 시설 좋아요 - User */
    @GetMapping("/join/like/{userUuid}/{facilityNum}")
    public ResponseEntity myFacilityLike(@PathVariable("userUuid")String userUuid,
                                         @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityJoinService.myFacilityLike(userUuid, facilityNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


}
