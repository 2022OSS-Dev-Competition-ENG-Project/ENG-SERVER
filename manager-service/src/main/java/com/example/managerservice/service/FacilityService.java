package com.example.managerservice.service;

import com.example.managerservice.constant.RegisterConstant;
import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.dto.FacilityJoinDto;
import com.example.managerservice.mapper.FacilityMapper;
import com.example.managerservice.vo.GetMyFacilityList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.managerservice.constant.FacilityConstant.*;
import static com.example.managerservice.constant.FacilityContentConstant.FACILITY_MY_JOIN_SUCCESS;

@Slf4j
@Service
public class FacilityService {

    private FacilityMapper facilityMapper;

    @Autowired
    public FacilityService(FacilityMapper facilityMapper) {
        this.facilityMapper = facilityMapper;
    }

    /* 시설물 등록 */
    public String registerFacility(FacilityDto facilityDto) {
        facilityMapper.registerFacility(facilityDto);
        return RegisterConstant.REGISTER_SUCCESS;
    }

    /* 주소로 시서물 상제 정보 찾기 */
    public String findDetailFacilityAd(String facilityAddress) {
        FacilityDto facilityDto;
        facilityDto = facilityMapper.findDetailFacilityAd(facilityAddress);
        return facilityDto.getFacilityAddress();
    }

    /* 이름, 주소로 정보 찾기 */
    public FacilityDto nameAndAddressToDto(FacilityDto facilityDto){
        FacilityDto fa = new FacilityDto();
        fa = facilityMapper.nameAndAddressToDto(facilityDto);
        log.info(fa.toString());
        return facilityMapper.nameAndAddressToDto(facilityDto);
    }

    /* 아이디로 시서물 상제 정보 찾기 */
    public Integer findDerailFacilityId(String userUuid, String facilityId){
        return facilityMapper.findDetailFacilityId(userUuid, facilityId);
    }

    /* 시설물 번호로 시설 조회*/
    public FacilityDto findDetailFacilityFn(String facilityNo) {
        FacilityDto fd = facilityMapper.findDetailFacilityFn(facilityNo);
        return fd;
    }

    /* 사용자 시설 추가 */
    public String facilityUserJoin(String uuid, String userFacility){
        FacilityJoinDto fjd = new FacilityJoinDto();
        fjd.setUserUuid(uuid);
        fjd.setUserFacility(userFacility);
        log.info(fjd.toString());
        facilityMapper.facilityUserJoin(fjd);
        return FACILITY_MY_JOIN_SUCCESS;
    }

    /* 내가 등록한 시설 불러오기 */
    public List<GetMyFacilityList> getMyFacilityList(String userUuid){
        List list = facilityMapper.getMyFacilityList(userUuid);
        return list;
    }

    /* 내가 등록한 시설 삭제 */
    public ResponseEntity deleteMyFacility(String uuid, String facilityNo){
        try{
            facilityMapper.deleteMyFacility(uuid,facilityNo);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MY_FACILITY_NOT_DELETE);
        }
        facilityMapper.deleteMyFacility(uuid,facilityNo);
        return ResponseEntity.status(HttpStatus.OK).body(MY_FACILITY_DELETE);
    }

    /* 내가 등록한 시설 좋아요 */
    public void myFacilityLike(String userUuid, String facilityNo, Integer value ){
        facilityMapper.myFacilityLike(userUuid, facilityNo, value);
    }

    /* 내가 등록한 시설 좋아요 여부 */
    public Integer myFacilityLikeBool(String userUuid, String facilityNo){
        return facilityMapper.myFacilityLikeBool(userUuid, facilityNo);
    }

}
