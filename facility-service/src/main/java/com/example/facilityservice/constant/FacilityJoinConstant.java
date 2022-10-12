package com.example.facilityservice.constant;

public class FacilityJoinConstant {
    /* 시설물 가입 */
    public static final String FACILITY_JOIN_CONFLICT = "이미 가입하신 시설물입니다.";
    public static final String FACILITY_JOIN_COMPLETE = "정상적으로 시설물에 가입 되었습니다.";

    /* 시설물 탈퇴 */
    public static final String FACILITY_RESIGNATION_COMPLETE = "정상적으로 시설물에서 탈퇴되었습니다.";
    public static final String FACILITY_RESIGNATION_FAIL = "이미 탈퇴 하였거나 시설물에 속해있지 않기 때문에 탈퇴 할수 없습니다.";

    /* 내가 가입한 시설물 */
    public static final String GET_MY_FACILITY_NOT_FOUND = "등록한 시설물이 없습니다.";

    /* 시설물 좋아요 */
    public static final String FACILITY_LIKE_COMPLETE = "좋아요 처리가 완료되었습니다.";
    public static final String FACILITY_LIKE_CANCEL_COMPLETE = "좋아요 취소 처리가 완료되었습니다.";
}
