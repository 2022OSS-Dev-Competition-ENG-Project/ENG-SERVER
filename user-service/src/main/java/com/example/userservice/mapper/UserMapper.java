package com.example.userservice.mapper;
import com.example.userservice.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void SignupUser(UserDto userDto);

    void EmailConform(String userEmail,String temporaryUuid);

    UserDto findEmail(UserDto userDto);

    UserDto registerEmailCheck(String email);

    UserDto registerNickNameCheck(String nickname);

    void NickNameCheck(String nickName, Integer AccessType, String userEmail);

    void EmailCode(String userEmail, Integer LoginKey);
}