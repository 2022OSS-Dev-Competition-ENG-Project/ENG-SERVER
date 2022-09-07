package com.example.managerservice.constant;


public class FacilityConstant {

    /* 시설물 등록 Constant */
    public static final String REGISTER_VALID_MANAGER = "매니저의 계정만 시설물을 등록할수 있습니다.";
    public static final String REGISTER_CONFLICT_FACILITY = "입력하신 주소로 이미 등록된 시설이 있습니다.";
    public static final String REGISTER_COMPLETE = "정상적으로 시설물이 등록되었습니다.";

    /* 시설물 가입 Constant */
    public static final String FACILITY_JOIN_NOT_FOUND = "가입 하실려는 시설물은 존재하지 않습니다.";
    public static final String FACILITY_JOIN_USER_NOT_FOUND = "해당 서비스를 이용하실수 없습니다.";
    public static final String FACILITY_JOIN_CONFLICT = "이미 가입하신 시설물입니다.";
    public static final String FACILITY_JOIN_USER = "user_use_facility";
    public static final String FACILITY_JOIN_MANAGER = "manager_use_facility";
    public static final String FACILITY_JOIN_USER_TYPE = "user_uuid";
    public static final String FACILITY_JOIN_MANAGER_TYPE = "manager_uuid";
    public static final String FACILITY_JOIN_COMPLETE = "시설물에 정상적으로 가입되셨습니다.";

    /* 가입된 시설물 리스트 & 삭제 Constant */
    public static final String FACILITY_LIST_USER_TYPE = "user_uuid";
    public static final String FACILITY_LIST_MANAGER_TYPE = "manager_uuid";
    public static final String FACILITY_LIST_USER_TABLE = "user_use_facility";
    public static final String FACILITY_LIST_MANAGER_TABLE = "manager_use_facility";
    public static final String FACILITY_MY_DELETE_NOT_FOUND = "삭제 하려는 게시물이 없습니다.";
    public static final String FACILITY_MY_DELETE = "정상적으로 가입한 시설물이 삭제 되었습니다.";

    /* 가입된 시설물 좋아요 */
    public static final String FACILITY_LIKE_COMPLETE = "좋아요 처리가 완료되었습니다.";
    public static final String FACILITY_LIKE_CANCEL_COMPLETE = "좋아요 취소 처리가 완료되었습니다.";

}
