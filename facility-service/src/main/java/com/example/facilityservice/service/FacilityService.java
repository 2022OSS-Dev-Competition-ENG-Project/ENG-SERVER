package com.example.facilityservice.service;

import com.example.facilityservice.client.ManagerServiceClient;
import com.example.facilityservice.mapper.FacilityMapper;
import com.example.facilityservice.vo.RequestChangeAddress;
import com.example.facilityservice.vo.RequestDeleteFacility;
import com.example.facilityservice.vo.RequestFacilityChangeName;
import com.example.facilityservice.vo.RequestFacilityRegister;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.example.facilityservice.constant.FacilityConstant.*;

@Service
public class FacilityService {

    private FacilityMapper facilityMapper;
    private ManagerServiceClient managerServiceClient;
    private QrService qrService;

    @Autowired
    public FacilityService(FacilityMapper facilityMapper, ManagerServiceClient managerServiceClient, QrService qrService) {
        this.facilityMapper = facilityMapper;
        this.managerServiceClient = managerServiceClient;
        this.qrService = qrService;
    }

    /* 시설물 생성 */
    public ResponseEntity registerFacility(RequestFacilityRegister facility) throws IOException, WriterException {


        /* 매니저 계정인지 확인 */
        if (managerServiceClient.getValidManager(facility.getManagerUuid()) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(REGISTER_VALID_FAIL_MANAGER);
        }

        /* 주소 중복 검사 */
        if (facilityMapper.conflictValidAddress(facility.getFacilityAddress())==1){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(REGISTER_VALID_CONFLICT_ADDRESS);
        }

        /* FacilityUuid 설정 */
        facility.setFacilityNum(UUID.randomUUID().toString());

        /* QR 생성 */
        facility.setFacilityQr(qrService.generateQRCodeImage(
                facility.getFacilityNum(),
                facility.getFacilityName(),
                facility.getFacilityAddress()));

        /* 시설물 생성 */
        facilityMapper.registerFacility(facility);

        /* 생성한 시설물에 가입 */
        facilityMapper.rJoinFacility(facility);

        /* 생성한 시설물에 최초 관리자 등급 등록 */
        facilityMapper.setGrade(facility.getManagerUuid(), facility.getFacilityNum(), "관리자");

        return ResponseEntity.status(HttpStatus.OK).body(REGISTER_COMPLETE);
    }


    /* 시설물 수정 - 시설물 이름 */
    public ResponseEntity facilityChangeName(RequestFacilityChangeName changeData) {
        /* 시설물 수정 - 이름 중복 검사 */
        if(facilityMapper.conflictValidName(changeData.getChangeName()) == 0){
            facilityMapper.facilityChangeName(changeData);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CHANGE_NAME);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_CHANGE_NAME_CONFLICT);
        }
    }

    /* 시설물 수정 - 시설물 주소 */
    public ResponseEntity facilityChangeAddress(RequestChangeAddress changeData) {
        /* 시설물 수정 - 이름 중복 검사 */
        if(facilityMapper.conflictValidAddress(changeData.getChangeAddress()) == 0){
            facilityMapper.facilityChangeAddress(changeData);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CHANGE_ADDRESS);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_CHANGE_ADDRESS_CONFLICT);
        }
    }

    /* 시설물 삭제 */
    public ResponseEntity deleteFacility(RequestDeleteFacility deleteFacility) {
        /* 시설물 삭제 - 관리자 검증 */
        if (facilityMapper.validManagerGrade(deleteFacility.getFacilityNum(),deleteFacility.getManagerUuid()) == 1){
            facilityMapper.deleteFacility(deleteFacility);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_DELETE_COMPLETE);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_DELETE_FAIL);
        }
    }

}
