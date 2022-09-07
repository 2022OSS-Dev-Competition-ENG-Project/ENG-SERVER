package com.example.managerservice.service;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.mapper.FacilityMapper;
import com.example.managerservice.vo.GetMyFacilityListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.managerservice.constant.FacilityConstant.FACILITY_MY_DELETE;

@Slf4j
@Service
public class FacilityService {

    private FacilityMapper facilityMapper;

    @Autowired
    public FacilityService(FacilityMapper facilityMapper) {
        this.facilityMapper = facilityMapper;
    }

    /* 시설물 등록하기 */
    public void registerFacility(FacilityDto facilityDto){
        facilityMapper.registerFacility(facilityDto);
    }

    /* 시설물 등록하기 - 매니저의 계정인지 검사하기 */
    public Integer validManager(String managerUuid){
        return facilityMapper.validManager(managerUuid);
    }

    /* 시설물 등록하기 - 중복된 시설물인지 검사 하기 */
    public Integer validConflictFacility(String facilityAddress){
        return facilityMapper.validConflictFacility(facilityAddress);
    }

    public FacilityDto getFacilityInfo(String facilityAddress){
        return facilityMapper.getFacilityInfo(facilityAddress);
    }


    /* 시설물 가입하기 - 매니저 - Manager & User */
    public void joinFacility(String uuid, String facilityNo, String table){
        facilityMapper.joinFacility(uuid, facilityNo, table);
    }

    /* 시설물 가입하기 - 사용자 확인 */
    public Integer joinValidFacility(String uuid, String uuidType, String table){
        return facilityMapper.joinValidFacility(uuid,uuidType,table);
    }

    /* 시설물 가입하기 - 존재하는 시설물인지 확인 */
    public Integer validFacility(String facilityNo){
        return facilityMapper.validFacility(facilityNo);
    }

    /* 시설물 거압허가 - 중복된 시설물인지 검사 하기 - Manager & User */
    public Integer conflictJoinValidFacility(String facilityNo, String uuid, String uuidType,String table){
        return facilityMapper.conflictJoinValidFacility(facilityNo, uuid, uuidType, table);
    }

    /* 내가 가압한 시설물 불러오기 */
    public List<GetMyFacilityListVo> getMyFacilityList(String uuid, String table, String colum){
        return facilityMapper.getMyFacilityList(uuid,table, colum);
    }

    /* 내가 가입한 시설물 삭제하기 */
    public String deleteMyFacility(String uuid, String facilityNo, String table, String colum) {
        facilityMapper.deleteMyFacility(uuid,facilityNo,table, colum);
        return FACILITY_MY_DELETE;
    }

    /* 내가 가입한 시설 좋아요 */
    public void myFacilityLike(String userUuid, String facilityNo, Integer value ){
        facilityMapper.myFacilityLike(userUuid, facilityNo, value);
    }

    /* 내가 가입한 시설 좋아요 여부 */
    public Integer myFacilityLikeBool(String userUuid, String facilityNo){
        return facilityMapper.myFacilityLikeBool(userUuid, facilityNo);
    }

    public int validJoinFacility(String uuid, String facilityNo, String table, String colum) {
        log.info(uuid + " " + facilityNo + " " + table + " " + colum);
        return facilityMapper.validJoinFacility(uuid, facilityNo, table, colum);
    }

    /* 내 시설물 삭제 */
    public String deleteFacility(String managerUuid,String facilityNo) {
        try {
            /* 삭제를 할수 있는 조건인지 검사( Manager이며 FacilityOwner이여야 한다.) */
            log.info(facilityMapper.deleteValidFacility(managerUuid, facilityNo).toString());
        }catch (NullPointerException e){
            return "시설물의 주인만 시설물을 삭제 하실수 있습니다.";
        }
        log.info(managerUuid + "" + facilityNo);
        facilityMapper.deleteFacility(facilityNo);
        return "시설물 삭제";
    }

    public void registerManager(String facilityOwner, String facilityNo) {
        facilityMapper.registerManager(facilityOwner,facilityNo);
    }
}
