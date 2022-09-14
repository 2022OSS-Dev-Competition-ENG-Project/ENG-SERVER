package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.vo.FindIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Autowired
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    /* User 회원가입 */
    public void SignupUser(UserDto userDto) {
        LocalDate localDate = LocalDate.now();
        userDto.setUserUuid(UUID.randomUUID().toString());
        userDto.setUserJoinDate(localDate);
        String encodePassword = passwordEncoder.encode(userDto.getUserPassword());
        userDto.setUserPassword(encodePassword);
        userMapper.SignupUser(userDto);
    }

    /* User 회원가입 - 중복된 Email 검사하기 */
    public UserDto registerEmailCheck(String email){
        return userMapper.registerEmailCheck(email);
    }

    /* User 회원가입 - Email 중복 검사 성공시 */
    public void  EmailConform(String userEmail, String temporaryUuid){
        userMapper.EmailConform(userEmail,temporaryUuid);
    }

    /* User 회원가입 - Email 인코드 검사 성공시 */
    public void EmailCode(String userEmail) {
        Integer LoginKey = 1;
        userMapper.EmailCode(userEmail, LoginKey);
    }

    /* User 회원가입 - 중복된 Nickname 검사하기 */
    public UserDto registerNickNameCheck(String nickname){
        return userMapper.registerNickNameCheck(nickname);
    }

    /* User 회원가입 - Nickname 검사 성공시 */
    public void NickNameCheck(String userNickname, String userEmail){
        Integer AccessType = 1;
        userMapper.NickNameCheck(userNickname, AccessType, userEmail);
    }

    /* User 로그인 */
    public UserDto findEmail(UserDto userDto) {
        return userMapper.findEmail(userDto);
    }

    /* User 로그인 - 성공시 uuid 검색 */
    public UserDto findUuid(String uuid) {
        return userMapper.findUuid(uuid);
    }

    public String RandomObject () {
        Random rnd = new Random();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < 20; i++) {
            if (rnd.nextBoolean()) {
                buf.append((char) ((int) (rnd.nextInt(26)) + 97));
            } else {
                buf.append((rnd.nextInt(10)));
            }
        }
        return buf.toString();
    }

    /* User 비밀번호 찾기 - 랜덤 비밀번호 생성 */
    public void changeRandomPassword (UserDto userDto) {
        String encodePassword = passwordEncoder.encode(userDto.getUserPassword());
        userDto.setUserPassword(encodePassword);

        userMapper.changeRandomPassword(userDto);
    }

    /* User 아이디 찾기 */
    public String findId(FindIdVo findIdVo){
        return userMapper.findId(findIdVo);
    }

    /* User 마이페이지 - 비빌번호 재설정 */
    public void changePW(UserDto userDto) {
        String ChgPassword = passwordEncoder.encode(userDto.getUserPassword());
        userDto.setUserPassword(ChgPassword);
        userMapper.changePW(userDto);
    }
}