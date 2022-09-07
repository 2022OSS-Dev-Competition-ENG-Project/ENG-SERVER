package com.example.managerservice.controller;

import com.example.managerservice.service.QRService;
import com.example.managerservice.vo.GetQrLocationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class QrController {

    private QRService qrService;

    @Autowired
    public QrController(QRService qrService) {
        this.qrService = qrService;
    }

    @RequestMapping(value = "/facility/qr/getUrl", produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.POST)
    public ResponseEntity getQrLocation(@RequestBody GetQrLocationVo qv){
//        FileInputStream fis = null;
//        ByteArrayOutputStream baos = new cByteArrayOutputStream();
        String fileDir = qrService.getQRCode(qv);

//        // facilityName, facilityAddress 가 존재하는지 여부
//        if(fileDir == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(QR_CODE_NOT_FOUND);
//        }
//
//        try{
//            fis = new FileInputStream(fileDir);
//        } catch(FileNotFoundException e){
//            e.printStackTrace();
//        }
//
//        int readCount = 0;
//        byte[] buffer = new byte[1024];
//        byte[] fileArray = null;
//
//        try{
//            while((readCount = fis.read(buffer)) != -1){
//                baos.write(buffer, 0, readCount);
//            }
//            fileArray = baos.toByteArray();
//            fis.close();
//            baos.close();
//        } catch(IOException e){
//            throw new RuntimeException("File Error");
//        }
        return ResponseEntity.status(HttpStatus.OK).body(fileDir);
    }
}
