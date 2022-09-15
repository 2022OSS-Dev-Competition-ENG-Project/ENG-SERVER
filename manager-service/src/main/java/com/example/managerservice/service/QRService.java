package com.example.managerservice.service;

import com.example.managerservice.mapper.QrMapper;
import com.example.managerservice.vo.GetQrLocationVo;
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
public class QRService {

    private QrMapper qrMapper;
    private ImageService imageService;

    @Autowired
    public QRService(QrMapper qrMapper, ImageService imageService) {
        this.qrMapper = qrMapper;
        this.imageService = imageService;
    }

    /* QR 코드 생성 */
    public String generateQRCodeImage(String id, String name, String address) throws WriterException, IOException {


        String url = QR_CODE_GENERATE_URL
                .replaceAll("\\$id", id)
                .replaceAll("\\$name", name)
                .replaceAll("\\$address", address);

        String codeUrl = new String(url.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(codeUrl, BarcodeFormat.QR_CODE, QR_CODE_HEIGHT, QR_CODE_WIDTH);

        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(QR_CODE_COLOR, QR_CODE_BACKGROUND_COLOR);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        File temp = new File(QR_CODE_SAVE_PATH + fileName + name + address + ".png");

//        imageService.saveQRImage(bufferedImage,temp);
        ImageIO.write(bufferedImage, "png", temp);
        String saveDBUrl = QR_CODE_SAVE_PATH_DB + fileName + name + address + ".png";
        return saveDBUrl;
    }

    /* QR 불러오기 */
    public String getQRCode(GetQrLocationVo qv) {
        log.info(qv.toString());
        return qrMapper.getQRCode(qv.getFacilityName(), qv.getFacilityAddress());
    }
}
