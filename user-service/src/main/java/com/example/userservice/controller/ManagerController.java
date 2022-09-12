package com.example.userservice.controller;

import com.example.userservice.constant.MyPageConstant;
import com.example.userservice.constant.SignUpConstant;
import com.example.userservice.dto.*;
import com.example.userservice.service.*;
import com.example.userservice.vo.FindManagerIdVo;
import com.example.userservice.dto.ManagerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
@ControllerAdvice
public class ManagerController {
    @Autowired
    public ManagerController(UserService userService, ManagerService managerService, PasswordEncoder passwordEncoder){
        this.managerService = managerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    private ManagerService managerService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    /* 매니저 회원가입 */
    @PostMapping("/manager-service/signup")
    public ResponseEntity<String> Manager(@RequestBody ManagerDto managerDto) {
        String ResponseEmail = managerDto.getManagerEmail();
        String ResponsePhoneNum = managerDto.getManagerPhoneNum();
        String DBPhoneNum;
        try { DBPhoneNum = managerService.PhoneNumberCheck(ResponsePhoneNum).getManagerPhoneNum();
        } catch (NullPointerException e) {
            Integer LoginKeyCheck = managerService.ManagerEmailCheck(ResponseEmail).getManagerLoginKey();
            Integer AccessType = managerService.ManagerEmailCheck(ResponseEmail).getManagerAccessType();
            if ( LoginKeyCheck == 1 && AccessType == 1) {
                managerService.SignupManager(managerDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(SignUpConstant.SIGNUP_CLEAR);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.SIGNUP_FAIL_CONTENT);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponsePhoneNum + SignUpConstant.SIGNUP_FAIL_PUN_OVERLAP);
    }

    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/manager-service/register/check/email/{managerEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("managerEmail")String managerEmail) throws NullPointerException{
        try {
            managerService.ManagerEmailCheck(managerEmail);
        } catch (NullPointerException e) {
            String temporaryUuid = userService.RandomObject();
            managerService.ManagerEmailConform(managerEmail,temporaryUuid);
            return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.EMAIL_CHECK_CLEAR);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(SignUpConstant.EMAIL_CHECK_FAIL);
    }

    /* 닉네임 중복 체크 */
    @GetMapping("/manager-service/register/check/nickname/{nickname}/{email}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("nickname")String nickname,
            @PathVariable("email")String email){
        try {
            managerService.ManagerNickNameCheck(nickname);
        } catch (NullPointerException e) {
            managerService.NickNameCheckAccess(nickname,email);
            return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.NICKNAME_CHECK_CLEAR);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(SignUpConstant.NICKNAME_CHECK_FAIL);
    }

    /* 이메일 코드 확인 */
    @GetMapping("/manager-service/register/check/email/{email}/{code}")
    public ResponseEntity emailCheck(@PathVariable("email") String email,
                                     @PathVariable("code") String code) {
        String temporaryKey = "11111"; // SMTP 해결시 RedisServer을 통하여 코드 수정 예정
        if (Integer.parseInt(temporaryKey) == Integer.parseInt(code)){
            managerService.ManagerEmailCode(email);
            return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.EMAIL_CODE_CLEAR);
        } else {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(SignUpConstant.EMAIL_CODE_FAIL);
        }
    }

    /* 매니저 로그인 */
    @PostMapping("/manager-service/login")
    public Object AllManagers(@RequestBody ManagerDto managerDto) {
        String ResponsePw = managerDto.getManagerPassword();
        String managerEmail = managerDto.getManagerEmail();
        String encodePassword = (managerService.findManagerEmail(managerDto)).getManagerPassword();
        ManagerDataDto managerDataDto = new ManagerDataDto();
        String UserId;
        try {
            UserId = managerService.findManagerEmail(managerDto).getManagerEmail();
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_ID_FAIL);
        }

        Integer managerAccessType = managerService.findManagerEmail(managerDto).getManagerAccessType();
        if (passwordEncoder.matches(ResponsePw,encodePassword) && managerAccessType == 1) {
            managerDataDto.setManagerName(managerService.findManagerID(managerEmail).getManagerName());
            managerDataDto.setManagerUuid(managerService.findManagerID(managerEmail).getManagerUuid());
            return managerDataDto;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_PASSWORD_FAIL);
        }
    }

    /* 매니저 아이디 찾기 */
    @PostMapping (value = "/manager-service/FindManagerId")
    public ResponseEntity FindManagerId (@RequestBody FindManagerIdVo findManagerIdVo){
        /* 이름 전화 번호가 한컬럼 안에서 다를 때 */
        try{
            log.info(managerService.findManagerId(findManagerIdVo));
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_ID_FAIL);
        }

        if (managerService.findManagerId(findManagerIdVo) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_ID_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(managerService.findManagerId(findManagerIdVo));
    }

    /* 매니저 비밀번호 초기화(랜덤) */
    @PostMapping(value = "/manager-service/ManagerPasswordReset")
    public ResponseEntity<String> FindManagerPassword (@RequestBody ManagerDto managerDto) {
        String DBEmail;
        String DBName;
        String ChgManagerPassword = "aaaaa"; //userService.RandomObject();  SMTP 오류 해결시 랜덤비빌번호 설저 예정
        try {
            DBEmail = managerService.findManagerEmail(managerDto).getManagerEmail();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_EMAIL_FAIL);
        }

        try {
            DBName = managerService.findManagerName(managerDto).getManagerName();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_NAME_FAIL);
        }
        if (DBEmail != null && DBName != null) {
            managerDto.setManagerPassword(ChgManagerPassword);
            managerService.resetPassword(managerDto);
            return ResponseEntity.status(HttpStatus.OK).body(ChgManagerPassword);
        }
        return null;
    }

    /* 매니저 마이페이지 (비밀번호 재 설정) */
    @GetMapping("/manager-service/myPage/changePW/{uuid}")
    public ResponseEntity ChangePW(@PathVariable("uuid") String uuid,
                                   @RequestBody ManagerDto managerDto) {
        managerDto.setManagerUuid(uuid);
        managerService.changeManagerPW(managerDto);
        String managerName = managerService.findManagerUuid(uuid).getManagerName();
        return ResponseEntity.status(HttpStatus.OK).body(managerName + MyPageConstant.MYPAGE_CLEAR);
    }
}