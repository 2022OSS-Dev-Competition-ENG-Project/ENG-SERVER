package com.example.managerservice.service;


import com.example.managerservice.constant.RegisterConstant;
import com.example.managerservice.dto.Manager;
import com.example.managerservice.mapper.ManagerMapper;
import com.example.managerservice.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.managerservice.constant.EmailConstant.SMTP_EMAIL_CODE_CHECK_COMPLETE;
import static com.example.managerservice.constant.EmailConstant.SMTP_EMAIL_CODE_CHECK_NOT_COMPLETE;
import static com.example.managerservice.constant.GradeConstant.GRADE_CHANGE_FAIL;
import static com.example.managerservice.constant.GradeConstant.GRADE_CHANGE_FAIL_CONFLICT;
import static com.example.managerservice.constant.MyPageConstant.PASSWORD_CHANGE_CLEAR;
import static com.example.managerservice.constant.MyPageConstant.PASSWORD_CHANGE_FAIL;
import static com.example.managerservice.constant.RegisterConstant.*;

@Slf4j
@Service
public class ManagerService {

    @Autowired
    public ManagerService(ManagerMapper managerMapper, PasswordEncoder passwordEncoder, EmailService emailService, RedisService redisService) {
        this.managerMapper = managerMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.redisService = redisService;
    }

    private ManagerMapper managerMapper;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private RedisService redisService;

    /* Manger 회원가입 */
    public ResponseEntity registerManager(Manager manager) {

        /* UUID 만들기*/
        manager.setManagerUuid(UUID.randomUUID().toString());
        manager.setManagerJoinDate(LocalDateTime.now());
        /* 휴대폰 번호 중복 체크 */
        if(managerMapper.phoneNumberConflictCheck(manager.getManagerPhoneNumber()) == 0){
            /* 비밀 번호 암호화 */
            manager.setManagerPassword(passwordEncoder.encode(manager.getManagerPassword()));
            /* 회원 가입 */
            managerMapper.registerManager(manager);
            return ResponseEntity.status(HttpStatus.CREATED).body(RegisterConstant.REGISTER_COMPLETE);
            /* 휴대폰 번호 중복 발생시 */
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(RegisterConstant.REGISTER_PHONE_NUMBER_CONFLICT
                            .replaceAll("\\$phonnumber",manager.getManagerPhoneNumber()));
        }
    }

    /* Manager 회원가입 - 이메일 중복 체크, 메일 발송*/
    public ResponseEntity emailConflictCheck(String managerEmail){
        /* 이메일에 발송될 키 생성 */
        String key = RandomStringUtils.randomNumeric(6);
        log.info("==== 이메일 발송 Start===");
        /* 이메일 중복 예외 처리 */
        if (managerMapper.emailConflictCheck(managerEmail) == 0){
            /* 이메일 발송*/
            emailService.sendMail(managerEmail,key,"EmailCheck");
            log.info("==== 이메일 발송 END===");
            return ResponseEntity.status(HttpStatus.OK).body(EMAIL_CHECK_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_CHECK_FAIL);
        }
    }

