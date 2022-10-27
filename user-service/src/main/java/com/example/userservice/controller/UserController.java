package com.example.userservice.controller;

import com.example.userservice.dto.User;
import com.example.userservice.service.*;
import com.example.userservice.vo.FindIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity FindUserId (@RequestBody User user){
        ResponseEntity responseEntity = userService.FindId(user);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 비밀번호 찾기 */
    @PostMapping(value = "/UserPasswordReset")
    public ResponseEntity FindUserPassword (@RequestBody User user) {
        ResponseEntity responseEntity = userService.changeRandomPassword(user);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 마이페이지 */
    @GetMapping("/myPage/{uuid}")
    public Object myPage(@PathVariable("uuid") String uuid) {
        ResponseEntity responseEntity = userService.myPage(uuid);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 마이페이지(비밀번호 재설정) */
    @PostMapping("/myPage/changePW")
    public ResponseEntity ChangePW(@RequestBody User user) {
        ResponseEntity responseEntity = userService.changePW(user);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 프로필 이미지 저장 */
    @PostMapping("/SaveProfileImage/{uuid}")
    public ResponseEntity upload(@RequestParam("images") MultipartFile multipartFile,
                                         @PathVariable("uuid")String userUuid) throws IOException {
        ResponseEntity responseEntity = ImageUploader.upload(multipartFile,userUuid);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 프로필 이미지 가져오기 */
    @GetMapping(value = "/ProfileImage/{uuid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> getImage(@PathVariable("uuid")String uuid) throws IOException {
        ResponseEntity responseEntity = userService.getImage(uuid);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}