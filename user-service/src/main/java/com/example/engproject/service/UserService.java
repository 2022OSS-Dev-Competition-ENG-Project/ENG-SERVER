package com.example.engproject.service;

import com.example.engproject.dto.UserDto;
import com.example.engproject.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDto EmailConform(String email, String LoginKey){
        return userMapper.EmailConform(email,LoginKey);
    }

    public UserDto registerNickNameCheck(String nickName){
        return userMapper.registerNickNameCheck(nickName);
    }

    public UserDto findEmail(UserDto userDto) {
        return userMapper.findEmail(userDto);
    }
}
