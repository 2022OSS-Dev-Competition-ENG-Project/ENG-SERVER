package com.example.userservice.controller;

import com.example.userservice.dto.*;
import com.example.userservice.service.*;
import com.example.userservice.vo.AdminVO;
import com.example.userservice.vo.FindManagerIdVo;
import com.example.userservice.dto.ManagerDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
@ControllerAdvice
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
            managerDataDto.setManagerName(managerService.findManagerID(managerEmail).getManagerName());
            managerDataDto.setManagerUuid(managerService.findManagerID(managerEmail).getManagerUuid());
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
    @PostMapping("/manager-service/myPage/changePW")
    public ResponseEntity ChangePW(@RequestBody ManagerDto managerDto) {
        String uuid = managerDto.getManagerUuid();
        managerDto.setManagerUuid(uuid);
        managerService.changeManagerPW(managerDto);
        log.info("MyPage : 개인 정보 수정 완료");
        String managerName = managerService.findManagerUuid(uuid).getManagerName();
        String managerEmail = managerService.findManagerUuid(uuid).getManagerEmail();
        return ResponseEntity.status(HttpStatus.OK).body(managerName + "님의 비밀번호가 변경되었습니다. 변경된 아이디 = " + managerEmail);
    }

//    /* 시설물 관리하는 매니저List */
//    @GetMapping("/manager-service/adminManagement/{facilityNo}")
//    public List<AdminVO> facilityAdminList(Model model
//            , @PathVariable("facilityNo") String facilityNo) throws Exception {
//        List<AdminVO> list = managerService.AdminList(facilityNo);
//        model.addAttribute("list",list);
//        return list;
//    }
//
//    /* 시설물 관리를위한 매니저 등록 */
//    @GetMapping("/manager-service/addAdmin/{facilityNo}/{managerUuid}")
//    public ResponseEntity AddAdmin(@PathVariable("facilityNo")String facilityNo,
//                                   @PathVariable("managerUuid")String managerUuid,
//                                   @RequestBody ManagerDto managerDto) {
//        String ResponseName = managerDto.getManagerName();
//        String ResponseEmail = managerDto.getManagerEmail();
//        String facilityMasterUuid;
//        try {
//            facilityMasterUuid = managerService.facilityMasterCheck(facilityNo).getFacilityOwner();
//        } catch (NullPointerException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("마스터만 가능한 서비스입니다.");
//        }
//
//        log.info("MasterUuid : " + facilityMasterUuid);
//        log.info("managerUuid : " + managerUuid);
//        if (facilityMasterUuid.equals(managerUuid)) {
//            String DBEmail = managerService.findManagerName(managerDto).getManagerEmail();
//            String managerName = managerService.findManagerID(DBEmail).getManagerName();
//            if (ResponseName.equals(managerName) && ResponseEmail.equals(DBEmail)) {
//                Integer facilityGrade = 1;
//                log.info(facilityNo);
//                managerService.addAdmin(facilityGrade, managerName, facilityNo);
//                return ResponseEntity.status(HttpStatus.OK).body("매니저 등록이 완료되었습니다.");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마스터가 아닙니다.");
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마스터가 아닙니다.");
//    }
//
//    /* 시설물 관리를위한 매니저 삭제 */
//    @GetMapping("/manager-service/deleteAdmin/{facilityNo}/{managerUuid}")
//    public ResponseEntity DeleteAdmin(@PathVariable("facilityNo")String facilityCheckNo,
//                                      @PathVariable("managerUuid")String managerUuid,
//                                      @RequestBody ManagerDto managerDto) {
//        String ResponseName = managerDto.getManagerName();
//        String ResponseEmail = managerDto.getManagerEmail();
//        String facilityMasterUuid;
//        try {
//            facilityMasterUuid = managerService.facilityMasterCheck(facilityCheckNo).getFacilityOwner();
//        } catch (NullPointerException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("마스터만 가능한 서비스입니다.");
//        }
//
//        if (facilityMasterUuid.equals(managerUuid)) {
//            String DBEmail = managerService.findManagerName(managerDto).getManagerEmail();
//            String managerName = managerService.findManagerID(DBEmail).getManagerName();
//            if (ResponseName.equals(managerName) && ResponseEmail.equals(DBEmail)) {
//                Integer facilityGrade = 0;
//                String facilityNo = NULL;
//                managerService.deleteAdmin(facilityGrade, managerName, facilityNo);
//                return ResponseEntity.status(HttpStatus.OK).body("매니저 삭제가 완료되었습니다.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마스터가 아닙니다.");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마스터가 아닙니다.");
//    }
    /* Master 직급 양도 */
    @GetMapping("/manager-service/changeOwner/{facilityNo}/{managerUuid}")
    public ResponseEntity ChangeOwner(@PathVariable("facilityNo")String facilityNo,
                                      @PathVariable("managerUuid")String managerUuid,
                                      @RequestBody ManagerDto managerDto) {
        String ResponseName = managerDto.getManagerName();
        String ResponseEmail = managerDto.getManagerEmail();
        String DBUuid;
        String DBName;
        try {
            String MasterUuid = managerService.facilityMasterCheck(facilityNo).getFacilityOwner();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("마스터가 가능한 서비스입니다.");
        }
        try {
            DBUuid = managerService.ManagerEmailCheck(ResponseEmail).getManagerUuid();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("managerEmail를 확인해주세요");
        }
        try {
            DBName = managerService.ManagerEmailCheck(ResponseEmail).getManagerName();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ManagerName을 확인해주세요.");
        }
        if (managerUuid == DBUuid && DBName == ResponseName) {
            managerService.changeOwner(managerUuid, facilityNo);
            return ResponseEntity.status(HttpStatus.OK).body("마스터가 변경 되었습니다.");
        }
        return null;
    }
}