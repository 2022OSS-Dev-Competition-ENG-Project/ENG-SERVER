package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.dto.FacilityJoinDto;
import com.example.managerservice.service.FacilityService;
import com.example.managerservice.service.QrService;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

import static com.example.managerservice.constant.FacilityConstant.*;
import static com.example.managerservice.constant.FacilityContentConstant.FACILITY_MY_JOIN_FAIL;
import static com.example.managerservice.constant.RegisterConstant.REGISTER_CONFLICT;

@Slf4j
@RestController
@RequestMapping("/api")
public class FacilityController {

    private FacilityService facilityService;
    private QrService qrService;

    @Autowired
    public FacilityController(FacilityService facilityService, QrService qrService) {
        this.facilityService = facilityService;
        this.qrService = qrService;
    }

    /* 시설물 등록 */
    @PostMapping("/registerFacility")
    public ResponseEntity<String> registerFacility(@RequestBody FacilityDto facilityDto) throws IOException, WriterException {

        facilityDto.setFacilityNo(UUID.randomUUID().toString());
        try{
            /* 주소 중복 검사 */
            String address =facilityService.findDetailFacilityAd(facilityDto.getFacilityAddress());
            if (address != null){
                throw new Exception();
            }
            /* 사용 가능한 데이터라면 그냥 넘어가기 */
        }catch (NullPointerException e){
            log.info("사용 가능한 데이터");

            /* 등록할 주소가 이미 있을 경우 예외 처리*/
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(REGISTER_CONFLICT);
        }
        facilityDto.setFacilityQrCode(qrService.generateQRCodeImage(
                facilityDto.getFacilityNo(),
                facilityDto.getFacilityName(),
                facilityDto.getFacilityAddress()));
        return ResponseEntity.status(HttpStatus.CREATED).body(facilityService.registerFacility(facilityDto));
    }

    /* 내가 사용할 시설물 등록 */
    @PostMapping("/facility/join")
    public ResponseEntity myFacilityJoin(@RequestBody FacilityJoinDto fjd) {

        /* 시설물 존재 여부 확인 예외처리 */
        FacilityDto fd = facilityService.findDetailFacilityFn(fjd.getUserFacility());
        if (fd == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FACILITY_NOT_FOUND);
        }

        /* 사용자가 이미 등록한 시설물인지 검사 */
        if(facilityService.findDerailFacilityId(fjd.getUserUuid(),fjd.getUserFacility()) == 1){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_MY_JOIN_FAIL);
        }

        return ResponseEntity.status(HttpStatus.OK).body(facilityService.facilityUserJoin(
                fjd.getUserUuid(),
                fjd.getUserFacility()));
    }

    /* 내가 등록한 시설물 불러오기 */
    @GetMapping("/facility/{userUuid}/list")
    public ResponseEntity myFacilityList(
            @PathVariable("userUuid")String userUuid){
        try {
            facilityService.getMyFacilityList(userUuid);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FACILITY_LIST_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facilityService.getMyFacilityList(userUuid));
    }

    /* 내가 등록할 시설 삭제 하기 */
    @GetMapping("/facility/my/delete/{userUuid}/{facilityNo}")
    public ResponseEntity deleteMyFacility(@PathVariable("userUuid")String userUuid,
                                           @PathVariable("facilityNo")String facilityNo){
        return ResponseEntity.status(HttpStatus.OK).body(facilityService.deleteMyFacility(userUuid,facilityNo));
    }

    /* 내가 등록한 시설물 좋아요 누르기 */
    @GetMapping("/facility/like/{userUuid}/{facilityNo}")
    public ResponseEntity myfacilityLike(
            @PathVariable("userUuid") String userUuid,
            @PathVariable("facilityNo") String facilityNo){

        /*좋아요 여부 확인*/
        if(facilityService.myFacilityLikeBool(userUuid, facilityNo) == 0){
            facilityService.myFacilityLike(userUuid, facilityNo, 1);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_LIKE_COMPLETE);
        }else{
            facilityService.myFacilityLike(userUuid, facilityNo, 0);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_LIKE_CANCEL_COMPLETE);
        }
    }
}

