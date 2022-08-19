package com.example.userservice.service;


import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        //해싱
        String encodePassword = passwordEncoder.encode(userDto.getUserPassword());
        userDto.setUserPassword(encodePassword);

        userMapper.SignupUser(userDto);
    }

    public UserDto EmailConform(String email){
        return userMapper.EmailConform(email);
    }

    public UserDto findEmail(UserDto userDto) {
        return userMapper.findEmail(userDto);
    }
}
