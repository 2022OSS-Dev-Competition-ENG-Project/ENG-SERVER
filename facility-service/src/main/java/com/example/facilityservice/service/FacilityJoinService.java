package com.example.facilityservice.service;

import com.example.facilityservice.dto.Facility;
import com.example.facilityservice.mapper.FacilityJoinMapper;
import com.example.facilityservice.vo.RequestFacilityJoin;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.RequestResignationFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.facilityservice.constant.FacilityJoinConstant.*;

@Service
public class FacilityJoinService {

    private FacilityJoinMapper facilityJoinMapper;

    @Autowired
    public FacilityJoinService(FacilityJoinMapper facilityJoinMapper) {
        this.facilityJoinMapper = facilityJoinMapper;
    }

    /* 시설물 가입 */
    public ResponseEntity joinFacility(RequestJoinFacility joinFacility,String table){

        /* 시설물 가입 - 중복 가입 검사 */
        if (facilityJoinMapper.conflictJoin(joinFacility) == 0){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_JOIN_CONFLICT);
        }else {
            /* 시설물 가입 */
            facilityJoinMapper.joinFacilityUser(joinFacility,table);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_JOIN_COMPLETE);
        }
    }

    /* 시설물 탈퇴 */
    public ResponseEntity resignationFacility(RequestResignationFacility resignationFacility, String table) {

        facilityJoinMapper.resignationFacility(resignationFacility,table);
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_RESIGNATION_COMPLETE);
    }


    /* 내가 가입한 시설 좋아요 - User */
    public ResponseEntity myFacilityLike(String userUuid, String facilityNum) {
        /*좋아요 여부 확인*/
        if(facilityJoinMapper.facilityLikeBool(userUuid, facilityNum) == 0){
            facilityJoinMapper.updateFacilityLike(userUuid, facilityNum, 1);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_LIKE_COMPLETE);
        }else{
            facilityJoinMapper.updateFacilityLike(userUuid, facilityNum, 0);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_LIKE_CANCEL_COMPLETE);
        }
    }
    /* 내가 가입한 시설물 불러오기 */
    public ResponseEntity getMyFacilityList(String uuid,String table) {
        List<Facility> facilityList = facilityJoinMapper.getManagerFacilityList(uuid,table);
        return ResponseEntity.status(HttpStatus.OK).body(facilityList);
    }


}