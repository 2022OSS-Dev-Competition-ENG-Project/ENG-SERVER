package com.example.managerservice.service;


import com.example.managerservice.constant.SignUpConstant;
import com.example.managerservice.dto.Manager;
import com.example.managerservice.mapper.ManagerMapper;
import com.example.managerservice.vo.RequestChangePassword;
import com.example.managerservice.vo.RequestFindManagerPassword;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

import static com.example.managerservice.constant.MyPageConstant.*;
import static com.example.managerservice.constant.SignUpConstant.*;

@Slf4j
@Service
public class ManagerService {

    @Autowired
    public ManagerService(ManagerMapper managerMapper, PasswordEncoder passwordEncoder, EmailService emailService, Random random) {
        this.managerMapper = managerMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.random = random;
    }

    private ManagerMapper managerMapper;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private Random random;

    /* Manger 회원가입 */
    public ResponseEntity signupManager(Manager manager) {

        /* UUID 만들기*/
        manager.setManagerId(UUID.randomUUID().toString());
        /* 휴대폰 번호 중복 체크 */
        if(managerMapper.phoneNumberConflictCheck(manager.getManagerPhoneNumber()) == 0){
            /* 비밀 번호 암호화 */
            manager.setManagerPassword(passwordEncoder.encode(manager.getManagerPassword()));
            /* 회원 가입 */
            managerMapper.signupManager(manager);
            return ResponseEntity.status(HttpStatus.CREATED).body(SignUpConstant.SIGNUP_CLEAR);
            /* 휴대폰 번호 중복 발생시 */
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(SignUpConstant.SIGNUP_FAIL_PUN_OVERLAP
                            .replaceAll("\\$phonnumber",manager.getManagerPhoneNumber()));
        }
    }

    /* Manager 회원가입 - 이메일 중복 체크, 메일 발송*/
    public ResponseEntity emailConflictCheck(String managerEmail){

        /* 이메일 중복 예외 처리 */
        if (managerMapper.emailConflictCheck(managerEmail) == 0){
            /* 이메일 발송*/
            /* 이메일에 발송될 키 생성 */
            emailService.sendMail(managerEmail,Integer.toString(random.nextInt((int)Math.pow(10,6))),"EmailCheck");
            /**/
            return ResponseEntity.status(HttpStatus.OK).body(EMAIL_CHECK_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_CHECK_FAIL);
        }
    }

    /* Manager 회원가입 - 인증 코드 확인 */
    public void ManagerEmailCode(String userEmail) {
        Integer LoginKey = 1;
        managerMapper.managerEmailCode(userEmail, LoginKey);
    }

    /* Manager 회원가입 - 닉네임 중복 체크 */
    public ResponseEntity registerNicknameCheck(String nickname) {
        if (managerMapper.registerNicknameCheck(nickname) == 0){
            return ResponseEntity.status(HttpStatus.OK).body(NICKNAME_CHECK_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(NICKNAME_CHECK_FAIL);
        }
    }

    /* Manager 로그인 */
    public ResponseEntity managerLogin(Manager manager) {

        /* 비교할 매니저 생성 */
        Manager subManager = new Manager();

        /* manager 아이디 검사 예외 처리 */
        try {
            subManager = managerMapper.findManager(manager.getManagerEmail());
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LOGIN_ID_FAIL);
        }

        /* subManager 와 Manager 간에 Password 매치, subManager AccessType 검사*/
        if (passwordEncoder.matches(manager.getManagerPassword(),subManager.getManagerPassword()) && subManager.getManagerAccessType() == 1){
            return ResponseEntity.status(HttpStatus.OK).body(subManager);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_PASSWORD_FAIL);
        }
    }

    /* Manager 아이디 찾기 */
    public ResponseEntity findManagerId(String managerPhoneNumber, String managerName){
        String managerEmail = managerMapper.findManagerId(managerPhoneNumber, managerName);
        if(managerEmail.equals(null)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FIND_ID_PASSWORD_FAIL);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(managerEmail);
        }
    }

    /* 비밀번호 찾기 - 비밀번호 초기화 */
    public ResponseEntity findManagerPassword(RequestFindManagerPassword managerData) {
        if (managerMapper.findManagerPassword(managerData) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FIND_ID_PASSWORD_FAIL);
        }else {
            String resetPassword = RandomStringUtils.randomAlphanumeric(10);
            emailService.sendMail(managerData.getManagerEmail()
                    ,resetPassword
                    ,"resetPassword");
            managerMapper.resetPassword(managerData.getManagerEmail(),resetPassword);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FIND_RESET_PASSWORD);
        }
    }

    /* Manager 마이페이지 - 비밀번호 변경 */
    public ResponseEntity changeManagerPW(RequestChangePassword requestChangePassword) {
        /* 현재 비밀번호를 검증할 Sub Manager 생성 */
        Manager subManager = new Manager();

        /* 검증할 매니저 불러오기 */
        subManager = managerMapper.findManagerUuid(requestChangePassword.getManagerUuid());

        /* 현재 비밀번호 검증 */
        if (passwordEncoder.matches(requestChangePassword.getCurrentPassword(), subManager.getManagerPassword())){
            managerMapper.changeManagerPW(requestChangePassword);
            return ResponseEntity.status(HttpStatus.OK).body(PASSWORD_CHANGE_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASSWORD_CHANGE_FAIL);
        }
    }

    /* 매니저 검증 - OpenFeign */
    public Integer getValidManager(String managerId) {
        return managerMapper.getValidManager(managerId);
    }

}