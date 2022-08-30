package com.example.userservice.service;

import com.example.userservice.dto.ManagerDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.ManagerMapper;
import com.example.userservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ManagerService {
    @Autowired
    public ManagerService(ManagerMapper managerMapper, PasswordEncoder passwordEncoder) {
        this.managerMapper = managerMapper;
        this.passwordEncoder = passwordEncoder;
    }
    ManagerMapper managerMapper;
    PasswordEncoder passwordEncoder;

    public void SignupManager(ManagerDto managerDto) {
        log.info(managerDto.toString());
        managerDto.setManagerUuid(UUID.randomUUID().toString());
        // 해싱
        String encodePassword = passwordEncoder.encode(managerDto.getManagerPassword());
        managerDto.setManagerPassword(encodePassword);
        managerMapper.SignupManager(managerDto);
    }

    public ManagerDto ManagerEmailCheck(String managerEmail){
        return managerMapper.ManagerEmailCheck(managerEmail);
    }

    public void ManagerEmailCode(String userEmail,Integer LoginKey) {
        managerMapper.ManagerEmailCode(userEmail, LoginKey);
    }

    public void  ManagerEmailConform(String managerEmail, String temporaryUuid){
        managerMapper.ManagerEmailConform(managerEmail,temporaryUuid);
    }

    public ManagerDto ManagerNickNameCheck(String nickname){
        return managerMapper.ManagerNickNameCheck(nickname);
    }

    public void NickNameCheckAccess(String managerNickname, Integer AccessType, String managerEmail){
        managerMapper.NickNameCheckAccess(managerNickname, AccessType, managerEmail);
    }

    public ManagerDto findManagerEmail(ManagerDto managerDto) {
        return managerMapper.findManagerEmail(managerDto);
    }
}
