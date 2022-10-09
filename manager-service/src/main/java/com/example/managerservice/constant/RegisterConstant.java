package com.example.managerservice.constant;

public class RegisterConstant {
    /* 회원가입 */

    public static final String REGISTER_COMPLETE = "정상적으로 회원가입 완료되었습니다.";
    public static final String REGISTER_PHONE_NUMBER_CONFLICT = "$phonnumber는 이미 회원가입된 전화번호입니다.";


    /* 이메일 중복 검사, 코드 발송 */
    public static final String EMAIL_CHECK_CLEAR = "사용 가능한 이메일입니다. \n입력하신 이메일로 인증코드가 발송되었습니다.";
    public static final String EMAIL_CHECK_FAIL = "사용중인 이메일입니다.";

    /* 이메일 코드 확인 */
    public static final String EMAIL_CODE_CLEAR = "이메일 인증이 완료 되었습니다.";
    public static final String EMAIL_CODE_FAIL = "잘못된 인증 코드 입니다.";

    /* 닉네임 중복 체크 */
    public static final String NICKNAME_CHECK_COMPLETE = "사용 가능한 닉네임입니다.";
    public static final String NICKNAME_CHECK_FAIL = "사용중인 닉네임입니다.";

    /* 로그인 */
    public static final String LOGIN_ID_FAIL = "아이디가 틀렸습니다.";
    public static final String LOGIN_PASSWORD_FAIL = "비밀번호가 틀렸습니다.";
    public static final String LOGIN_CLEAR = "사용중인 닉네임입니다.";

    /* Id/Password 찾기 */
    public static final String FIND_ID_PASSWORD_FAIL = "입력하신 정보가 없습니다.";
    public static final String FIND_RESET_PASSWORD = "비밀번호가 초기화 되었습니다\n이메일을 확인해주세요.";
}