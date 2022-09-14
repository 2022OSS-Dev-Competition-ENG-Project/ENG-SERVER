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

    /* 시설물 QR 불러오기 */
    @RequestMapping(value = "/facility/qr/getUrl", produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.POST)
    public ResponseEntity getQrLocation(@RequestBody GetQrLocationVo qv){
        String fileDir = qrService.getQRCode(qv);
        return ResponseEntity.status(HttpStatus.OK).body(fileDir);
    }
}
