package com.example.managerservice.controller;


import com.example.managerservice.constant.MyPageConstant;
import com.example.managerservice.constant.SignUpConstant;
import com.example.managerservice.dto.Manager;
import com.example.managerservice.dto.ManagerDataDto;
import com.example.managerservice.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
@ControllerAdvice
public class ManagerController {

    @Autowired
    public ManagerController(ManagerService managerService, PasswordEncoder passwordEncoder){
        this.managerService = managerService;
        this.passwordEncoder = passwordEncoder;
    }
    private ManagerService managerService;
    private PasswordEncoder passwordEncoder;

    /* 매니저 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity Manager(@RequestBody Manager manager) {
        return ResponseEntity.status(HttpStatus.OK).body(managerService.SignupManager(manager));
    }

    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/register/check/email/{managerEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("managerEmail")String managerEmail) throws NullPointerException{
        return ResponseEntity.status(HttpStatus.OK).body(managerService.ManagerEmailConform(managerEmail));
    }

    /* 닉네임 중복 체크 */
    @GetMapping("/register/check/nickname/{nickname}/{email}")
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
    @GetMapping("/register/check/email/{email}/{code}")
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
    @PostMapping("/login")
    public Object AllManagers(@RequestBody Manager manager) {
        String ResponsePw = manager.getManagerPassword();
        String managerEmail = manager.getManagerEmail();
        String encodePassword = (managerService.findManagerEmail(manager)).getManagerPassword();
        ManagerDataDto managerDataDto = new ManagerDataDto();
        String UserId;
        try {
            UserId = managerService.findManagerEmail(manager).getManagerEmail();
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_ID_FAIL);
        }

        Integer managerAccessType = managerService.findManagerEmail(manager).getManagerAccessType();
        if (passwordEncoder.matches(ResponsePw,encodePassword) && managerAccessType == 1) {
            managerDataDto.setManagerName(managerService.findManagerID(managerEmail).getManagerName());
            managerDataDto.setManagerUuid(managerService.findManagerID(managerEmail).getManagerId());
            return managerDataDto;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_PASSWORD_FAIL);
        }
    }

    /* 매니저 아이디 찾기 */
    @PostMapping (value = "/FindManagerId")
    public ResponseEntity FindManagerId (@RequestBody Manager manager){
        if (managerService.findManagerId(manager) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_ID_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(managerService.findManagerId(manager));
    }

    /* 매니저 비밀번호 초기화(랜덤) */
    @PostMapping(value = "/ManagerPasswordReset")
    public ResponseEntity<String> FindManagerPassword (@RequestBody Manager manager) {
        String DBEmail;
        String DBName;
        String ChgManagerPassword = "aaaaa"; //userService.RandomObject();  SMTP 오류 해결시 랜덤비빌번호 설저 예정
        try {
            DBEmail = managerService.findManagerEmail(manager).getManagerEmail();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_EMAIL_FAIL);
        }

        try {
            DBName = managerService.findManagerName(manager).getManagerName();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_NAME_FAIL);
        }
        if (DBEmail != null && DBName != null) {
            manager.setManagerPassword(ChgManagerPassword);
            managerService.resetPassword(manager);
            return ResponseEntity.status(HttpStatus.OK).body(ChgManagerPassword);
        }
        return null;
    }

    /* 매니저 마이페이지 (비밀번호 재 설정) */
    @PostMapping("/myPage/changePW")
    public ResponseEntity ChangePW(@RequestBody Manager manager) {
        String uuid = manager.getManagerId();
        managerService.changeManagerPW(manager);
        String managerName = managerService.findManagerUuid(uuid).getManagerName();
        return ResponseEntity.status(HttpStatus.OK).body(managerName + MyPageConstant.MYPAGE_CLEAR);
    }

    /* 매니저 검증 - FacilityService를 위한 코드 */
    @GetMapping("/valid/manager/{managerId}")
    public Integer getValidManager(@PathVariable("managerId") String managerId){
        return managerService.getValidManager(managerId);
    }
}