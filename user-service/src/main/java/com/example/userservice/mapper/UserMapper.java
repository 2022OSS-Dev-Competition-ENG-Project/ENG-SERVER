package com.example.userservice.mapper;
<<<<<<< HEAD
=======


>>>>>>> 848e550e8be41ae0c081d4641e74caca9ba9c911
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