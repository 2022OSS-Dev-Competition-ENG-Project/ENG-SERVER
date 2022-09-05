package com.example.userservice.service;

import com.example.userservice.dto.FacilityDto;
import com.example.userservice.dto.ManagerDto;
import com.example.userservice.mapper.ManagerMapper;
import com.example.userservice.vo.AdminVO;
import com.example.userservice.vo.FindManagerIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ManagerDto findManagerName(ManagerDto managerDto) {
        return managerMapper.findManagerName(managerDto);
    }

    public String findManagerId(FindManagerIdVo findManagerIdVo){
        return managerMapper.findManagerId(findManagerIdVo);
    }

    public void changeManagerPW(ManagerDto managerDto) {
        //변경할 비밀번호 해싱
        String ChgPassword = passwordEncoder.encode(managerDto.getManagerPassword());
        managerDto.setManagerPassword(ChgPassword);
        managerMapper.changeManagerPW(managerDto);
    }

    public ManagerDto findManagerUuid(String uuid) {
        return managerMapper.findManagerUuid(uuid);
    }

    public ManagerDto findManagerID(String managerEmail) {
        return managerMapper.findManagerID(managerEmail);
    }

    public void resetPassword (ManagerDto managerDto) {
        //랜덤 비밀번호 해싱
        String encodePassword = passwordEncoder.encode(managerDto.getManagerPassword());
        managerDto.setManagerPassword(encodePassword);

        managerMapper.resetPassword(managerDto);
    }

    public FacilityDto facilityMasterCheck(String facilityNo){
        return managerMapper.facilityMasterCheck(facilityNo);
    }

    public List<AdminVO> AdminList(String facilityNo) throws Exception {
        return managerMapper.AdminList(facilityNo);
    }

    public void updateGrade(Integer facilityGrade,String managerName, String facilityNo){
        managerMapper.updateGrade(facilityGrade, managerName, facilityNo);
    }
}
