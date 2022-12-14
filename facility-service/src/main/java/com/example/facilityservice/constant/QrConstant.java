package com.example.facilityservice.constant;

public class QrConstant {

    /* QR 코드 생성 셋팅 */
    public static final String QR_CODE_GENERATE_URL = "$id/$name/$address";
    public static final String QR_CODE_SAVE_PATH = "/home/eng2/ENG-STORAGE/images/qr/";
    public static final String QR_CODE_SAVE_PATH_DB = "http://jlchj.iptime.org:8000/facility-service/image/view/home&eng2&ENG-STORAGE&images&qr&";

//    /* */
//    public static final String QR_CODE_SAVE_PATH = "/Users/jeonghunlee/image/qr/";
//    public static final String QR_CODE_SAVE_PATH_DB = "http://jlchj.iptime.org:8000/facility-service/image/view/Users&jeonghunlee&image&qr&";

    public static final int QR_CODE_WIDTH = 200;
    public static final int QR_CODE_HEIGHT = 200;
    public static final int QR_CODE_COLOR = 0xFF2e4e96;
    public static final int QR_CODE_BACKGROUND_COLOR = 0xFFFFFFFF;

    public static final String NOT_FOUND_QR = "찾으시는 시설물의 QR 코드가 존재하지 않습니다.";

}
