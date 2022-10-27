package com.example.userservice.mapper;

import com.example.userservice.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /* User 회원가입 */
    void SignupUser(User userDto);

    /* USer 회원가입 - Email 중복 검사 */
    Integer UserEmailConform(@Param("userEmail") String userEmail);

    /* User 회원가입 - Nickname 중복 검사 */
    Integer UserNicknameConform(@Param("userNickname") String userNickname);

    /* User 회원가입 - 중복된 PhoneNumber 검사하기 */
    Integer PhoneNumberCheck(String userPhoneNumber);

    /* User 로그인 */
    User findEmail(User userDto);

    /* User 로그인 - 성공시 uuid 검색 */
    User findUuid(String userUuid);

    /* User 비밀번호 찾기 - 랜덤 비밀번호 생성 */
    void changeRandomPassword(User userDto);

    /* User 아이디 찾기 - Name 검색 */
    User FindEmail(User user);

    /* User 아이디 찾기 - Name으로 회원가입했는지 검사 */
    Integer UserNameConform(@Param("userName") String userName);

    /* User 마이페이지 - 비빌번호 재설정 */
    void changePW(User userDto);
}