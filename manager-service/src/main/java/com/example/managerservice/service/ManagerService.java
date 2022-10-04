package com.example.managerservice.service;


import com.example.managerservice.constant.SignUpConstant;
import com.example.managerservice.dto.Manager;
import com.example.managerservice.mapper.ManagerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.managerservice.constant.SignUpConstant.*;

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
    public ResponseEntity SignupManager(Manager manager) {

        /* 매니저 최초 가입 시간 */

        /* UUID 만들기*/
        manager.setManagerId(UUID.randomUUID().toString());
        /* 휴대폰 번호 중복 체크 */
        if(managerMapper.phoneNumberCheck(manager.getManagerPhoneNumber()) == 0){
            /* 비밀 번호 암호화 */
            manager.setManagerPassword(passwordEncoder.encode(manager.getManagerPassword()));
            managerMapper.SignupManager(manager);
            return ResponseEntity.status(HttpStatus.CREATED).body(SignUpConstant.SIGNUP_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(SignUpConstant.SIGNUP_FAIL_PUN_OVERLAP
                            .replaceAll("\\$phonnumber",manager.getManagerPhoneNumber()));
        }
    }

    /* Manager 회원가입 - 중복된 Email 검사하기 */
    public Manager ManagerEmailCheck(String managerEmail){
        return managerMapper.ManagerEmailCheck(managerEmail);
    }

    /* Manager 회원가입 - Email 중복 검사 */
    /* SMTP 를 통해 이메일 발송 */
    public ResponseEntity ManagerEmailConform(String managerEmail){
        Integer conform = managerMapper.ManagerEmailConform(managerEmail);
        if (conform == 0){
            /* 이메일 발송*/
//
            /**/
            return ResponseEntity.status(HttpStatus.OK).body(EMAIL_CHECK_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_CHECK_FAIL);
        }
    }

    /* Manager 회원가입 - Email 인증코드 검사 성공시 */
    public void ManagerEmailCode(String userEmail) {
        Integer LoginKey = 1;
        managerMapper.ManagerEmailCode(userEmail, LoginKey);
    }

    /* Manager 회원가입 - 중복된 Nickname 검사하기 */
    public Manager ManagerNickNameCheck(String nickname){
        return managerMapper.ManagerNickNameCheck(nickname);
    }

    /* Manager 회원가입 - Nickname 검사 성공시 */

    public void NickNameCheckAccess(String managerNickname, String managerEmail){
        Integer AccessType = 1;
        managerMapper.NickNameCheckAccess(managerNickname, AccessType, managerEmail);
    }

    /* Manager 로그인 */
    public Manager findManagerEmail(Manager manager) {
        return managerMapper.findManagerEmail(manager);
    }

    /* Manager 로그인 - 성공시 uuid 검색 */
    public Manager findManagerName(Manager manager) {
        return managerMapper.findManagerName(manager);
    }

    /* Manager 아이디 찾기 */
    public String findManagerId(Manager manager){
        return managerMapper.findManagerId(manager);
    }

    /* Manager 마이페이지 - 비빌번호 재설정 */
    public void changeManagerPW(Manager manager) {
        String ChgPassword = passwordEncoder.encode(manager.getManagerPassword());
        manager.setManagerPassword(ChgPassword);
        managerMapper.changeManagerPW(manager);
    }

    /* Manager 마이페이지 - uuid를통해 유저 정보 수정 */
    public Manager findManagerUuid(String uuid) {
        return managerMapper.findManagerUuid(uuid);
    }

    /* Manager 로그인 - 로그인 성공시 데이터 찾기 */
    public Manager findManagerID(String managerEmail) {
        return managerMapper.findManagerID(managerEmail);
    }

    /* Manager 비밀번호 찾기- 랜덤 비밀번호 생성 */
    public void resetPassword (Manager manager) {
        String encodePassword = passwordEncoder.encode(manager.getManagerPassword());
        manager.setManagerPassword(encodePassword);

        managerMapper.resetPassword(manager);
    }

    /* 매니저 검증 */
    public Integer getValidManager(String managerId) {
        managerMapper.getValidManager(managerId);
    }
}