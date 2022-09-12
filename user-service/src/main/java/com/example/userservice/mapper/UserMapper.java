package com.example.userservice.mapper;
import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.FindIdVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void SignupUser(UserDto userDto);

    void EmailConform(String userEmail,String temporaryUuid);

    UserDto findEmail(UserDto userDto);

    UserDto findUuid(String uuid);

    UserDto registerEmailCheck(String email);

    UserDto registerNickNameCheck(String nickname);

    void NickNameCheck(String nickName, Integer AccessType, String userEmail);

    void EmailCode(String userEmail, Integer LoginKey);

    void changeRandomPassword(UserDto userDto);

    void changePW(UserDto userDto);

    String findId(FindIdVo findIdVo);
}