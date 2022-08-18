package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.service.FacilityService;
import com.example.managerservice.service.QrService;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

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

        String address = new String();
        facilityDto.setFacilityNo(UUID.randomUUID().toString());
        try{
            address = facilityService.findDetailFacility(facilityDto.getFacilityAddress());
            if(facilityDto.getFacilityAddress() != null){
                throw new Exception(REGISTER_CONFLICT);
            }
        }catch (NullPointerException e){
            facilityDto.setFacilityQrCode(qrService.generateQRCodeImage(
                    facilityDto.getFacilityNo(),
                    facilityDto.getFacilityName(),
                    facilityDto.getFacilityAddress())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(facilityService.registerFacility(facilityDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

