package com.example.userservice.mapper;
import com.example.userservice.dto.FacilityDto;
import com.example.userservice.dto.ManagerDto;
import com.example.userservice.vo.AdminVO;
import com.example.userservice.vo.FindManagerIdVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {

    void SignupManager(ManagerDto managerDto);

    ManagerDto ManagerEmailCheck(String managerEmail);

    void ManagerEmailCode(String managerEmail, Integer LoginKey);

    void ManagerEmailConform(String managerEmail,String temporaryUuid);

    ManagerDto ManagerNickNameCheck(String nickname);

    void NickNameCheckAccess(String nickName, Integer AccessType, String managerEmail);

    ManagerDto findManagerEmail(ManagerDto managerDto);

    ManagerDto findManagerName(ManagerDto managerDto);

    String findManagerId(FindManagerIdVo findManagerIdVo);

    void changeManagerPW(ManagerDto managerDto);

    ManagerDto findManagerUuid(String uuid);

    void resetPassword(ManagerDto managerDto);

    ManagerDto findManagerID(String managerEmail);

    FacilityDto facilityMasterCheck(String facilityNo);

    List<AdminVO> AdminList(String facilityNo) throws Exception;

    void addAdmin(Integer facilityGrade, String managerName, String facilityNo);

    void deleteAdmin(Integer facilityGrade, String managerName, String facilityNo);

    void changeOwner(String managerUuid, String facilityNo);
    }
