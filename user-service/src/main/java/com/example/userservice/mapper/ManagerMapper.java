package com.example.userservice.mapper;

import com.example.userservice.dto.ManagerDto;
import com.example.userservice.vo.FindManagerIdVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {
    /* Manger 회원가입 */
    void SignupManager(ManagerDto managerDto);

    /* Manager 회원가입 - 중복된 Email 검사하기 */
    ManagerDto ManagerEmailCheck(String managerEmail);

    /* Manager 회원가입 - Email 중복 검사 성공시 */
    void ManagerEmailConform(String managerEmail,String temporaryUuid);

    /* Manager 회원가입 - Email 인증코드 검사 성공시 */
    void ManagerEmailCode(String managerEmail, Integer LoginKey);

    /* Manager 회원가입 - 중복된 Nickname 검사하기 */
    ManagerDto ManagerNickNameCheck(String nickname);

    /* Manager 회원가입 - Nickname 검사 성공시 */
    void NickNameCheckAccess(String nickName, Integer AccessType, String managerEmail);

    /* Manager 회원가입 - 중복된 PhoneNumber 검사하기 */
    ManagerDto PhoneNumberCheck(String managerPhoneNumber);

    /* Manager 로그인 */
    ManagerDto findManagerEmail(ManagerDto managerDto);

    /* Manager 로그인 - 성공시 uuid 검색 */
    ManagerDto findManagerName(ManagerDto managerDto);

    /* Manager 아이디 찾기 */
    String findManagerId(FindManagerIdVo findManagerIdVo);

    /* Manager 마이페이지 - 비빌번호 재설정 */
    void changeManagerPW(ManagerDto managerDto);

    /* Manager 마이페이지 - uuid를통해 유저 정보 수정 */
    ManagerDto findManagerUuid(String uuid);

    /* Manager 로그인 - 로그인 성공시 데이터 찾기 */
    ManagerDto findManagerID(String managerEmail);

    /* Manager 비밀번호 찾기- 랜덤 비밀번호 생성 */
    void resetPassword(ManagerDto managerDto);
}