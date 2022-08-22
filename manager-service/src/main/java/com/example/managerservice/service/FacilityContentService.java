package com.example.managerservice.service;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.mapper.FacilityContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.managerservice.constant.FacilityContentConstant.*;


@Slf4j
@Service
public class FacilityContentService {
    private FacilityContentMapper fcm;

    @Autowired
    public FacilityContentService(FacilityContentMapper facilityContentMapper) {
        this.fcm = facilityContentMapper;
    }

    /* 게시물 등록 */
    public ResponseEntity registerContent(FacilityContentDto fcd) throws NullPointerException{
        try{
            Integer valid = fcm.findValidFacilityContentNum(fcd.getFacilityContentNum());
            /* Content ID 중복 검사 */
            if(valid == 1){
                throw new Exception(FACILITY_CONTENT_CONFLICT);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_CONTENT_CONFLICT);
        }
        fcm.registerContent(fcd);
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_REGISTER_SUCCESS);
    }

    /* 게시물 상세 조회 */
    /* 조건 : 해당 건물 등록 한 사람만 */
    public ResponseEntity contentDetailsView(String uuid,Integer contentId){
        try{
            fcm.facilityInUserValid(uuid, contentId);
            }catch (NullPointerException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_VIEW_VALID_FAIL);
            }
        return ResponseEntity.status(HttpStatus.OK).body(fcm.contentDetailView(contentId));
    }

    /* 게시물 삭제 */
    /* 조건 : 게시물 등록한사람, 게시물 번호 일치*/
    public String deleteContent(String uuid, String contentId){
        try{
            fcm.validContentOwner(uuid,contentId);
        }catch (NullPointerException e){
            return FACILITY_CONTENT_DELETE_VALID_FAIL;
        }
        fcm.deleteContent(uuid,contentId);
        return FACILITY_CONTENT_DELETE_VALID_COMPLETE;
    }


}
