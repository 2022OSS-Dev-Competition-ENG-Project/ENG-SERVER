package com.example.facilityservice.constant;

public class FacilityConstant {

    /* 시설물 생성 */
    public static final String REGISTER_VALID_FAIL_MANAGER = "매니저의 계정만 시설물을 등록할수 있습니다.";
    public static final String REGISTER_VALID_CONFLICT_ADDRESS = "이미 등록 되어 있는 시설물입니다.";
    public static final String REGISTER_COMPLETE = "정상적으로 시설물이 등록되었습니다.";

    /* 시설물 수정 */
    public static final String FACILITY_CHANGE_NAME = "정상적으로 시설물 이름을 변경하였습니다.";
    public static final String FACILITY_CHANGE_NAME_FAIL = "이미 사용중인 이름입니다.";
    public static final String FACILITY_CHANGE_ADDRESS = "정상적으로 시설물 주소를 변경하였습니다";
    public static final String FACILITY_CHANGE_ADDRESS_FAIL = "시설물 주소 변경을 실패하였습니다.";

    /* 시설물 삭제 */
    public static final String FACILITY_DELETE_COMPLETE = "정상적으로 시설물을 삭제 하였습니다.";
    public static final String FACILITY_DELETE_FAIL = "시설물의 관리자만 삭제가 가능합니다.";

}
