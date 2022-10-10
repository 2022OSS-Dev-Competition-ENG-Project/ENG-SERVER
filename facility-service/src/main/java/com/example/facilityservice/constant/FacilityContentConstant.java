package com.example.facilityservice.constant;

public class FacilityContentConstant {

    /* 게시물 등록 */
    public static final String FACILITY_CONTENT_CREATE = "게시물이 정상적으로 등록되었습니다.";
    /* 게시물 등록 */
    public static final String FACILITY_NOTICE_CREATE = "게시물이 정상적으로 등록되었습니다.";
//        public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH = "/Users/jeonghunlee/image/content/";
    public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH = "/home/eng2/ENG-STORE/image/content/";
//        public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH_DB = "http://jlchj.iptime.org:1001/api/facility/content/image/view/Users&jeonghunlee&image&content&";
    public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH_DB = "http://203.250.32.29:2200/api/facility/content/image/view/home&icslab&ENG-store&image&content&";

    /* 게시물 삭제*/
    public static final String FACILITY_CONTENT_DELETE_VALID_COMPLETE = "게시물을 정상적으로 삭제 하였습니다.";
    public static final String FACILITY_CONTENT_DELETE_VALID_FAIL= "게시물 작성자만 삭제 하실수 있습니다.";

    /* 게시물 삭제 - Manager*/
    public static final String FACILITY_CONTENT_MANAGER_DELETE_COMPLETE = "게시물을 정상적으로 삭제 하였습니다.";
    public static final String FACILITY_CONTENT_MANAGER_VALID_FAIL = "삭제를 시도하는 계정이 매니저의 계정이거나, 시설물에 속해 있어야 합니다.";

    /* 게시물 좋아요 */
    public static final String COMMENT_LIKE = "해당 게시물을 좋아요 처리가 되었습니다.";
    public static final String COMMENT_LIKE_CANCEL = "해당 게시물을 좋아요 취소 처리가 되었습니다.";


}