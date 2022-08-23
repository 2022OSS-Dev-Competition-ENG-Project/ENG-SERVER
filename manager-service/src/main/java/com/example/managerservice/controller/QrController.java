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
import java.util.Locale;

import static com.example.managerservice.constant.FacilityConstant.FACILITY_NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/api")
public class QrController {

    private QrService qrService;
    private FacilityService facilityService;

    String out;

    @Autowired
    public QrController(QrService qrService, FacilityService facilityService) {
        this.qrService = qrService;
        this.facilityService = facilityService;
    }

    @GetMapping(value = "/QR/getUrl", produces= MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity getQRImage(@RequestBody FacilityDto fd){

        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        String fileDir;
        try{
            facilityService.nameAndAddressToDto(fd).getFacilityQrCode();
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FACILITY_NOT_FOUND);
        }
        fileDir = facilityService.nameAndAddressToDto(fd).getFacilityQrCode();

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