    /* Manager 회원가입 - 인증 코드 확인 */
    public ResponseEntity emailCodeCheck(String managerEmail, String code) {
        /* Redis에 저장된 키값의 Value를 가져온다.*/
        String key = redisService.getData(managerEmail);
        if (key == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SMTP_EMAIL_CODE_CHECK_NOT_COMPLETE);
        if(Integer.parseInt(key) == Integer.parseInt(code)){
            redisService.deleteData(managerEmail);
            return ResponseEntity.status(HttpStatus.OK).body(SMTP_EMAIL_CODE_CHECK_COMPLETE);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SMTP_EMAIL_CODE_CHECK_NOT_COMPLETE);
        }
    }

    /* Manager 회원가입 - 닉네임 중복 체크 */
    public ResponseEntity registerNicknameCheck(String nickname) {
        if (managerMapper.registerNicknameCheck(nickname) == 0){
            return ResponseEntity.status(HttpStatus.OK).body(NICKNAME_CHECK_COMPLETE);
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(NICKNAME_CHECK_FAIL);
        }
    }

    /* Manager 로그인 */
    public ResponseEntity managerLogin(RequestManagerLogin loginData) {

        /* 비교할 매니저 생성 */
        Manager subManager;

        /* Manager 로그인 - Email 검증 */
        subManager = managerMapper.findManager(loginData.getManagerEmail());
        if (subManager == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LOGIN_ID_FAIL);
        }

        /* subManager 와 Manager 간에 Password 매치, subManager AccessType 검사*/
        if (passwordEncoder.matches(loginData.getManagerPassword(),subManager.getManagerPassword())){
            ResponseLogin responseLogin = new ResponseLogin();
            responseLogin.setManagerUuid(subManager.getManagerUuid());
            responseLogin.setManagerName(subManager.getManagerName());
            return ResponseEntity.status(HttpStatus.OK).body(responseLogin);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RegisterConstant.LOGIN_PASSWORD_FAIL);
        }
    }

    /* Manager 아이디 찾기 */
    public ResponseEntity findManagerId(RequestFindManagerId requestFindManagerId){
        String managerEmail = managerMapper.findManagerId(requestFindManagerId);
        if (managerEmail == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FIND_ID_PASSWORD_FAIL);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(managerEmail);
        }
    }

    /* 비밀번호 찾기 - 비밀번호 초기화 */
    public ResponseEntity findManagerPassword(RequestFindManagerPassword managerData) {
        if (managerMapper.findManagerPassword(managerData) == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FIND_ID_PASSWORD_FAIL);
        }else {
            String resetPassword = RandomStringUtils.randomAlphanumeric(10);
            emailService.sendMail(managerData.getManagerEmail()
                    ,resetPassword
                    ,"resetPassword");
            managerMapper.resetPassword(managerData.getManagerEmail(),passwordEncoder.encode(resetPassword));
            return ResponseEntity.status(HttpStatus.OK).body(FIND_RESET_PASSWORD);
        }
    }

    /* Manager 마이페이지 - 비밀번호 변경 */
    public ResponseEntity changeManagerPassword(RequestChangePassword requestChangePassword) {
        /* 현재 비밀번호를 검증할 Sub Manager 생성 */
        Manager subManager;

        /* 검증할 매니저 불러오기 */
        subManager = managerMapper.findManagerUuid(requestChangePassword.getManagerUuid());

        /* 현재 비밀번호 검증 */
        if (passwordEncoder.matches(requestChangePassword.getCurrentPassword(), subManager.getManagerPassword())){
            requestChangePassword.setChangePassword(passwordEncoder.encode(requestChangePassword.getChangePassword()));
            managerMapper.changeManagerPassword(requestChangePassword);
            return ResponseEntity.status(HttpStatus.OK).body(PASSWORD_CHANGE_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASSWORD_CHANGE_FAIL);
        }
    }

    /* 시설물 가입 - 매니저 검색 ( OpenFeign ) */
    public String findJoinManager(String managerName, String managerPhoneNumber){
        return managerMapper.findJoinManager(managerName, managerPhoneNumber);
    }

    /* 매니저 검증 - OpenFeign */
    public Integer getValidManager(String managerUuid) {
        return managerMapper.getValidManager(managerUuid);
    }

    /* 매니저 시설물에서 직급 변경 */
    public ResponseEntity changeGrade(String uuid,String managerUuid, String facilityNum,String grade) {

        String facilityOwner = managerMapper.findFacilityOwner(facilityNum);

        /* 시설물 주인의 uuid 불러오기 */
        if(facilityOwner.equals(managerUuid)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한이 없습니다");
        }

        /* 변경 하려는 사용자의 직급 불러오기*/
        String masterGrade = managerMapper.findManagerGrade(uuid,facilityNum);
        if (!masterGrade.equals("마스터")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GRADE_CHANGE_FAIL);
        }

        /* 직급 불러오기 */
        String subGrade = managerMapper.findManagerGrade(managerUuid,facilityNum);
        if(subGrade == grade){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(GRADE_CHANGE_FAIL_CONFLICT);
        }

        managerMapper.changeGrade(managerUuid,facilityNum,grade);
        return ResponseEntity.status(HttpStatus.OK).body("등급이 정상적으로 변경되었습니다.");
    }
}