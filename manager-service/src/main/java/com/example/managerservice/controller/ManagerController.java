package com.example.managerservice.controller;


import com.example.managerservice.dto.Manager;
import com.example.managerservice.service.ManagerService;
import com.example.managerservice.vo.RequestChangePassword;
import com.example.managerservice.vo.RequestFindManagerId;
import com.example.managerservice.vo.RequestFindManagerPassword;
import com.example.managerservice.vo.RequestManagerLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ManagerController {

    @Autowired
    public ManagerController(ManagerService managerService, PasswordEncoder passwordEncoder){
        this.managerService = managerService;
        this.passwordEncoder = passwordEncoder;
    }
    private ManagerService managerService;
    private PasswordEncoder passwordEncoder;

    /* 매니저 회원가입 */
    @PostMapping("/register")
    public ResponseEntity registerManager(@RequestBody Manager manager) {
        ResponseEntity responseEntity = managerService.registerManager(manager);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* Manager 회원가입 - 이메일 중복 체크, 메일 발송*/
    @GetMapping("/register/check/email/{managerEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("managerEmail")String managerEmail){
        ResponseEntity responseEntity = managerService.emailConflictCheck(managerEmail);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* Manager 회원가입 - 인증 코드 확인 */
    @GetMapping("/register/check/email/{email}/{code}")
    public ResponseEntity emailCodeCheck(@PathVariable("email") String email,
                                         @PathVariable("code") String code) {
        ResponseEntity responseEntity = managerService.emailCodeCheck(email,code);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
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
    public ResponseEntity loginManager(@RequestBody RequestManagerLogin loginData) {
        ResponseEntity responseEntity = managerService.managerLogin(loginData);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 아이디 찾기 */
    @PostMapping ("/find/id")
    public ResponseEntity findManagerId (@RequestBody RequestFindManagerId requestFindManagerId){
        ResponseEntity responseEntity = managerService.findManagerId(requestFindManagerId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 비밀번호 찾기 - 비밀번호 초기화 */
    @PostMapping("/find/password")
    public ResponseEntity findManagerPassword (@RequestBody RequestFindManagerPassword managerData) {
        ResponseEntity responseEntity = managerService.findManagerPassword(managerData);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 마이페이지 - 비밀번호 변경 */
    @PostMapping("/myPage/change/password")
    public ResponseEntity changePassword(@RequestBody RequestChangePassword requestChangePassword) {
        ResponseEntity responseEntity = managerService.changeManagerPassword(requestChangePassword);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 매니저 검증 - OpenFeign */
    @GetMapping("/valid/manager/{managerUuid}")
    public Integer getValidManager(@PathVariable("managerUuid") String managerUuid){
        return managerService.getValidManager(managerUuid);
    }
}