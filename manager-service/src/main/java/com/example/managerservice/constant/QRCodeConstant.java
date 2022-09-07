package com.example.managerservice.constant;

public class QRCodeConstant {

    /* QR 코드 생성 셋팅 */
    public static final String QR_CODE_GENERATE_URL = "$id/$name/$address";
    public static final String QR_CODE_SAVE_PATH = "/home/icslab/ENG-store/image/QR/";
    public static final String QR_CODE_SAVE_PATH_DB = "http://203.250.32.29:2200/api/facility/content/image/view/home&icslab&ENG-store&image&QR&";
//    public static final String QR_CODE_SAVE_PATH = "/Users/jeonghunlee/qr";
//    public static final String QR_CODE_SAVE_PATH_DB = "http://203.250.32.29:2200/api/facility/content/image/view/Users&jeonghunlee&qr&";


    public static final int QR_CODE_WIDTH = 200;
    public static final int QR_CODE_HEIGHT = 200;
    public static final int QR_CODE_COLOR = 0xFF2e4e96;
    public static final int QR_CODE_BACKGROUND_COLOR = 0xFFFFFFFF;

    /* QR Code Location 불러오기 */
    public static final String QR_CODE_NOT_FOUND ="존재 하지 않는 QR 코드 입니다.";
}
