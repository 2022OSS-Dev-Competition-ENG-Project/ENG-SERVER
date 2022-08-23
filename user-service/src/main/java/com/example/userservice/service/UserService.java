package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void SignupUser(UserDto userDto) {

        log.info(userDto.toString());
        userDto.setUserUuid(UUID.randomUUID().toString());
        // 해싱
        String encodePassword = passwordEncoder.encode(userDto.getUserPassword());
        userDto.setUserPassword(encodePassword);

        userMapper.SignupUser(userDto);
    }
    public UserDto registerEmailCheck(String email){
        return userMapper.registerEmailCheck(email);
    }

    public void  EmailConform(String userEmail, String temporaryUuid){
        userMapper.EmailConform(userEmail,temporaryUuid);
    }

    public UserDto registerNickNameCheck(String nickname){
        return userMapper.registerNickNameCheck(nickname);
    }

    public void NickNameCheck(String nickName, Integer AccessType, String userEmail){
        userMapper.NickNameCheck(nickName, AccessType, userEmail);
    }

    public UserDto findEmail(UserDto userDto) {
        return userMapper.findEmail(userDto);
    }

    public void EmailCode(String userEmail,Integer LoginKey) {
        userMapper.EmailCode(userEmail, LoginKey);
    }

    public String RandomUuid () {
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
}
