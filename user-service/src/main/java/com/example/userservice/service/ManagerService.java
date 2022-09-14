package com.example.userservice.service;

import com.example.userservice.dto.ManagerDto;
import com.example.userservice.mapper.ManagerMapper;
import com.example.userservice.vo.FindManagerIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ManagerService {
    @Autowired
    public ManagerService(ManagerMapper managerMapper, PasswordEncoder passwordEncoder) {
        this.managerMapper = managerMapper;
        this.passwordEncoder = passwordEncoder;
    }
    ManagerMapper managerMapper;
    PasswordEncoder passwordEncoder;

    /* Manger 회원가입 */
    public void SignupManager(ManagerDto managerDto) {
        managerDto.setManagerUuid(UUID.randomUUID().toString());
        String encodePassword = passwordEncoder.encode(managerDto.getManagerPassword());
        managerDto.setManagerPassword(encodePassword);
        managerMapper.SignupManager(managerDto);
    }

    /* Manager 회원가입 - 중복된 Email 검사하기 */
    public ManagerDto ManagerEmailCheck(String managerEmail){
        return managerMapper.ManagerEmailCheck(managerEmail);
    }

    /* Manager 회원가입 - Email 중복 검사 성공시 */
    public void  ManagerEmailConform(String managerEmail, String temporaryUuid){
        managerMapper.ManagerEmailConform(managerEmail,temporaryUuid);
    }

    /* Manager 회원가입 - Email 인증코드 검사 성공시 */
    public void ManagerEmailCode(String userEmail) {
        Integer LoginKey = 1;
        managerMapper.ManagerEmailCode(userEmail, LoginKey);
    }

    /* Manager 회원가입 - 중복된 Nickname 검사하기 */
    public ManagerDto ManagerNickNameCheck(String nickname){
        return managerMapper.ManagerNickNameCheck(nickname);
    }

    /* Manager 회원가입 - Nickname 검사 성공시 */
    public void NickNameCheckAccess(String managerNickname, String managerEmail){
        Integer AccessType = 1;
        managerMapper.NickNameCheckAccess(managerNickname, AccessType, managerEmail);
    }

    /* Manager 회원가입 - 중복된 PhoneNumber 검사하기 */
    public ManagerDto PhoneNumberCheck(String ResponsePhoneNum){
        return managerMapper.PhoneNumberCheck(ResponsePhoneNum);
    }

    /* Manager 로그인 */
    public ManagerDto findManagerEmail(ManagerDto managerDto) {
        return managerMapper.findManagerEmail(managerDto);
    }

    /* Manager 로그인 - 성공시 uuid 검색 */
    public ManagerDto findManagerName(ManagerDto managerDto) {
        return managerMapper.findManagerName(managerDto);
    }

    /* Manager 아이디 찾기 */
    public String findManagerId(FindManagerIdVo findManagerIdVo){
        return managerMapper.findManagerId(findManagerIdVo);
    }

    /* Manager 마이페이지 - 비빌번호 재설정 */
    public void changeManagerPW(ManagerDto managerDto) {
        String ChgPassword = passwordEncoder.encode(managerDto.getManagerPassword());
        managerDto.setManagerPassword(ChgPassword);
        managerMapper.changeManagerPW(managerDto);
    }

    /* Manager 마이페이지 - uuid를통해 유저 정보 수정 */
    public ManagerDto findManagerUuid(String uuid) {
        return managerMapper.findManagerUuid(uuid);
    }

    /* Manager 로그인 - 로그인 성공시 데이터 찾기 */
    public ManagerDto findManagerID(String managerEmail) {
        return managerMapper.findManagerID(managerEmail);
    }

    /* Manager 비밀번호 찾기- 랜덤 비밀번호 생성 */
    public void resetPassword (ManagerDto managerDto) {
        String encodePassword = passwordEncoder.encode(managerDto.getManagerPassword());
        managerDto.setManagerPassword(encodePassword);

        managerMapper.resetPassword(managerDto);
    }
}