package com.example.engproject.controller;


import com.example.engproject.dto.UserDto;
import com.example.engproject.service.EmailService;
import com.example.engproject.service.RedisService;
import com.example.engproject.service.SecurityService;
import com.example.engproject.service.UserService;
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

        log.info("Signup : 시도");
        String LoginKeyCheck = userService.findEmail(userDto).getUserLoginKey();
        String AccessType = userService.findEmail(userDto).getUserAccessType();
        if ( LoginKeyCheck == "1" && AccessType == "1") {
        log.info(userDto.toString());
            userService.SignupUser(userDto);
            log.info("Signup : 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } else if (LoginKeyCheck != "1" && AccessType != "1") {
            log.info("SignUp : 닉네임 중복 체크, 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증과 닉네임 중복 체크를 해주세요");
        } else if ( AccessType != "1" ) {
            log.info("SignUp : 닉네임 중복 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("닉네임 중복 체크를 진행해주세요.");
        } else if ( LoginKeyCheck != "1") {
            log.info("Signup : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else {
            return null;
        }
    }

    @GetMapping("/register/check/email/{email}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("email")String email) throws NullPointerException{
        UserDto userDto = userService.registerEmailCheck(email);
        if (userDto == null){
            String LoginKey = "1";
            emailService.sendMail(email);
            userService.EmailConform(email,LoginKey);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.");

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 이메일입니다.");
        }
    }

    @GetMapping("/register/check/email/{email}/{code}")
    public ResponseEntity emailCheck(@PathVariable("email") String email,
                                     @PathVariable("code") String code){
        String valueKey = redisService.getData(email);
        if (valueKey == null) return ResponseEntity.status(HttpStatus.OK)
                .body("인증코드 유효기간이 만료 되었습니다.");
        log.info(valueKey);
        if (Integer.parseInt(valueKey) == Integer.parseInt(code)){
            redisService.deleteData(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("이메일 인증이 완료 되었습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 인증 코드 입니다.");
        }
    }

    @GetMapping("/register/check/nickname/{nickname}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("nickname")String nickname){
        UserDto userDto = userService.registerNickNameCheck(nickname);
        if (userDto == null){
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다.");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 닉네임입니다.");
        }
    }

    @PostMapping("/user-service/login")
    public ResponseEntity<String> retrieveAllUsers(@RequestBody UserDto userDto) {
        String ResponsePw = userDto.getUserPassword();
        String encodePassword;
        String ResponseId = userDto.getUserEmail();
        String UserName;
        try {
            UserName = userService.findEmail(userDto).getUserEmail();
        }
        catch (java.lang.NullPointerException e){
            log.info("Login : 아이디 틀림");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 틀렸습니다.");
        }
        String phoneNum = userService.findEmail(userDto).getUserPhoneNum();
        String loginkey = userService.findEmail(userDto).getUserLoginKey();
        try {
            encodePassword = (userService.findEmail(userDto)).getUserPassword();
        }
        catch (java.lang.NullPointerException e1) {
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
//}
    @GetMapping("/user/{id}")
    public ResponseEntity<String> test(@PathVariable("id")String id) {
        return ResponseEntity.status(HttpStatus.OK).body("id = "+id);
    }
}

