package com.example.userservice.controller;

import com.example.userservice.constant.ImageConstant;
import com.example.userservice.constant.MyPageConstant;
import com.example.userservice.constant.SignUpConstant;
import com.example.userservice.dto.UserDataDto;
import com.example.userservice.dto.User;
import com.example.userservice.service.*;
import com.example.userservice.vo.FindIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Slf4j
@RestController
@RequestMapping(value = "")
@ControllerAdvice
public class UserController {

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    private UserService userService;

    /* 유저 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity User(@RequestBody User user) {
        ResponseEntity responseEntity = userService.signupUser(user);
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        }


    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/register/check/email/{userEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("userEmail")String userEmail) throws NullPointerException{
        ResponseEntity responseEntity = userService.userEmailConform(userEmail);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 닉네임 중복 확인 */
    @GetMapping("/register/check/nickname/{userNickname}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("userNickname")String userNickname){
        ResponseEntity responseEntity = userService.userNickConform(userNickname);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 이메일 코드 확인 */
    @GetMapping("/register/check/email/{email}/{code}")
    public ResponseEntity emailCheck(@PathVariable("email") String email,
                                     @PathVariable("code") String code){
        ResponseEntity responseEntity = userService.emailCode(email, code);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 유저 로그인 */
    @PostMapping("/login")
    public ResponseEntity retrieveAllUsers(@RequestBody User user) {
        ResponseEntity responseEntity = userService.userLogin(user);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 아이디 찾기 */
    @PostMapping (value = "/FindUserid")
    public ResponseEntity FindUserId (@RequestBody FindIdVo findIdVo){
        ResponseEntity responseEntity = userService.findId(findIdVo);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 비밀번호 찾기 */
    @PostMapping(value = "/UserPasswordReset")
    public ResponseEntity FindUserPassword (@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeRandomPassword(user));
    }

    /* 마이페이지 */
    @GetMapping("/myPage/{uuid}")
    public Object myPage(@PathVariable("uuid") String uuid) {
        ResponseEntity responseEntity = userService.myPage(uuid);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 마이페이지(비밀번호 재설정) */
    @GetMapping("/myPage/changePW/{userUuid}")
    public ResponseEntity ChangePW(@PathVariable("userUuid") String userUuid,
                                   @RequestBody User user) {
        ResponseEntity responseEntity = userService.changePW(userUuid,user);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 프로필 이미지 저장 */
    @PostMapping("/SaveProfileImage/{uuid}")
    public ResponseEntity<String> upload(@RequestParam("images") MultipartFile multipartFile,
                                         @PathVariable("uuid")String userUuid) throws IOException {
        //String userImg = "http://203.250.32.29:2201/api/user-service/ProfileImage/" + userId;
        String userNickname = userService.findUuid(userUuid).getUserNickname();
        String userImg = "http:/jlchj.iptime.org/ENG-STORAGE/images/profileImage/" + userUuid
                + "/" + userNickname;
        ImageUploader.upload(multipartFile,userImg,userUuid,userNickname);
        return ResponseEntity.status(HttpStatus.OK).body(ImageConstant.IMAGE_SUCCESS);
    }

    /* 프로필 이미지 가져오기 */
    @GetMapping(value = "/ProfileImage/{uuid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ResponseEntity<byte[]>> getImage(@PathVariable("uuid")String uuid) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getImmage(uuid));
    }
}