package com.example.userservice.constant;

public class SignUpConstant {
    /* 회원가입 */
    public static final String SIGNUP_CLEAR = "회원가입 완료";
    public static final String SIGNUP_FAIL_PUN_OVERLAP = "이미 회원가입된 전화번호입니다.";

    /* 이메일 중복 검사, 코드 발송 */
    public static final String EMAIL_CHECK_CLEAR = "사용 가능한 이메일입니다. 인증코드가 발송 되었습니다.";
    public static final String EMAIL_CHECK_FAIL = "사용중인 이메일입니다.";

    /* 닉네임 중복 체크 */
    public static final String NICKNAME_CHECK_CLEAR = "사용 가능한 닉네임입니다.";
    public static final String NICKNAME_CHECK_FAIL = "사용중인 닉네임입니다.";

    /* 로그인 */
    public static final String LOGIN_ID_FAIL = "아이디가 틀렸습니다.";
    public static final String LOGIN_PASSWORD_FAIL = "비밀번호가 틀렸습니다.";

    /* Id/Password 찾기 */
    public static final String FIND_ID_FAIL = "입력하신 정보가 없습니다.";
    public static final String FIND_PASSWORD_EMAIL_FAIL = "이메일을 다시 확인해주세요.";
    public static final String FIND_PASSWORD_NAME_FAIL = "이름을 다시 확인해주세요.";
}