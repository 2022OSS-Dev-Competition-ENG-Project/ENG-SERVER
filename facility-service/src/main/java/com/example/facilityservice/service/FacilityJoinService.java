package com.example.facilityservice.service;

import com.example.facilityservice.client.ManagerServiceClient;
import com.example.facilityservice.mapper.FacilityJoinMapper;
import com.example.facilityservice.mapper.FacilityMapper;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.ResponseFacilityManagerList;
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
    private FacilityMapper facilityMapper;
    private ManagerServiceClient managerServiceClient;

    @Autowired
    public FacilityJoinService(FacilityJoinMapper facilityJoinMapper, FacilityMapper facilityMapper, ManagerServiceClient managerServiceClient) {
        this.facilityJoinMapper = facilityJoinMapper;
        this.facilityMapper = facilityMapper;
        this.managerServiceClient = managerServiceClient;
    }

    /* 시설물 가입 */
    public ResponseEntity joinFacility(RequestJoinFacility joinFacility, String table, String colum,Integer type){

        /* 시설물 가입, 시설물 탈퇴 - 중복 가입 검사 */
        if (facilityJoinMapper.conflictValidJoin(joinFacility.getFacilityNum(),joinFacility.getUuid(), table, colum) == 1){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_JOIN_CONFLICT);
        }

        /* 시설물 가입 */
        if(type == 0){
            facilityJoinMapper.joinFacilityUser(joinFacility.getFacilityNum(), joinFacility.getUuid(), table);
        }else {
            facilityJoinMapper.joinFacilityManager(joinFacility.getFacilityNum(), joinFacility.getUuid(), table);
            facilityMapper.setGrade(joinFacility.getUuid(), joinFacility.getFacilityNum(), "매니저");
        }
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_JOIN_COMPLETE);
    }

    /* 시설물 탈퇴 */
    public ResponseEntity resignationFacility(String facilityNum, String userUuid, String table, String colum) {

        /* 가입 되어 있지 않았을 때 */
        if (facilityJoinMapper.isValidJoin(facilityNum,userUuid, table, colum) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_RESIGNATION_FAIL);

        } else {
            facilityJoinMapper.resignationFacility(facilityNum, userUuid, table, colum);
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

    /* 시설물 가입 - 매니저 검색 ( OpenFeign )
    *  매니저의 이름과 전화번호 데이터를 입력했을 경우 매니저의 UUID값을 Return 받을수 있다.
    * */
    public ResponseEntity findJoinManager(String managerName, String managerPhoneNumber){

        if (facilityJoinMapper.findJoinManager(managerName, managerPhoneNumber) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MANAGER_NOT_FOUND);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(facilityJoinMapper.findJoinManager(managerName, managerPhoneNumber));
        }
    }

    /* 시설물에 가입된 매니저 불러오기 */
    public ResponseEntity getFacilityManagerList(String facilityNum) {
        List<ResponseFacilityManagerList> facilityManagerLists = facilityJoinMapper.getFacilityManagerList(facilityNum);
        return ResponseEntity.status(HttpStatus.OK).body(facilityManagerLists);
    }


    /* 시설물에 가입된 매니저 삭제 */
    public ResponseEntity joinDeleteManager(String uuid, String managerUuid, String facilityNum) {
        /* 매니저 등급 불러오기
        * 삭제하려는 매니저의 등급이 관리자여야 한다.
        * */



        /* 삭제를 하려는 사람이 오너일 경우 */
        if (facilityJoinMapper.getManagerGrade(uuid,facilityNum).equals("오너")){
            /* 오너이면서 자기 자신을 삭제하려 했을 경우*/
            if (uuid.equals(managerUuid)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NOT_CONDITION_DELETE);
            }
            facilityJoinMapper.joinDeleteManager(managerUuid, facilityNum);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_DELETE_MANAGER);
        }

        /* 삭제를 하려는 사람이 마스터이고 삭제를 당하는 사람이 마스터 일경우 */
        if (!facilityJoinMapper.getManagerGrade(uuid,facilityNum).equals("마스터") ||
                facilityJoinMapper.getManagerGrade(managerUuid,facilityNum).equals("마스터")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NOT_CONDITION_DELETE);
        } else {
            /* 마스터이면서 자기 자신을 삭제 하려 했을 경우 */
            if (uuid.equals(managerUuid)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NOT_CONDITION_DELETE);
            }
            facilityJoinMapper.joinDeleteManager(managerUuid, facilityNum);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_DELETE_MANAGER);
        }

    }
}
