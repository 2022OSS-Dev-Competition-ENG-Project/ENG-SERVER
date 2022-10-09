package com.example.managerservice.controller;


import com.example.managerservice.constant.MyPageConstant;
import com.example.managerservice.constant.SignUpConstant;
import com.example.managerservice.dto.Manager;
import com.example.managerservice.service.ManagerService;
import com.example.managerservice.vo.RequestChangePassword;
import com.example.managerservice.vo.RequestFindManagerPassword;
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
    public ResponseEntity registerManager(@RequestBody Manager manager) {
        ResponseEntity responseEntity = managerService.signupManager(manager);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* Manager 회원가입 - 이메일 중복 체크, 메일 발송*/
    /* 밑에 코드 수정 다 되면 작업 할것 */
    @GetMapping("/register/check/email/{managerEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("managerEmail")String managerEmail){
        return ResponseEntity.status(HttpStatus.OK).body(managerService.emailConflictCheck(managerEmail));
    }

    /* Manager 회원가입 - 인증 코드 확인 */
    /* 밑에 코드 수정 다 되면 작업 할것 */
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

    /* Manager 회원가입 - 닉네임 중복 체크 */
    @GetMapping("/register/check/nickname/{nickname}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("nickname")String nickname){
        ResponseEntity responseEntity = managerService.registerNicknameCheck(nickname);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* Manager 로그인 */
    @PostMapping("/login")
    public ResponseEntity AllManagers(@RequestBody Manager manager) {
        ResponseEntity responseEntity = managerService.managerLogin(manager);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 아이디 찾기 */
    @PostMapping (value = "/findManagerId")
    public ResponseEntity FindManagerId (@RequestBody Manager manager){
        ResponseEntity responseEntity = managerService.findManagerId(
                manager.getManagerPhoneNumber(),
                manager.getManagerName()
        );
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 비밀번호 찾기 - 비밀번호 초기화 */
    @PostMapping(value = "/ManagerPasswordReset")
    public ResponseEntity FindManagerPassword (@RequestBody RequestFindManagerPassword managerData) {
        ResponseEntity responseEntity = managerService.findManagerPassword(managerData);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 마이페이지 (비밀번호 재 설정) */
    @PostMapping("/myPage/changePW")
    public ResponseEntity ChangePW(@RequestBody RequestChangePassword requestChangePassword) {
        ResponseEntity responseEntity = managerService.changeManagerPW(requestChangePassword);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 검증 - OpenFeign */
    @GetMapping("/valid/manager/{managerId}")
    public Integer getValidManager(@PathVariable("managerId") String managerId){
        return managerService.getValidManager(managerId);
    }
}