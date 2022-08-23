package com.example.managerservice.service;

import com.example.managerservice.mapper.FacilityMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.managerservice.constant.QRCodeConstant.*;

@Slf4j
@Service
public class QrService {

    public String generateQRCodeImage(String id,String name,String address) throws WriterException, IOException {

        String savePath = SAVE_PATH;
        String url = QRCODE_GENERATE_URL
                .replaceAll("\\$id",id)
                .replaceAll("\\$name",name)
                .replaceAll("\\$address",address);

        log.info(url);

        String codeUrl = new String(url.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(codeUrl, BarcodeFormat.QR_CODE, QRCODE_HEIGHT, QRCODE_WIDTH);

        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF2e4e96,0xFFFFFFFF);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        File temp = new File(savePath + fileName + name + address +".png");

        ImageIO.write(bufferedImage,"png",temp);
        String saveDBUrl = savePath + fileName + name + address +".png";
        return saveDBUrl;
    }

}
