package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.service.FacilityService;
import com.example.managerservice.service.QrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class QrController {

    private QrService qrService;
    private FacilityService facilityService;

    @Autowired
    public QrController(QrService qrService, FacilityService facilityService) {
        this.qrService = qrService;
        this.facilityService = facilityService;
    }

    @GetMapping(value = "/QR/getUrl",
            produces= MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRImage(@RequestBody FacilityDto fd){

        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        log.info("입력 데이터 : " + fd.getFacilityName() +", " + fd.getFacilityAddress());
        log.info("데이터 검색 시작");
        String fileDir = facilityService.nameAndAddressToDto(fd).getFacilityQrCode();


        try{
            fis = new FileInputStream(fileDir);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = fis.read(buffer)) != -1){
                baos.write(buffer, 0, readCount);
            }
            fileArray = baos.toByteArray();
            fis.close();
            baos.close();
        } catch(IOException e){
            throw new RuntimeException("File Error");
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileArray);
    }
}
