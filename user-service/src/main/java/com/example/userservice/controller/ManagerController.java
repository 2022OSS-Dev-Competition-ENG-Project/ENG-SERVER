package com.example.userservice.controller;

import com.example.userservice.dto.ManagerDataDto;
import com.example.userservice.dto.ManagerDto;
import com.example.userservice.service.*;
import com.example.userservice.vo.FindManagerIdVo;
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
public class ManagerController {
    @Autowired
    public ManagerController(UserService userService, ManagerService managerService, PasswordEncoder passwordEncoder
            , SecurityService securityService, EmailService emailService, RedisService redisService){
        this.managerService = managerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.emailService = emailService;
        this.redisService = redisService;
    }
    private ManagerService managerService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;
    private EmailService emailService;
    private RedisService redisService;
    String token;

    /* 매니저 회원가입 */
    @PostMapping("/manager-service/signup")
    public ResponseEntity<String> Manager(@RequestBody ManagerDto managerDto) {
        String ResponseEmail = managerDto.getManagerEmail();
        String ResponseName = managerDto.getManagerName();
        String ResponsePhoneNum = managerDto.getManagerPhoneNum();
//        String DBName = userService.registerEmailCheck(ResponseEmail).getUserName();
//        String DBPhoneNum = userService.registerEmailCheck(ResponseEmail).getUserPhoneNum();
        //try catch 로 예외처리 (이미 회원가입된 사람)
        log.info("ManagerSignup : 시도");
        log.info(managerDto.toString());
        Integer LoginKeyCheck = managerService.ManagerEmailCheck(ResponseEmail).getManagerLoginKey();
        Integer AccessType = managerService.ManagerEmailCheck(ResponseEmail).getManagerAccessType();
//        if (ResponseName == DBName && ResponsePhoneNum == DBPhoneNum) {
//            log.info("Signup : 이미 가입한 유저");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("회원님의 이름과 전화번호로 가입한 아이디가 있습니다.");
//        } else
        if ( LoginKeyCheck == 1 && AccessType == 1) {
            managerService.SignupManager(managerDto);
            log.info("ManagerSignup : 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } else if ( LoginKeyCheck != 1 && AccessType == 1) {
            log.info("ManagerSignup : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else if ( AccessType != 1 && LoginKeyCheck == 1) {
            log.info("ManagerSignup : 닉네임 중복 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("닉네임 중복 체크를 진행해주세요.");
        } else {
            log.info("ManagerSignup : 회원가입 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 시도중 오류가 발생했습니다. 다시 진행해주세요.");
        }
    }

    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/manager-service/register/check/email/{managerEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("managerEmail")String managerEmail) throws NullPointerException{
        ManagerDto managerDto = managerService.ManagerEmailCheck(managerEmail);
        if (managerDto == null){
//            emailService.sendMail(email);
            String temporaryUuid = userService.RandomObject();
            managerService.ManagerEmailConform(managerEmail,temporaryUuid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 이메일입니다.");
        }
    }
    /* 닉네임 중복 체크 */
    @GetMapping("/manager-service/register/check/nickname/{nickname}/{email}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("nickname")String nickname,
            @PathVariable("email")String email){
        Integer AccessType = 1;
        ManagerDto managerDto = managerService.ManagerNickNameCheck(nickname);
        if (managerDto == null){
            managerService.NickNameCheckAccess(nickname,AccessType,email);
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 닉네임입니다.");
        }
    }

    /* 이메일 코드 확인 */
    @GetMapping("/manager-service/register/check/email/{email}/{code}")
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
            managerService.ManagerEmailCode(email,LoginKey);
            return ResponseEntity.status(HttpStatus.OK).body("이메일 인증이 완료 되었습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("잘못된 인증 코드 입니다.");
        }
    }

    /* 매니저 로그인 */
    @PostMapping("/manager-service/login")
    public Object AllManagers(@RequestBody ManagerDto managerDto) {
        String ResponsePw = managerDto.getManagerPassword();
        String managerEmail = managerDto.getManagerEmail();
        String encodePassword;
        ManagerDataDto managerDataDto = new ManagerDataDto();
        managerDataDto.setManagerName(managerService.findManagerID(managerEmail).getManagerName());
        managerDataDto.setManagerUuid(managerService.findManagerID(managerEmail).getManagerUuid());
        String UserId;
        try {
            UserId = managerService.findManagerEmail(managerDto).getManagerEmail();
        }
        catch (NullPointerException e){
            log.info("ManagerLogin : 아이디 틀림");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 틀렸습니다.");
        }
        String phoneNum = managerService.findManagerEmail(managerDto).getManagerPhoneNum();
        Integer managerAccessType = managerService.findManagerEmail(managerDto).getManagerAccessType();
        try {
            encodePassword = (managerService.findManagerEmail(managerDto)).getManagerPassword();
        }
        catch (NullPointerException e1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 되지 않은 유저입니다.");
        }

        log.info("ManagerLogin : 로그인 시도");

        if (passwordEncoder.matches(ResponsePw,encodePassword) && managerAccessType != 1) {
            log.info("ManagerLogin : 이메일 인증 안함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 진행해주세요.");
        } else if (passwordEncoder.matches(ResponsePw,encodePassword) && managerAccessType == 1) {
            //token = securityService.createToken(ResponseEmail,(30*60*1000),phoneNum,UserId);
            String DBManagerUuid = managerService.findManagerEmail(managerDto).getManagerUuid();
            log.info("ManagerLogin : 로그인 성공, 토큰 발급");
            return managerDataDto;
        } else {
            log.info("ManagerLogin : 비밀번호가 틀렸습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다.");
        }
    }

    /* 매니저 아이디 찾기 */
    @PostMapping (value = "/manager-service/FindManagerId")
    public ResponseEntity FindManagerId (@RequestBody FindManagerIdVo findManagerIdVo){
        /* 이름 전화 번호가 한컬럼 안에서 다를 때 */
        try{
            log.info(managerService.findManagerId(findManagerIdVo));
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 정보가 없습니다.");
        }

        if (managerService.findManagerId(findManagerIdVo) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 정보가 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(managerService.findManagerId(findManagerIdVo));
    }

    /* 매니저 비밀번호 초기화(랜덤) */
    @PostMapping(value = "/manager-service/ManagerPasswordReset")
    public ResponseEntity<String> FindManagerPassword (@RequestBody ManagerDto managerDto) {
        String ResponseEmail = managerDto.getManagerEmail(); // 입력 받은 Email
        String DBEmail;
        String DBName;
        //String ChgUserPassword = userService.RandomObject();
        String ChgManagerPassword = "aaaaa";
        log.info("findUserPassword : 비밀번호 재 설정 시도");
        try {
            DBEmail = managerService.findManagerEmail(managerDto).getManagerEmail();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이메일을 다시 확인해주세요.");
        }

        try {
            DBName = managerService.findManagerName(managerDto).getManagerName();
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
            managerDto.setManagerPassword(ChgManagerPassword);
            managerService.resetPassword(managerDto);
//            log.info("findUserPassword : 비밀번호 재 설정 완료");

            return ResponseEntity.status(HttpStatus.OK).body(ChgManagerPassword);
        }
        return null;
    }

    /* 매니저 마이페이지 (비밀번호 재 설정) */
    @GetMapping("/manager-service/myPage/changePW/{uuid}")
    public ResponseEntity ChangePW(@PathVariable("uuid") String uuid,
                                   @RequestBody ManagerDto managerDto) {
        managerDto.setManagerUuid(uuid);
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
        managerService.changeManagerPW(managerDto);
        log.info("MyPage : 개인 정보 수정 완료");
        String userName = managerService.findManagerUuid(uuid).getManagerName();
        String userEmail = managerService.findManagerUuid(uuid).getManagerEmail();
        return ResponseEntity.status(HttpStatus.OK).body(userName + "님의 비밀번호가 변경되었습니다. 변경된 아이디 = " + userEmail);
    }


}