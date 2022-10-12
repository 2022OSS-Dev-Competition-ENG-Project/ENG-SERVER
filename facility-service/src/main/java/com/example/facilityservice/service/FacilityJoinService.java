package com.example.facilityservice.service;

import com.example.facilityservice.dto.Facility;
import com.example.facilityservice.mapper.FacilityJoinMapper;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.RequestResignationFacility;
import com.example.facilityservice.vo.ResponseGetMyFacility;
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
    public ResponseEntity joinFacility(RequestJoinFacility joinFacility,String table, String colum){

        /* 시설물 가입, 시설물 탈퇴 - 중복 가입 검사 */
        if (facilityJoinMapper.conflictValidJoin(joinFacility.getFacilityNum(),joinFacility.getUuid(), table, colum) == 1){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_JOIN_CONFLICT);
        }else {
            /* 시설물 가입 */
            facilityJoinMapper.joinFacilityUser(joinFacility.getFacilityNum(),joinFacility.getUuid(),table);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_JOIN_COMPLETE);
        }
    }

    /* 시설물 탈퇴 */
    public ResponseEntity resignationFacility(RequestResignationFacility resignationFacility, String table, String colum) {

        /* 가입 되어 있지 않았을 때 */
        if (facilityJoinMapper.isValidJoin(resignationFacility.getFacilityNum(),resignationFacility.getUuid(), table, colum) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_RESIGNATION_FAIL);

        } else {
            facilityJoinMapper.resignationFacility(resignationFacility.getFacilityNum(),resignationFacility.getUuid(),table, colum);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_RESIGNATION_COMPLETE);
        }
    }

    /* 내가 가입한 시설물 불러오기 */
    public ResponseEntity getMyFacilityList(String uuid, String table, String colum, String type) {

        List<ResponseGetMyFacility> facilityList = null;

        /* type이 Manager인 경우*/
        if (type == "manager") {
            facilityList = facilityJoinMapper.getMyFacilityListManager(uuid, table, colum);
        }

        /* type이 user인 경우*/
        if (type == "user"){
            facilityList = facilityJoinMapper.getMyFacilityListUser(uuid, table, colum);
        }

        /* 아무 데이터가 없을 경우 */
        if (facilityList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GET_MY_FACILITY_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(facilityList);
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



}
