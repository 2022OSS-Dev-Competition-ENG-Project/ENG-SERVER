package com.example.engproject.mapper;
import com.example.engproject.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void SignupUser(UserDto userDto);

    UserDto EmailConform(String email);

    UserDto findEmail(UserDto userDto);
}