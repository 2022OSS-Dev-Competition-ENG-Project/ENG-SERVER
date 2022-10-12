package com.example.managerservice.mapper;

import com.example.managerservice.dto.Manager;
import com.example.managerservice.vo.RequestChangePassword;
import com.example.managerservice.vo.RequestFindManagerId;
import com.example.managerservice.vo.RequestFindManagerPassword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManagerMapper {

    /* Manger 회원가입 */
    void registerManager(Manager managerDto);

    /* Manager 회원가입 - PhoneNumber 중복 검사 */
    Integer phoneNumberConflictCheck(@Param("managerPhoneNumber") String managerPhoneNumber);

    /* Manager 회원가입 - Email 중복 검사 */
    Integer emailConflictCheck(@Param("managerEmail") String managerEmail);

    /* Manager 회원가입 - 닉네임 중복 체크 */
    Integer registerNicknameCheck(@Param("managerNickname") String managerNickname);

    /* Manager 로그인 - Email 검증 */
    Manager findManager(@Param("managerEmail") String managerEmail);

    /* Manager 아이디 찾기 */
    String findManagerId(RequestFindManagerId requestFindManagerId);

    /* 비밀번호 찾기 */
    Integer findManagerPassword(RequestFindManagerPassword managerData);

    /* 비밀번호 찾기 - 초기화 된 비밀번호 변경 */
    void resetPassword(@Param("managerEmail") String managerEmail,
                       @Param("resetPassword") String resetPassword);

    /* Manager 마이페이지 - 비빌번호 재설정 */
    void changeManagerPassword(RequestChangePassword requestChangePassword);

    /* Manager 마이페이지 - uuid를통해 유저 정보 수정 */
    Manager findManagerUuid(@Param("managerUuid") String managerUuid);

    /* 매니저 검증 */
    Integer getValidManager(@Param("managerUuid") String managerUuid);

}
