package com.example.userservice.controller;


import com.example.userservice.dto.UserDto;
import com.example.userservice.service.EmailService;
import com.example.userservice.service.SecurityService;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, SecurityService securityService, EmailService emailService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.emailService = emailService;
    }

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;
    private EmailService emailService;
    String token;


    @PostMapping("/user-service/signup")
    public ResponseEntity<String> retrieveAllUser(@RequestBody UserDto userDto) {
        log.info("Signup : 시도");
        String LoginKeyCheck = userService.findEmail(userDto).getUserLoginKey();
        if ( LoginKeyCheck == "1" ) {
            userService.SignupUser(userDto);
            log.info("Signup : 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } else {
            log.info("Signup : 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        }
    }

    @GetMapping("/user-service/check/email/{email}")
    public ResponseEntity<String> EmailConform(@PathVariable("email")String email) throws NullPointerException {
        UserDto userDto = userService.EmailConform(email);
        if (userDto == null) {
            emailService.sendMail(email);
            return ResponseEntity.status(HttpStatus.OK).body("사용가능한 Email입니다. \n 인증코드가 발송 되었습니다.");
        } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용불가능한 Email입니다.");
        }
    }
//    @GetMapping("/user/certification/conform/{email}")
//    public String conform(@PathVariable("email")String email) {
//        userService.conform(email);
//        log.info("Certification : 이메일 인증");
//        return "인증되었습니다.";
//    }

    @PostMapping("/user-service/login")
    public ResponseEntity<String> retrieveAllUsers(@RequestBody UserDto userDto) {
        String ResponsePw = userDto.getUserPassword();
        String encodePassword;
        String ResponseId = userDto.getUserEmail();
        String UserName;
        try {
            UserName = userService.findEmail(userDto).getUserEmail();
        }
        catch (NullPointerException e){
            log.info("Login : 아이디 틀림");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 틀렸습니다.");
        }
        int phoneNum = userService.findEmail(userDto).getUserPhoneNum();
        String loginkey = userService.findEmail(userDto).getUserLoginKey();
        try {
            encodePassword = (userService.findEmail(userDto)).getUserPassword();
        }
        catch (NullPointerException e1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 되지 않은 유저입니다.");
        }

        log.info("Login : 로그인 시도");

        if (passwordEncoder.matches(ResponsePw,encodePassword) && loginkey != "1") {
            log.info("Login : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else if (passwordEncoder.matches(ResponsePw,encodePassword) && loginkey == "1") {
            token = securityService.createToken(ResponseId,(30*60*1000),phoneNum,UserName);
            log.info("Login : 로그인 성공, 토큰 발급");
            return ResponseEntity.status(HttpStatus.OK).body("환영합니다!" + token);
        } else {
            log.info("Login : 비밀번호가 틀렸습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다.");
        }
    }



    // 사용자가 등록한 시설물 리스트 출력
//    @GetMapping("/shop/name/search/")
//    public List<UserFacilityDto> UserFacilityList(UserFacilityDto userFacilityDto){
//        List
//        return searchService.ProductNameSearch(List<userFacilityDto>);
//    }
}