package com.example.facilityservice.controller;

import com.example.facilityservice.service.FacilityJoinService;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.RequestResignationFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
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
        ResponseEntity responseEntity = facilityJoinService.joinFacility(joinFacility,"facility_join_manager", "manager_uuid",1);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 가입 - User */
    @PostMapping("/join/user")
    public ResponseEntity joinFacilityUser(@RequestBody RequestJoinFacility joinFacility){
        ResponseEntity responseEntity = facilityJoinService.joinFacility(joinFacility,"facility_join_user", "user_uuid",0);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 시설물 탈퇴 - Manger */
    @GetMapping("/resignation/manager{facilityNum}/{managerUuid}")
    public ResponseEntity resignationFacilityManager(@PathVariable("facilityNum") String facilityNum,
                                                     @PathVariable("managerUuid")String managerUuid){
        ResponseEntity responseEntity = facilityJoinService.resignationFacility(facilityNum,managerUuid,"facility_join_manager", "manager_uuid");
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 탈퇴 - User */
    @GetMapping("/resignation/user/{facilityNum}/{userUuid}")
    public ResponseEntity resignationFacilityUser(@PathVariable("facilityNum") String facilityNum,
                                                  @PathVariable("userUuid")String userUuid){
        ResponseEntity responseEntity = facilityJoinService.resignationFacility(facilityNum,userUuid,"facility_join_user", "user_uuid");
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

    /* 시설물 가입 - 매니저 검색 */
    @GetMapping("/join/find/manager/{managerName}/{managerPhoneNumber}")
    public ResponseEntity findJoinManager(@PathVariable("managerName")String managerName,
                                          @PathVariable("managerPhoneNumber")String managerPhoneNumber){
        ResponseEntity responseEntity = facilityJoinService.findJoinManager(managerName,managerPhoneNumber);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

    }

}
