package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.dto.FacilityJoinDto;
import com.example.managerservice.service.FacilityService;
import com.example.managerservice.service.QRService;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

import static com.example.managerservice.constant.FacilityConstant.*;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FacilityController {

    private FacilityService facilityService;
    private QRService qrService;


    @Autowired
    public FacilityController(FacilityService facilityService, QRService qrService) {
        this.facilityService = facilityService;
        this.qrService = qrService;
    }

    /* 시설물 생성 */
    @PostMapping("/facility/register")
    public ResponseEntity registerFacility(@RequestBody FacilityDto facilityDto) throws IOException, WriterException {

        /* FacilityNo UUID로 부여 */
        facilityDto.setFacilityNo(UUID.randomUUID().toString());

        /* 매니저 계정인지 검사 */
        if(facilityService.validManager(facilityDto.getFacilityOwner()) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(REGISTER_VALID_MANAGER);
        }
        /* 중복된 시설물인지 검사 하기 */
        if(facilityService.validConflictFacility(facilityDto.getFacilityAddress()) == 1){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(REGISTER_CONFLICT_FACILITY);
        }
        /* QR 코드 생성 */
        facilityDto.setFacilityQrLocation(qrService.generateQRCodeImage(
                facilityDto.getFacilityNo(),
                facilityDto.getFacilityName(),
                facilityDto.getFacilityAddress()
        ));
        /* 시설물 정보 DB 저장 */
        facilityService.registerFacility(facilityDto);

        /* 시설물 조회 */
        FacilityDto fd = new FacilityDto();
        fd = facilityService.getFacilityInfo(facilityDto.getFacilityAddress());

        /* 시살물 가입 */
        facilityService.joinFacility(fd.getFacilityOwner(),fd.getFacilityNo(),"manager_use_facility");

        /* 매니저 소유 건물 등록 */
        facilityService.registerManager(fd.getFacilityOwner(),fd.getFacilityNo());

        return ResponseEntity.status(HttpStatus.CREATED).body(REGISTER_COMPLETE);
    }

    /* 시설물 삭제*/
    @GetMapping("/facility/delete/{managerUuid}/{facilityNo}")
    public ResponseEntity deleteFacility(@PathVariable("managerUuid") String managerUuid,
                                         @PathVariable("facilityNo") String facilityNo){
        return ResponseEntity.status(HttpStatus.OK).body(facilityService.deleteFacility(managerUuid, facilityNo));
    }

    /* 유저 혹은 매니저가 시설물 가입하기 */
    @PostMapping("/facility/join/{type}")
    public ResponseEntity joinFacility(@RequestBody FacilityJoinDto fd,
                                       @PathVariable("type")String type){
        String table;
        String uuidType;
        String boolColum;

        if(type.equals("mg")){
            table = FACILITY_JOIN_MANAGER;
            uuidType = FACILITY_JOIN_MANAGER_TYPE;
            boolColum = "manager";
            if(facilityService.joinValidFacility(fd.getUuid(),uuidType,boolColum) == 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_JOIN_USER_NOT_FOUND);
            }
        }else {
            table = FACILITY_JOIN_USER;
            uuidType = FACILITY_JOIN_USER_TYPE;
            boolColum = "user";
            if(facilityService.joinValidFacility(fd.getUuid(),uuidType,boolColum) == 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_JOIN_USER_NOT_FOUND);
            }
        }

        /* 가입할 시설물이 존재하는지 확인 */
        if (facilityService.validFacility(fd.getFacilityNo()) == 0){
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FACILITY_JOIN_NOT_FOUND);
        }
        /* 가입할 시설이 중복하지 않는지 확인 */
        if (facilityService.conflictJoinValidFacility(fd.getFacilityNo(), fd.getUuid(),uuidType, table) == 1 ){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_JOIN_CONFLICT);
        }

        /* 시설물 등록하기 */
        facilityService.joinFacility(fd.getUuid(),fd.getFacilityNo(),table);
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_JOIN_COMPLETE);
    }


    /* 내가 가입한 시설물 보기 - Manager & User */
    @GetMapping("/facility/join/{uuid}/{type}/list")
    public ResponseEntity getMyFacilityList(
            @PathVariable("uuid")String uuid,
            @PathVariable("type")String type){

        String table;
        String colum;

        if (type.equals("mg")){
            table = FACILITY_LIST_MANAGER_TABLE;
            colum = FACILITY_LIST_MANAGER_TYPE;
        }else{
            table = FACILITY_LIST_USER_TABLE;
            colum = FACILITY_LIST_USER_TYPE;
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(facilityService.getMyFacilityList(uuid,table, colum));

    }

    /* 내가 가입한 시설물 삭제 - Manager & User */
    @GetMapping("/facility/my/delete/{type}/{uuid}/{facilityNo}")
    public ResponseEntity deleteMyFacility(
            @PathVariable("type")String type,
            @PathVariable("uuid")String uuid,
            @PathVariable("facilityNo")String facilityNo){

        String table;
        String colum;

        if (type.equals("mg")){
            table = FACILITY_LIST_MANAGER_TABLE;
            colum = FACILITY_LIST_MANAGER_TYPE;
        }else{
            table = FACILITY_LIST_USER_TABLE;
            colum = FACILITY_LIST_USER_TYPE;
        }

        if(facilityService.validJoinFacility(uuid,facilityNo,table, colum) == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FACILITY_MY_DELETE_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(facilityService.deleteMyFacility(uuid,facilityNo,table, colum));

    }

    /* 내가 가입한 시설물 좋아요 누르기 */
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

