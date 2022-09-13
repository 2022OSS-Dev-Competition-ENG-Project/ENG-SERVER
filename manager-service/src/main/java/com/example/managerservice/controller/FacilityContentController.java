package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.dto.FacilityContentLikeDto;
import com.example.managerservice.service.FacilityContentService;
import com.example.managerservice.service.ImageService;
import com.example.managerservice.vo.ContentListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.managerservice.constant.FacilityContentConstant.FACILITY_CONTENT_REGISTER_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FacilityContentController {

    private FacilityContentService fcs;
    private ImageService is;
    LocalDateTime now = LocalDateTime.now();




    @Autowired
    public FacilityContentController(FacilityContentService fcs, ImageService is) {
        this.fcs = fcs;
        this.is = is;
    }

    /* 게시물 등록 */
    @PostMapping(value = "/facility/content/register",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity registerContent(@RequestPart FacilityContentDto facilityContentDto,
                                          @RequestPart(required = false) List<MultipartFile> images) throws Exception{

        /* 현재 시간 가져와서 시간을 저장 */
        LocalDateTime datetime = LocalDateTime.of(now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());
        log.info(datetime.toString());
        facilityContentDto.setContentDate(datetime);


        /* 2. 등록된 게시물의 번호와 시설물 번호를 들고 온다. */
        long contentNum = fcs.registerContent(facilityContentDto);
        String facilityNo = facilityContentDto.getFacilityNo();
        log.info("contentNum : " + contentNum);

        /* 3. 게시물 번호와 시설물 번호를 들고 오면 ImageService.saveContentImage 로 보낸다. */
        if(facilityContentDto.getContentType() == 1) {
            if(!images.isEmpty()){
                StringBuilder sb = new StringBuilder();
                for(String item : is.saveContentImage(images, facilityNo, (int)contentNum)){
                    sb.append(item);
                }
            fcs.contentImageUpdate(sb.toString(), (int)contentNum);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_REGISTER_SUCCESS);
    }

    /*게시물 상세 보가*/
    @GetMapping("/facility/content/{userUuid}/{contentId}")
    public ResponseEntity contentDetailView(@PathVariable("contentId") Integer contentId,
                                            @PathVariable("userUuid") String userUuid){

        return ResponseEntity.status(HttpStatus.OK).body(fcs.contentDetailsView(contentId,userUuid));
    }

    /* 게시물 좋아요 */
    @PostMapping("/facility/content/liked")
    public ResponseEntity contentLike(@RequestBody FacilityContentLikeDto facilityContentLikeDto) {
        /* 좋아요 여부 알아보기 */
        if(fcs.contentLikeBool(
                facilityContentLikeDto.getUserUuid(),
                facilityContentLikeDto.getContentNum()
        ) == 0) /* 좋아요 누른 상태가 아닐 때*/
        {
            return ResponseEntity.status(HttpStatus.OK).body(fcs.facilityContentLike(facilityContentLikeDto));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(fcs.deleteContentLike(
                    facilityContentLikeDto.getUserUuid(),
                    facilityContentLikeDto.getContentNum()));
        }
    }

    /* 게시물 좋아요 개수 불러오기 */
    @GetMapping("/facility/content/liked/{contentNum}")
    public ResponseEntity getLikeCount(@PathVariable("contentNum")Integer contentNum){
        log.info(contentNum.toString());
        try {
        fcs.getLikeCount(contentNum).toString();
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getLikeCount(contentNum));
    }

    /* 게시물 삭제 - 관리자용 */
    @GetMapping("/facility/content/delete/mg/{facilityNo}/{contentId}")
    public ResponseEntity deleteContentManager(@PathVariable("contentId") Integer contentId,
                                               @PathVariable("facilityNo") String facilityNo){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.deleteContentManager(contentId, facilityNo));
    }

    /*  게시물 삭제 */
    @GetMapping("/facility/content/delete/{uuid}/{contentNum}")
    public ResponseEntity deleteContent(@PathVariable("uuid") String uuid,
                                        @PathVariable("contentNum") Integer contentNum){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.deleteContent(uuid, contentNum));
    }

    /* 메인 배너에 보일 공지 게시물 불러오기 */
    @GetMapping("/facility/{facilityNo}/content/{type}/main")
    public ResponseEntity getContentListMain(
            @PathVariable("facilityNo") String facilityNo,
            @PathVariable("type") Integer type){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getContentListMain(facilityNo, type));
    }


    /* 게시물 리스트 불러오기 - Pagination */
    @GetMapping("/facility/content/{facility}/{type}/{position}/list")
    public ResponseEntity<List<ContentListVo>> getMyFacilityContentList(
            @PathVariable("facility") String facility,
            @PathVariable("type") Integer type,
            @PathVariable("position") Integer position){
        return ResponseEntity.status(HttpStatus.OK)
                .body(fcs.getContentList(facility, type, position));

    }

    /* 게시물 총 개수 불러오기 */
    @GetMapping("/facility/content/getcount/{facilityNo}/{type}")
    public Integer getContentCount(
            @PathVariable("facilityNo") String facilityNo,
            @PathVariable("type") Integer type){

        return fcs.getContentCount(facilityNo,type);
    }

    /* 내가 쓴 게시물 5개만 불러오기 */
    @GetMapping("/facility/content/main/user/{userUuid}")
    public ResponseEntity getMyContentLt(@PathVariable("userUuid")String userUuid){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getMyContentLt(userUuid));
    }

    /* 내가 쓴 게시물 불러오기 */
    @GetMapping("/facility/my/content/{userUuid}")
    public ResponseEntity getMyContent(@PathVariable("userUuid")String userUuid){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getMyContent(userUuid));
    }

}
