package com.example.userservice.mapper;
import com.example.userservice.dto.ManagerDto;
import com.example.userservice.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {

    void SignupManager(ManagerDto managerDto);

    ManagerDto ManagerEmailCheck(String managerEmail);

    void ManagerEmailCode(String managerEmail, Integer LoginKey);

    void ManagerEmailConform(String managerEmail,String temporaryUuid);

    ManagerDto ManagerNickNameCheck(String nickname);

    void NickNameCheckAccess(String nickName, Integer AccessType, String managerEmail);

    ManagerDto findManagerEmail(ManagerDto managerDto);
}
