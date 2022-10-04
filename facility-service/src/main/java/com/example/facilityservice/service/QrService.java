package com.example.facilityservice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.facilityservice.constant.QrConstant.*;

@Service
public class QrService {
    /* QR 코드 생성 */
    public String generateQRCodeImage(String facilityNum, String facilityName, String facilityAddress) throws WriterException, IOException {

        String url = QR_CODE_GENERATE_URL
                .replaceAll("\\$id", facilityNum)
                .replaceAll("\\$name", facilityName)
                .replaceAll("\\$address", facilityAddress);

        String codeUrl = new String(url.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(codeUrl, BarcodeFormat.QR_CODE, QR_CODE_HEIGHT, QR_CODE_WIDTH);

        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(QR_CODE_COLOR, QR_CODE_BACKGROUND_COLOR);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        File temp = new File(QR_CODE_SAVE_PATH + fileName + facilityName + facilityAddress + ".png");

        ImageIO.write(bufferedImage, "png", temp);
        String saveDBUrl = QR_CODE_SAVE_PATH_DB + fileName + facilityName + facilityAddress + ".png";
        return saveDBUrl;
    }
}
