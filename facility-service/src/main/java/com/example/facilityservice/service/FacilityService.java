package com.example.facilityservice.service;

import com.example.facilityservice.client.ManagerServiceClient;
import com.example.facilityservice.mapper.FacilityMapper;
import com.example.facilityservice.vo.RequestFacilityJoin;
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
        if (managerServiceClient.getValidManager(facility.getManagerId()) == 0){
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
        /* 시설물 생성과 동시에 FacilityJoin 동작이 같이 이루어 집니다. */
        /* FacilityJoin은 Logic 상이 아닌 다중 테이블 Insert를 사용하여 SQL에서 동작합니다.*/
        facilityMapper.registerFacility(facility);

        return ResponseEntity.status(HttpStatus.OK).body(REGISTER_COMPLETE);
    }
}
