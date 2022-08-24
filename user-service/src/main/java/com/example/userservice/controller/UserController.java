package com.example.userservice.controller;


import com.example.userservice.dto.UserDto;
import com.example.userservice.service.EmailService;
import com.example.userservice.service.RedisService;
import com.example.userservice.service.SecurityService;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, SecurityService securityService, EmailService emailService
            , RedisService redisService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.emailService = emailService;
        this.redisService = redisService;
    }
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;
    private EmailService emailService;
    private RedisService redisService;
    String token;

    @PostMapping("/user-service/signup")
    public ResponseEntity<String> retrieveAllUser(@RequestBody UserDto userDto) {
        String ResponseEmail = userDto.getUserEmail();
        String ResponseName = userDto.getUserName();
        String ResponsePhoneNum = userDto.getUserPhoneNum();
//        String DBName = userService.registerEmailCheck(ResponseEmail).getUserName();
//        String DBPhoneNum = userService.registerEmailCheck(ResponseEmail).getUserPhoneNum();
        //try catch 로 예외처리 (이미 회원가입된 사람)
        log.info("Signup : 시도");
        log.info(userDto.toString());
        Integer LoginKeyCheck = userService.registerEmailCheck(ResponseEmail).getUserLoginKey();
        Integer AccessType = userService.registerEmailCheck(ResponseEmail).getUserAccessType();
//        if (ResponseName == DBName && ResponsePhoneNum == DBPhoneNum) {
//            log.info("Signup : 이미 가입한 유저");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("회원님의 이름과 전화번호로 가입한 아이디가 있습니다.");
//        } else
            if ( LoginKeyCheck == 1 && AccessType == 1) {
            userService.SignupUser(userDto);
            log.info("Signup : 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } else if ( LoginKeyCheck != 1 && AccessType == 1) {
            log.info("Signup : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else if ( AccessType != 1 && LoginKeyCheck == 1) {
            log.info("SignUp : 닉네임 중복 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("닉네임 중복 체크를 진행해주세요.");
        } else {
            log.info("SignUp : 회원가입 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 시도중 오류가 발생했습니다. 다시 진행해주세요.");
        }
    }

    @GetMapping("/user-service/register/check/email/{userEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("userEmail")String userEmail) throws NullPointerException{
        UserDto userDto = userService.registerEmailCheck(userEmail);
        if (userDto == null){
//            emailService.sendMail(email);
            String temporaryUuid = userService.RandomUuid();
            userService.EmailConform(userEmail,temporaryUuid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 이메일입니다.");
        }
    }

    @GetMapping("/user-service/register/check/nickname/{nickname}/{email}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("nickname")String nickname,
            @PathVariable("email")String email){
        Integer AccessType = 1;
        UserDto userDto = userService.registerNickNameCheck(nickname);
        if (userDto == null){
            userService.NickNameCheck(nickname,AccessType,email);
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다.");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 닉네임입니다.");
        }
    }

    @GetMapping("/user-service/register/check/email/{email}/{code}")
    public ResponseEntity emailCheck(@PathVariable("email") String email,
                                     @PathVariable("code") String code){
        String temporaryKey = "11111";
        Integer LoginKey = 1;
        //String valueKey = redisService.getData(email);
//        if (valueKey == null)
//            return ResponseEntity.status(HttpStatus.OK).body("인증코드 유효기간이 만료 되었습니다.");
        //log.info(valueKey);
        if (Integer.parseInt(temporaryKey) == Integer.parseInt(code)){
            //redisService.deleteData(email);
            userService.EmailCode(email,LoginKey);
            return ResponseEntity.status(HttpStatus.OK).body("이메일 인증이 완료 되었습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("잘못된 인증 코드 입니다.");
        }
    }


    @PostMapping("/user-service/login")
    public ResponseEntity<String> retrieveAllUsers(@RequestBody UserDto userDto) {
        String ResponsePw = userDto.getUserPassword();
        String encodePassword;
        String ResponseEmail = userDto.getUserEmail();
        String UserId;
        try {
            UserId = userService.findEmail(userDto).getUserEmail();
        }
        catch (NullPointerException e){
            log.info("Login : 아이디 틀림");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 틀렸습니다.");
        }
        String phoneNum = userService.findEmail(userDto).getUserPhoneNum();
        Integer userLoginKey = userService.findEmail(userDto).getUserLoginKey();
        try {
            encodePassword = (userService.findEmail(userDto)).getUserPassword();
        }
        catch (NullPointerException e1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 되지 않은 유저입니다.");
        }

        log.info("Login : 로그인 시도");

        if (passwordEncoder.matches(ResponsePw,encodePassword) && userLoginKey != 1) {
            log.info("Login : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else if (passwordEncoder.matches(ResponsePw,encodePassword) && userLoginKey == 1) {
            token = securityService.createToken(ResponseEmail,(30*60*1000),phoneNum,UserId);
            log.info("Login : 로그인 성공, 토큰 발급");
            return ResponseEntity.status(HttpStatus.OK).body("환영합니다!" + token);
        } else {
            log.info("Login : 비밀번호가 틀렸습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다.");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> test(@PathVariable("id")String id) {
        return ResponseEntity.status(HttpStatus.OK).body("id = "+id);
    }
}

