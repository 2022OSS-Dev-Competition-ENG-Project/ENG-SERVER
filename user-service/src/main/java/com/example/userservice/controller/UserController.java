package com.example.userservice.controller;


import com.example.userservice.dto.UserDataDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.*;
import com.example.userservice.vo.FindIdVo;
import com.example.userservice.vo.UserDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, SecurityService securityService, EmailService emailService
            , RedisService redisService ){
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

    /* 유저 회원가입 */
    @PostMapping("/user-service/signup")
    public ResponseEntity<String> retrieveAllUser(@RequestBody UserDto userDto) {
        String ResponseEmail = userDto.getUserEmail();
        String ResponseName = userDto.getUserName();
        String ResponsePhoneNum = userDto.getUserPhoneNum();
//        String DBName = userService.registerEmailCheck(ResponseEmail).getUserName();
//        String DBPhoneNum = userService.registerEmailCheck(ResponseEmail).getUserPhoneNum();
        //try catch 로 예외처리 (이미 회원가입된 사람)
        log.info("UserSignup : 시도");
        log.info(userDto.toString());
        Integer LoginKeyCheck = userService.registerEmailCheck(ResponseEmail).getUserLoginKey();
        Integer AccessType = userService.registerEmailCheck(ResponseEmail).getUserAccessType();
//        if (ResponseName == DBName && ResponsePhoneNum == DBPhoneNum) {
//            log.info("Signup : 이미 가입한 유저");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("회원님의 이름과 전화번호로 가입한 아이디가 있습니다.");
//        } else
        if ( LoginKeyCheck == 1 && AccessType == 1) {
            userService.SignupUser(userDto);
            log.info("UserSignup : 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } else if ( LoginKeyCheck != 1 && AccessType == 1) {
            log.info("UserSignup : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else if ( AccessType != 1 && LoginKeyCheck == 1) {
            log.info("UserSignup : 닉네임 중복 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("닉네임 중복 체크를 진행해주세요.");
        } else {
            log.info("UserSignup : 회원가입 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 시도중 오류가 발생했습니다. 다시 진행해주세요.");
        }
    }

    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/user-service/register/check/email/{userEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("userEmail")String userEmail) throws NullPointerException{
        UserDto userDto = userService.registerEmailCheck(userEmail);
        if (userDto == null){
//            emailService.sendMail(email);
            String temporaryUuid = userService.RandomObject();
            userService.EmailConform(userEmail,temporaryUuid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 이메일입니다.");
        }
    }
    /* 닉네임 중복 확인 */
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


    /* 이메일 코드 확인 */
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

    /* 유저 로그인 */
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
            //token = securityService.createToken(ResponseEmail,(30*60*1000),phoneNum,UserId);
            String DBUuid = userService.findEmail(userDto).getUserUuid();
            log.info("Login : 로그인 성공, 토큰 발급");
            return ResponseEntity.status(HttpStatus.OK).body(DBUuid);
        } else {
            log.info("Login : 비밀번호가 틀렸습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다.");
        }
    }

    /* 아이디 찾기 */
    @PostMapping (value = "/user-service/FindUserid")
    public ResponseEntity FindUserId (@RequestBody FindIdVo findIdVo){
        /* 이름 전화 번호가 한컬럼 안에서 다를 때 */
        try{
            log.info(userService.findId(findIdVo));
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 정보가 없습니다.");
        }

        if (userService.findId(findIdVo) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 정보가 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findId(findIdVo));
    }


    /* 비밀번호 찾기 */
    @PostMapping(value = "/user-service/UserPasswordReset")
    public ResponseEntity<String> FindUserPassword (@RequestBody UserDto userDto) {
        String ResponseEmail = userDto.getUserEmail(); // 입력 받은 Email
        String DBEmail;
        String DBName;
        //String ChgUserPassword = userService.RandomObject();
        String ChgUserPassword = "aaaaa";
        log.info("findUserPassword : 비밀번호 재 설정 시도");
        try {
            DBEmail = userService.findEmail(userDto).getUserEmail();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이메일을 다시 확인해주세요.");
        }

        try {
            DBName = userService.findEmail(userDto).getUserName();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이름을 다시 확인해주세요.");
        }
        if (DBEmail != null && DBName != null) {
//            SimpleMailMessage simpleMessage = new SimpleMailMessage();
//            simpleMessage.setSubject("비밀번호 초기화");
//            simpleMessage.setText("비밀번호가 초기화 되었습니다. 초기화 된 비밀번호는 ["
//                    + ChgUserPassword + "] 입니다.");
//            simpleMessage.setTo(ResponseEmail);
//            javaMailSender.send(simpleMessage);
//            userDto.setPassword(ChgUserPassword);
//            userDto.setId(DBId);
            userDto.setUserPassword(ChgUserPassword);
            userService.changeRandomPassword(userDto);
//            log.info("findUserPassword : 비밀번호 재 설정 완료");

            return ResponseEntity.status(HttpStatus.OK).body(ChgUserPassword);
        }
        return null;
    }

    /* 마이페이지 */
    @GetMapping("/user-service/myPage/{uuid}")
    public Object myPage(@PathVariable("uuid") String uuid) {
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setUserEmail(userService.findUuid(uuid).getUserEmail());
        userDataDto.setUserNickname(userService.findUuid(uuid).getUserNickname());
        userDataDto.setUserJoinDate(userService.findUuid(uuid).getUserJoinDate());
        return userDataDto;
    }

    /* 마이페이지(비밀번호 재설정) */
    @GetMapping("/user-service/myPage/changePW/{uuid}")

    public ResponseEntity ChangePW(@PathVariable("uuid") String uuid,
                                 @RequestBody UserDto userDto) {
        userDto.setUserUuid(uuid);
        /* 토큰 구현시 다시 */
//        String UserEmail = userService.findUserUuid(userDto).getUserEmail();

//        try {
//            securityService.getSubject(token, UserEmail);
//        } catch (SignatureException e) {
//            log.info("MyPage : 정보 수정할때 Email을 수정, 잘못된 토큰");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("재 로그인 후 진행해주세요.");
//        } catch (IllegalArgumentException e) {
//            log.info("MyPage : 로그인 안함");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 진행해주세요.");
//        } catch (ExpiredJwtException e) {
//            log.info("MyPage : 토큰유효기간 지남");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰유효기간이 지났습니다. 재 로그인 후 진행해주세요.");
//        }
        userService.changePW(userDto);
        log.info("MyPage : 개인 정보 수정 완료");
        String userName = userService.findUuid(uuid).getUserName();
        return ResponseEntity.status(HttpStatus.OK).body(userName + "님의 비밀번호가 변경되었습니다. 변경된 아이디 = " + userName);
    }

    @PostMapping("/user-service/ProfileImages/{uuid}")
    public ResponseEntity<String> upload(@RequestParam("images") MultipartFile multipartFile,
                                         @PathVariable("uuid")String uuid) throws IOException {
        log.info("ProfileImages : 이미지 저장 시도");
//        String UserEmail = (userService.findEmailJWT(id)).getEmail();
//        try {
//            securityService.getSubject(token, UserEmail);
//        } catch (IllegalArgumentException e) {
//            log.info("ProfileImages : 토큰 없음");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 진행해주세요.");
//        }
        String userNickName = userService.findUuid(uuid).getUserNickname();
        ImageUploader.upload(multipartFile,userNickName);
        return ResponseEntity.status(HttpStatus.OK).body("설정 되었습니다.");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> test(@PathVariable("id")String id) {
        return ResponseEntity.status(HttpStatus.OK).body("id = "+id);
    }
}