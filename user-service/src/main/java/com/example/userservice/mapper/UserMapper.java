package com.example.userservice.mapper;
import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.FindIdVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void SignupUser(UserDto userDto);

    void EmailConform(String userEmail,String temporaryUuid);

    UserDto findEmail(UserDto userDto);

    UserDto findName(FindIdVo findIdVo);

    UserDto findPhoneNum(UserDto userDto);

    UserDto findUuid(String uuid);

    UserDto registerEmailCheck(String email);

    UserDto registerNickNameCheck(String nickname);

    UserDto registerPhoneNumberCheck(String userPhoneNumber);

    void NickNameCheck(String nickName, Integer AccessType, String userEmail);

    void EmailCode(String userEmail, Integer LoginKey);

    void changeRandomPassword(UserDto userDto);

    UserDto findUserUuid(UserDto userDto);

    void changePW(UserDto userDto);

    /* 추후 변경 */
    String findId(FindIdVo findIdVo);
}