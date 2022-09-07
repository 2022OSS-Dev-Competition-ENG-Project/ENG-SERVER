package com.example.managerservice.constant;

public class FacilityContentConstant {

    /* 게시물 등록 */
    public static final String FACILITY_CONTENT_CONFLICT = "이미 존재하는 등록 번호가 있습니다.";
    public static final String FACILITY_CONTENT_REGISTER_SUCCESS = "게시물이 정상적으로 등록되었습니다.";
    public static final String FACILITY_CONTENT_DELETE_VALID_FAIL= "게시물 작성자만 삭제 하실수 있습니다.";
    public static final String FACILITY_CONTENT_DELETE_VALID_COMPLETE = "게시물을 정상적으로 삭제 하였습니다.";
//    public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH = "/Users/jeonghunlee/image/content/";
    public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH = "/home/icslab/ENG-store/image/content/";
//    public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH_DB = "http://localhost:2200/api/facility/content/image/view/Users&jeonghunlee&image&content&";
    public static final String FACILITY_CONTENT_IMAGE_SAVE_PATH_DB = "http://203.250.32.29:2200/api/facility/content/image/view/home&icslab&ENG-store&image&content&";


    /* 게시물 메인에 보일 공지 게시물 갯수 */
    public static final Integer TAKE = 5;

    /* 게시물 조회 */
    public static final String FACILITY_CONTENT_VIEW_FAIL = "조회 하시려는 게시물이 존재하지 않습니다.";
//    public static final String FACILITY_CONTENT_VIEW_TABLE =


    /* 게시물 리스트 불러오기 */
    public static final String CONTENT_LIST_USER_TABLE = "user";
    public static final String CONTENT_LIST_USER_TYPE = "user_uuid";
    public static final String CONTENT_LIST_USER_VALUE = "user_name";
    public static final String CONTENT_LIST_MANAGER_TABLE = "manager";
    public static final String CONTENT_LIST_MANAGER_TYPE = "manager_uuid";
    public static final String CONTENT_LIST_MANAGER_VALUE = "manager_name";
    public static final Integer CONTENT_LIST_CUL = 10;

    /* 공지 사항 이미지 불러오기 */
    public static final String CONTENT_NOTICE_IMAGE_NOT_FOUND = "이미지를 찾을수 없습니다.";


    /* 댓글 */
    public static final String COMMENT_REGISTER_COMPLETE = "게시물에 정상적으로 댓글이 등록 되었습니다.";
    public static final String COMMENT_REGISTER_UPDATE = "게시물에 댓글이 수정되었습니다.";
    public static final String COMMENT_REGISTER_UPDATE_FAIL = "수정 할수 없는 댓글입니다.";
    public static final String COMMENT_DELETE_COMPLETE = "정상적으로 ";

    /* 게시물 좋아요 */
    public static final String COMMENT_LIKE = "해당 게시물을 좋아요 처리가 되었습니다.";
    public static final String COMMENT_LIKE_CANCEL = "해당 게시물을 좋아요 취소 처리가 되었습니다.";

}
