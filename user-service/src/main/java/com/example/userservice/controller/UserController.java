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
            return ResponseEntity.status(HttpStatus.OK).body(userService.signupUser(user));
        }


    /* 이메일 중복 확인 , 코드 발송 */
    @GetMapping("/register/check/email/{userEmail}")
    public ResponseEntity registerEmailCheck(
            @PathVariable("usoerEmail")String userEmail) throws NullPointerException{
        return ResponseEntity.status(HttpStatus.OK).body(userService.userEmailConform(userEmail));
    }


    /* 닉네임 중복 확인 */
    @GetMapping("/register/check/nickname/{userNickname}")
    public ResponseEntity registerNicknameCheck(
            @PathVariable("userNickname")String userNickname){
        return ResponseEntity.status(HttpStatus.OK).body(userService.userNickConform(userNickname));
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
        return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(user));
    }

    /* 아이디 찾기 */
    @PostMapping (value = "/FindUserid")
    public ResponseEntity FindUserId (@RequestBody FindIdVo findIdVo){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findId(findIdVo));
    }


    /* 비밀번호 찾기 */
    @PostMapping(value = "/UserPasswordReset")
    public ResponseEntity FindUserPassword (@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeRandomPassword(user));
    }

    /* 마이페이지 */
    @GetMapping("/myPage/{uuid}")
    public Object myPage(@PathVariable("uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.myPage(uuid));
    }

    /* 마이페이지(비밀번호 재설정) */
    @GetMapping("/myPage/changePW/{id}")
    public ResponseEntity ChangePW(@PathVariable("id") String id,
                                   @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePW(id,user));
    }

    /* 프로필 이미지 저장 */
    @PostMapping("/SaveProfileImage/{id}")
    public ResponseEntity<String> upload(@RequestParam("images") MultipartFile multipartFile,
                                         @PathVariable("id")String userId) throws IOException {
        //String userImg = "http://203.250.32.29:2201/api/user-service/ProfileImage/" + userId;
        String userImg = "http://localhost:2201/user-service/ProfileImage/" + userId;
        ImageUploader.upload(multipartFile,userImg,userId);
        return ResponseEntity.status(HttpStatus.OK).body(ImageConstant.IMAGE_SUCCESS);
    }

    /* 프로필 이미지 가져오기 */
    @GetMapping(value = "/ProfileImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ResponseEntity<byte[]>> getImage(@PathVariable("id")String id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getImmage(id));
    }
}