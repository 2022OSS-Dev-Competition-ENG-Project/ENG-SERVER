package com.example.facilityservice.controller;

import com.example.facilityservice.service.FacilityService;
import com.example.facilityservice.vo.RequestFacilityRegister;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("")
public class FacilityController {

    private FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    /* 시설물 생성 */
    @PostMapping("/facility/register")
    public ResponseEntity registerFacility(@RequestBody RequestFacilityRegister facility) throws IOException, WriterException {
        ResponseEntity responseEntity = new ResponseEntity<>(facilityService.registerFacility(facility).getStatusCode());
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 시설물 수정 */

    /* 시설물 삭제 */


}
