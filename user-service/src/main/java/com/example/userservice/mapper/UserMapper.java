package com.example.userservice.mapper;

import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.FindIdVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /* User 회원가입 */
    void SignupUser(UserDto userDto);

    /* User 회원가입 - 중복된 Email 검사하기 */
    UserDto registerEmailCheck(String email);

    /* User 회원가입 - Email 중복 검사 성공시 */
    void EmailConform(String userEmail,String temporaryUuid);

    /* User 회원가입 - Email 인코드 검사 성공시 */
    void EmailCode(String userEmail, Integer LoginKey);

    /* User 회원가입 - 중복된 Nickname 검사하기 */
    UserDto registerNickNameCheck(String nickname);

    /* User 회원가입 - Nickname 검사 성공시 */
    void NickNameCheck(String nickName, Integer AccessType, String userEmail);

    /* User 로그인 */
    UserDto findEmail(UserDto userDto);

    /* User 로그인 - 성공시 uuid 검색 */
    UserDto findUuid(String uuid);

    /* User 비밀번호 찾기 - 랜덤 비밀번호 생성 */
    void changeRandomPassword(UserDto userDto);

    /* User 아이디 찾기 */
    String findId(FindIdVo findIdVo);

    /* User 마이페이지 - 비빌번호 재설정 */
    void changePW(UserDto userDto);
}