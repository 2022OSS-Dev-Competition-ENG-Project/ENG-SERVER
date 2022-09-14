package com.example.userservice.controller;

import com.example.userservice.constant.ImageConstant;
import com.example.userservice.constant.MyPageConstant;
import com.example.userservice.constant.SignUpConstant;
import com.example.userservice.dto.UserDataDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.*;
import com.example.userservice.vo.FindIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import static com.example.userservice.constant.ImageConstant.SAVE_PATH;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder ){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    /* 유저 회원가입 */
    @PostMapping("/user-service/signup")
    public ResponseEntity<String> retrieveAllUser(@RequestBody UserDto userDto) {
        String ResponseEmail = userDto.getUserEmail();
        Integer LoginKeyCheck = userService.registerEmailCheck(ResponseEmail).getUserLoginKey();
        Integer AccessType = userService.registerEmailCheck(ResponseEmail).getUserAccessType();
        if ( LoginKeyCheck == 1 && AccessType == 1) {
            userService.SignupUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(SignUpConstant.SIGNUP_CLEAR);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.SIGNUP_FAIL_CONTENT);
        }
    }

    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/user-service/register/check/email/{userEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("userEmail")String userEmail) throws NullPointerException{
        UserDto userDto = userService.registerEmailCheck(userEmail);
        if (userDto == null){
            String temporaryUuid = userService.RandomObject();
            userService.EmailConform(userEmail,temporaryUuid);
            return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.EMAIL_CHECK_CLEAR);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(SignUpConstant.EMAIL_CHECK_FAIL);
        }
    }

    /* 닉네임 중복 확인 */
    @GetMapping("/user-service/register/check/nickname/{nickname}/{email}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("nickname")String nickname,
            @PathVariable("email")String email){
        UserDto userDto = userService.registerNickNameCheck(nickname);
        if (userDto == null){
            userService.NickNameCheck(nickname,email);
            return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.NICKNAME_CHECK_CLEAR);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(SignUpConstant.NICKNAME_CHECK_FAIL);
        }
    }

    /* 이메일 코드 확인 */
    @GetMapping("/user-service/register/check/email/{email}/{code}")
    public ResponseEntity emailCheck(@PathVariable("email") String email,
                                     @PathVariable("code") String code){
        String temporaryKey = "11111";
        if (Integer.parseInt(temporaryKey) == Integer.parseInt(code)){
            userService.EmailCode(email);
            return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.EMAIL_CODE_CLEAR);
        }else {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(SignUpConstant.EMAIL_CODE_FAIL);
        }
    }

    /* 유저 로그인 */
    @PostMapping("/user-service/login")
    public ResponseEntity<String> retrieveAllUsers(@RequestBody UserDto userDto) {
        String ResponsePw = userDto.getUserPassword();
        String encodePassword = (userService.findEmail(userDto)).getUserPassword();
        String UserId;
        try {
            UserId = userService.findEmail(userDto).getUserEmail();
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_ID_FAIL);
        }
        Integer userLoginKey = userService.findEmail(userDto).getUserLoginKey();
        if (passwordEncoder.matches(ResponsePw,encodePassword) && userLoginKey == 1) {
            String DBUuid = userService.findEmail(userDto).getUserUuid();
            return ResponseEntity.status(HttpStatus.OK).body(DBUuid);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_PASSWORD_FAIL);
        }
    }

    /* 아이디 찾기 */
    @PostMapping (value = "/user-service/FindUserid")
    public ResponseEntity FindUserId (@RequestBody FindIdVo findIdVo){
        if (userService.findId(findIdVo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_ID_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findId(findIdVo));
    }


    /* 비밀번호 찾기 */
    @PostMapping(value = "/user-service/UserPasswordReset")
    public ResponseEntity<String> FindUserPassword (@RequestBody UserDto userDto) {
        String DBEmail;
        String DBName;
        String ChgUserPassword = "aaaaa"; //userService.RandomObject();  SMTP 오류 해결시 랜덤비빌번호 설저 예정
        try {
            DBEmail = userService.findEmail(userDto).getUserEmail();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_EMAIL_FAIL);
        }

        try {
            DBName = userService.findEmail(userDto).getUserName();
        } catch (java.lang.NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_NAME_FAIL);
        }
        if (DBEmail != null && DBName != null) {
            userDto.setUserPassword(ChgUserPassword);
            userService.changeRandomPassword(userDto);
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
        userDataDto.setUserImg(userService.findUuid(uuid).getUserImg());
        return userDataDto;
    }

    /* 마이페이지(비밀번호 재설정) */
    @GetMapping("/user-service/myPage/changePW/{uuid}")
    public ResponseEntity ChangePW(@PathVariable("uuid") String uuid,
                                   @RequestBody UserDto userDto) {
        userDto.setUserUuid(uuid);
        userService.changePW(userDto);
        String userName = userService.findUuid(uuid).getUserName();
        return ResponseEntity.status(HttpStatus.OK).body(userName + MyPageConstant.MYPAGE_CLEAR);
    }

    /* 프로필 이미지 저장 */
    @PostMapping("/user-service/SaveProfileImage/{uuid}")
    public ResponseEntity<String> upload(@RequestParam("images") MultipartFile multipartFile,
                                         @PathVariable("uuid")String userUuid) throws IOException {
        String userImg = "http://203.250.32.29:2201/api/user-service/ProfileImage/" + userUuid;
        ImageUploader.upload(multipartFile,userImg,userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(ImageConstant.IMAGE_SUCCESS);
    }

    /* 프로필 이미지 가져오기 */
    @GetMapping(value = "/user-service/ProfileImage/{uuid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("uuid")String uuid) throws IOException {
        String savePath = SAVE_PATH;
        InputStream in = null;
        String userImage = savePath + "/" + uuid;

        try {
            in = new FileInputStream(userImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = in.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
            fileArray = out.toByteArray();
            in.close();
            out.close();
        } catch(IOException e){
            throw new RuntimeException(ImageConstant.IMAGE_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileArray);
    }

}