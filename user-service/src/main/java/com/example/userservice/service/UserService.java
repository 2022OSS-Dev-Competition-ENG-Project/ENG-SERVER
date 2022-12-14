package com.example.userservice.service;

import com.example.userservice.constant.ImageConstant;
import com.example.userservice.constant.MyPageConstant;
import com.example.userservice.constant.SignUpConstant;
import com.example.userservice.dto.User;
import com.example.userservice.dto.UserDataDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.vo.FindIdVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import static com.example.userservice.constant.ImageConstant.*;
import static com.example.userservice.constant.SignUpConstant.*;
import static com.example.userservice.constant.EmailConstant.*;

@Slf4j
@Service
public class UserService {

    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    EmailService emailService;
    RedisService redisService;

    @Autowired
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder, EmailService emailService, RedisService redisService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.redisService = redisService;
    }

    /* User 회원가입 */
    public ResponseEntity signupUser(User user) {
        /* User Uuid 생성 */
        user.setUserUuid(UUID.randomUUID().toString());
        /* User 회원가입 날짜 */
        user.setUserJoinDate(LocalDate.now());
        /* 휴대폰 번호 중복 체크 */
        if (userMapper.PhoneNumberCheck(user.getUserPhoneNumber()) == 0) {
            /* 비밀 번호 암호화 */
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userMapper.SignupUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(SignUpConstant.SIGNUP_CLEAR);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(SignUpConstant.SIGNUP_FAIL_PUN_OVERLAP
                            .replaceAll("\\$phonnumber", user.getUserPhoneNumber()));
        }
    }

        /* User 회원가입 - Email 중복 검사 */
        public ResponseEntity userEmailConform (String userEmail){
            /* 이메일 인증을 위한 Key값 생성 */
            String key = RandomStringUtils.randomNumeric(6);
            if (userMapper.UserEmailConform(userEmail) == 0) {
                /* 이메일 발송 */
                emailService.sendMail(userEmail,key,"EmailCheck");
                return ResponseEntity.status(HttpStatus.OK).body(EMAIL_CHECK_CLEAR);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_CHECK_FAIL);
            }
        }

        /* User 회원가입 - Email 인코드 검사 성공시 */
        public ResponseEntity emailCode (String userEmail, String code){
            /* Redis에 저장된 키값의 Value를 가져온다.*/
            String key = redisService.getData(userEmail);
            if (key == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SMTP_EMAIL_CODE_CHECK_NOT_COMPLETE);
            if(Integer.parseInt(key) == Integer.parseInt(code)){
                /* Redis에 key값을 가진 데이터 삭제 */
                redisService.deleteData(userEmail);
                return ResponseEntity.status(HttpStatus.OK).body(SMTP_EMAIL_CODE_CHECK_COMPLETE);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SMTP_EMAIL_CODE_CHECK_NOT_COMPLETE);
            }
        }

        /* User 회원가입 - 중복된 Nickname 검사하기 */
        public ResponseEntity userNickConform (String userNickname){
            /* NickName 검사하기 */
            Integer conform = userMapper.UserNicknameConform(userNickname);
            if (conform == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(NICKNAME_CHECK_CLEAR);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(NICKNAME_CHECK_FAIL);
            }
        }

        /* User 로그인 */
        public ResponseEntity userLogin (User user){
            String ResponsePw = user.getUserPassword();
            /* Email 찾기 */
            try {
                findEmail(user).getUserEmail();
            }
            catch (NullPointerException e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_ID_FAIL);
            }
            String encodePassword = (findEmail(user)).getUserPassword();
            /* 입력받은 비밀번호와 암호화로 저장된 비밀번호 비교 */
            if (passwordEncoder.matches(ResponsePw,encodePassword)) {
                String DBUuid = findEmail(user).getUserUuid();
                return ResponseEntity.status(HttpStatus.OK).body(DBUuid);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SignUpConstant.LOGIN_PASSWORD_FAIL);
            }
        }

        /* User 로그인 - UserEmail 검색 */
        public User findEmail (User user){
            return userMapper.findEmail(user);
        }

        /* User 마이페이지 - 유저 정보 불러오기 */
        public ResponseEntity<Object> myPage (String uuid) {
            UserDataDto userDataDto = new UserDataDto();
            userDataDto.setUserEmail(findUuid(uuid).getUserEmail());
            userDataDto.setUserNickname(findUuid(uuid).getUserNickname());
            userDataDto.setUserJoinDate(findUuid(uuid).getUserJoinDate());
            userDataDto.setUserProfileImage(findUuid(uuid).getUserProfileImage());
            return ResponseEntity.status(HttpStatus.OK).body(userDataDto);
        }

        /* User 마이페이지 - 유저 정보 검색 */
        public User findUuid (String uuid){
            return userMapper.findUuid(uuid);
        }

        /* User 비밀번호 찾기 - 랜덤 비밀번호 암호화 */
        public ResponseEntity changeRandomPassword (User user) {
            /* 임시 비밀번호 생성 */
            String ChgUserPassword = RandomObject();
            /* 입력받은 Email, Name로 회원가입했는지 검사 */
            Integer userEmail = userMapper.UserEmailConform(user.getUserEmail());
            Integer userName = userMapper.UserNameConform(user.getUserName());
            if (userEmail == 1 && userName == 1) {
                /* 임시 비밀번호 암호화 */
                user.setUserPassword(passwordEncoder.encode(ChgUserPassword));
                /* 이메일 발송 */
                emailService.sendMail(user.getUserEmail(), ChgUserPassword, "ChangePassword");
                userMapper.changeRandomPassword(user);
                return ResponseEntity.status(HttpStatus.OK).body(SignUpConstant.FIND_PASSWORD_NAME_SUCCESS);
            } else if (userEmail != 1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_EMAIL_FAIL);
            } else {
                findEmail(user).getUserName();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_PASSWORD_NAME_FAIL);
            }
        }
        /* User 비밀번호 찾기 - 임시 비밀번호 10자리 만들기 */
        public String RandomObject () {
            Random rnd = new Random();
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < 10; i++) {
                if (rnd.nextBoolean()) {
                    buf.append((char) ((int) (rnd.nextInt(26)) + 97));
                } else {
                    buf.append((rnd.nextInt(10)));
                }
            }
            return buf.toString();
        }

        /* User 아이디 찾기 */
        public ResponseEntity FindId (User user){
            /* 입력받은 PhoneNumber, Name으로 회원가입했는지 검사 */
            Integer DBPhoneNu = userMapper.PhoneNumberCheck(user.getUserPhoneNumber());
            Integer DBName = userMapper.UserNameConform(user.getUserName());
            if (DBPhoneNu == 1 && DBName == 1) {
                /* Email 찾기 */
                String DBEmail = String.valueOf(userMapper.FindEmail(user).getUserEmail());
                return ResponseEntity.status(HttpStatus.OK).body(DBEmail);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SignUpConstant.FIND_ID_FAIL);
            }
        }

        /* User 마이페이지 - 비빌번호 재설정 */
        public ResponseEntity changePW (User user){
            String userName = findUuid(user.getUserUuid()).getUserName();
            /* 입력받은 변경할 비밀번호 암호화 */
            String ChgPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(ChgPassword);
            userMapper.changePW(user);
            return ResponseEntity.status(HttpStatus.OK).body(userName + MyPageConstant.MYPAGE_CLEAR);
        }

        /* User 프로필 이미지 가져오기 */
        public  ResponseEntity<byte[]> getImage (String uuid) {
            String savePath = SAVE_PATH;
            InputStream in = null;
            String userImage = savePath + uuid;
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
