package com.example.facilityservice.service;

import com.example.facilityservice.dto.FacilityNotice;
import com.example.facilityservice.mapper.FacilityNoticeMapper;
import com.example.facilityservice.vo.ResponseGetNoticeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.facilityservice.constant.FacilityContentConstant.FACILITY_NOTICE_CREATE;

@Service
public class FacilityNoticeService {

    private FacilityNoticeMapper facilityNoticeMapper;
    private LocalDateTime now = LocalDateTime.now();
    private ContentImageService contentImageService;

    @Autowired
    public FacilityNoticeService(FacilityNoticeMapper facilityNoticeMapper, ContentImageService contentImageService) {
        this.facilityNoticeMapper = facilityNoticeMapper;
        this.contentImageService = contentImageService;
    }

    /* 공지 생성*/
    public ResponseEntity createNotice(FacilityNotice facilityNotice, MultipartFile image) throws Exception {

        Integer noticeNum;
        /* 현재 시간 가져와서 시간을 저장 */
        LocalDateTime datetime = LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());
        facilityNotice.setNoticeDate(datetime);


        /* 2. 등록된 게시물의 번호와 시설물 번호를 들고 온다. */
        facilityNoticeMapper.createNotice(facilityNotice);

        /* 3. 게시물 번호와 시설물 번호를 들고 오면 ImageService.saveContentImage 로 보낸다. */
        if (image !=null) {
            String saveDbPath = contentImageService.saveContentImage(image, facilityNotice.getFacilityNum(), facilityNotice.getNoticeNum());
            facilityNoticeMapper.contentImageUpdate(saveDbPath, facilityNotice.getNoticeNum());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(FACILITY_NOTICE_CREATE);
    }

    /* 공지 상세 보기 */
    public ResponseEntity viewNoticeDetail(Integer noticeNum) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityNoticeMapper.viewNoticeDetail(noticeNum));
    }

    /* 공지 리스트 불러오기 */
    public ResponseEntity getNoticeList(String facilityNum, Integer count) {
        List<ResponseGetNoticeList> facilityNoticeList;

        if (count == null){
            facilityNoticeList = facilityNoticeMapper.getNoticeList(facilityNum);
        }else {
            facilityNoticeList = facilityNoticeMapper.getNoticeListLt(facilityNum,count);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facilityNoticeList);
    }
}
