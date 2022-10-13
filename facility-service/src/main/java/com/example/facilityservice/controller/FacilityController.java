package com.example.facilityservice.controller;


import com.example.facilityservice.service.FacilityService;
import com.example.facilityservice.vo.RequestChangeAddress;
import com.example.facilityservice.vo.RequestDeleteFacility;
import com.example.facilityservice.vo.RequestFacilityChangeName;
import com.example.facilityservice.vo.RequestFacilityRegister;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class FacilityController {


    private FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    /* 시설물 생성 */
    @PostMapping("/register")
    public ResponseEntity registerFacility(@RequestBody RequestFacilityRegister facility) throws IOException, WriterException {
        ResponseEntity responseEntity = facilityService.registerFacility(facility);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 수정 - 시설물 이름 */
    @PostMapping("/change/name")
    public ResponseEntity changeFacilityName(@RequestBody RequestFacilityChangeName changeName){
        ResponseEntity responseEntity = facilityService.facilityChangeName(changeName);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 시설물 수정 - 시설물 주소 */
    @PostMapping("/change/address")
    public ResponseEntity changeFacilityAddress(@RequestBody RequestChangeAddress changeAddress){
        ResponseEntity responseEntity = facilityService.facilityChangeAddress(changeAddress);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 삭제 */
    @PostMapping("/delete")
    public ResponseEntity deleteFacility(@RequestBody RequestDeleteFacility deleteFacility){
        ResponseEntity responseEntity = facilityService.deleteFacility(deleteFacility);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

}
