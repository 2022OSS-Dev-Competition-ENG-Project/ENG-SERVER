package com.example.managerservice.controller;

import com.example.managerservice.constant.QRCodeConstant;
import com.example.managerservice.service.FacilityService;
import com.example.managerservice.service.QrService;
import com.example.managerservice.vo.GetQRUrlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/QR/getUrl", produces= MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity getQRImage(@RequestBody GetQRUrlVo qv){

        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String fileDir = qrService.getQRCode(qv);

        log.info(fileDir);

        // facilityName, facilityAddress 가 존재하는지 여부
        if(fileDir == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(QRCodeConstant.QRCODE_NOT_FOUND);
        }

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
