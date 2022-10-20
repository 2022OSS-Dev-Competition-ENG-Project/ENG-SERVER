package com.example.facilityservice.controller;

import com.example.facilityservice.service.QrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("")
public class ImageController {



    @Autowired
    public ImageController(QrService qrService) {
        this.qrService = qrService;
    }

    private QrService qrService;

    /* 시설물 QR 불러오기 */
    @GetMapping("/find/qr/{facilityNum}")
    public ResponseEntity findQr(@PathVariable("facilityNum") String facilityNum){
        ResponseEntity responseEntity = qrService.findQr(facilityNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 공지사항 이미지 불러오기 통합 */
    @GetMapping(value = "/image/view/{Path}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity getImage(@PathVariable("Path") String path) throws IOException {

        /* savePath에서 & 문자 모두 /로 치환 */
        String savePath =  "/" + path.replaceAll("&","/");
        Resource resource = new FileSystemResource(savePath);

        if (!resource.exists())
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        HttpHeaders headers = new HttpHeaders();
        Path filePath = null;

        try {
            filePath = Paths.get(savePath);

            headers.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e){
            e.getStackTrace();
        }
        return new ResponseEntity<>(resource,headers,HttpStatus.OK);
    }
}
