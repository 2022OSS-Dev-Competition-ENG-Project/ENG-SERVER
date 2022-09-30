package com.example.managerservice.mapper;

import com.example.managerservice.dto.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManagerMapper {

    /* Manger 회원가입 */
    void SignupManager(Manager managerDto);

    /* Manager 회원가입 - 중복된 Email 검사하기 */
    Manager ManagerEmailCheck(String managerEmail);

    /* Manager 회원가입 - Email 중복 검사 성공시 */
    Integer ManagerEmailConform(@Param("managerEmail") String managerEmail);

    /* Manager 회원가입 - Email 인증코드 검사 성공시 */
    void ManagerEmailCode(String managerEmail, Integer LoginKey);

    /* Manager 회원가입 - 중복된 Nickname 검사하기 */
    Manager ManagerNickNameCheck(String nickname);

    /* Manager 회원가입 - Nickname 검사 성공시 */
    void NickNameCheckAccess(String nickName, Integer AccessType, String managerEmail);

    /* Manager 회원가입 - 중복된 PhoneNumber 검사하기 */
    Integer phoneNumberCheck(String managerPhoneNumber);

    /* Manager 로그인 */
    Manager findManagerEmail(Manager managerDto);

    /* Manager 로그인 - 성공시 uuid 검색 */
    Manager findManagerName(Manager managerDto);

    /* Manager 아이디 찾기 */
    /* managerEmail, managerName, managerPhoneNumber 필요 */
    String findManagerId(Manager manager);

    /* Manager 마이페이지 - 비빌번호 재설정 */
    void changeManagerPW(Manager managerDto);

    /* Manager 마이페이지 - uuid를통해 유저 정보 수정 */
    Manager findManagerUuid(String uuid);

    /* Manager 로그인 - 로그인 성공시 데이터 찾기 */
    Manager findManagerID(String managerEmail);

    /* Manager 비밀번호 찾기- 랜덤 비밀번호 생성 */
    void resetPassword(Manager managerDto);

}
