# API 명세 (Read Me)

## 👨🏻‍💼 User ( 사용자 )

- 내가 가입한 시설물 좋아요 누르기
    - **API** : `/api/facility/like/{userUuid}/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```
    "userUuid" : 사용자 UUID
    "facilityNo" : 시설물 고유 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```
        is_like = 0 일때
        "좋아요 처리가 완료되었습니다."
        is_like = 1 일때
        "좋아요 취소 처리가 완료되었습니다."
        ```
        
- 회원가입
    - **API** : `/api/user-service/signup`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```
    {
    	"userEmail" : user의 ID에 해당
    	"userPassword" : user의 Password
    	"userName" : user의 Name
    	"userNickname" : user의 NickName
    	"userPhoneNum" : user의 PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        회원가입 완료
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```json
        회원가입 시도중 오류가 발생했습니다. 이메일 인증과 닉네임 중복체크를 확인해주세요.
        ```
        
- 이메일 중복 체크 및 코드 발송
    - **API** : `/api/user-service/register/check/email/{userEmail}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userEmail" : Email을 통하여 중복 체크
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.
        ```
        
        - 409 *CONFLICT*
        
        ```json
        사용중인 이메일입니다.
        ```
        
    
- 닉네임 중복 체크
    - **API** : `/api/user-service/register/check/nickname/{nickname}/{email}`
    - **Method : GET**
    - **Request**
    
    ```json
    "nickname" : 중복체크할 닉네임,
    "email" : 중복체크가 성공되면 해당 ID의 닉네임으로 지정
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        사용 가능한 닉네임입니다.
        ```
        
        - 409 *CONFLICT*
        
        ```json
        사용중인 닉네임입니다.
        ```
        
- 이메일 코드 확인
    - **API** : `/api/user-service/register/check/email/{email}/{code}`
    - **Method : GET**
    - **Request**
    
    ```json
    "email" : 유저가 Email 중복 체크때 사용한 이메일,
    "code" : 중복체크를 위한 코드번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        이메일 인증이 완료 되었습니다.
        ```
        
        - 203 *NON_AUTHORITATIVE_INFORMATION*
        
        ```json
        잘못된 인증 코드 입니다.
        ```
        
- 로그인
    - **API** : `/api/user-service/login`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```json
    {
        "userEmail" : user회원가입시 기입한 Email,
        "userPassword" : user회원가입시 기입한 Password
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
            "userUuid": user회원가입할때 지정된 Uuid
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```json
        아이디가 틀렸습니다.
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```json
        비밀번호가 틀렸습니다.
        ```
        
- 아이디 찾기
    - **API** : `/api/user-service/FindUserId`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```json
    {    
    	"userName" : user회원가입시 기입한 Name,
        "userPhoneNumber" : user회원가입시 기입한 PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
          "userEmail" : user회원가입시 기입한 Email
        ```
        
        - 404 *NOT_FOUND*
        
        ```json
        입력하신 정보가 없습니다.
        ```
        
- 비밀번호 초기화
    - **API** : `/api/user-service/UserPasswordReset`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```
    {    
    	"userEmail" : user회원가입시 기입한 Email
        "userName" : user회원가입시 기입한 Name
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        "ChgManagerPassword" : 랜덤함수 패스워드
        ```
        
        - 404 NOT_FOUND
        
        ```json
        이메일을 다시 확인해주세요.
        ```
        
        - 404 NOT_FOUND
        
        ```json
        이름을 다시 확인해주세요.
        ```
        
- 마이페이지( 메인 )
    - **API** : `/api/user-service/myPage/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "uuid" : user회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        {
            "userEmail": user회원가입시 기입한 Email,
            "userNickname": user회원가입시 기입한 Nickname,
            "userJoinDate": user회원가입시 기입한 JoinDate,
            "userImg": user회원가입시 기입한 ProfileImage
        }
        ```
        
- 마이페이지( 비밀번호 재 설정 )
    - **API** : `/api/user-service/myPage/changePW/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```json
        "uuid": user회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        userName + 님의 비밀번호가 변경되었습니다.
        ```
        
- 프로필 이미지 저장
    - **API** : `/api/user-service/SaveProfileImage/{uuid}`
    - **Method : GET**
    - **Body :  form-data**
    - **Request**
    
    ```json
    "uuid": user회원가입할때 지정된 Uuid
    
    KEY : images
    VALUES : images.jpg
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        이미지 저장을 성공했습니다.
        ```
        
- 프로필 이미지 가져오기
    - **API** : `/api/user-service/ProfileImage/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "uuid": user회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        fileArray // userImage 출력
        ```
        

## 👨🏻‍💻 Manager ( 매니저 )

- 회원가입
    - **API** : `/api/manager-service/signup`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```json
    {
    	"managerEmail" : manager의 ID에 해당
    	"managerPassword" : manager의 Password
    	"managerName" : manager의 Name
    	"managerNickname" : manager의 NickName
    	"managerPhoneNum" : manager의 PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        회원가입 완료
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```json
        회원가입 시도중 오류가 발생했습니다. 이메일 인증과 닉네임 중복체크를 확인해주세요.
        ```
        
        - 400 *BAD_REQUEST*
        
        ```json
        ResponsePhoneNum + 는 이미 회원가입된 전화번호입니다.
        ```
        
- 이메일 중복 체크 및 코드 발송
    - **API** : `/api/manager-service/register/check/email/{managerEmail}`
    - **Method : GET**
    - **Request**
    
    ```json
    "managerEmail" : Email을 통하여 중복 체크
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.
        ```
        
        - 409 *CONFLICT*
        
        ```json
        사용중인 이메일입니다.
        ```
        
    
- 닉네임 중복 체크
    - **API** : `/api/manager-service/register/check/nickname/{nickname}/{email}`
    - **Method : GET**
    - **Request**
    
    ```json
    "nickname" : 중복체크할 닉네임,
    "email" : 중복체크가 성공되면 해당 ID의 닉네임으로 지정
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        사용 가능한 닉네임입니다.
        ```
        
        - 409 *CONFLICT*
        
        ```json
        사용중인 닉네임입니다.
        ```
        
- 이메일 코드 확인
    - **API** : `/api/manager-service/register/check/email/{email}/{code}`
    - **Method : GET**
    - **Request**
    
    ```json
    "email" : 유저가 Email 중복 체크때 사용한 이메일,
    "code" : 중복체크를 위한 코드번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        이메일 인증이 완료 되었습니다.
        ```
        
        - 203 *NON_AUTHORITATIVE_INFORMATION*
        
        ```json
        잘못된 인증 코드 입니다.
        ```
        
- 로그인
    - **API** : `/api/manager-service/login`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```json
    {
      "managerEmail" : manager회원가입시 기입한 Email
      "managerPassword" : manager회원가입시 기입한 Password
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        {
          "managerUuid": manager회원가입할때 지정된 Uuid
          "managerName": manager회원가입시 기입한 Name
        }
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```json
        아이디가 틀렸습니다.
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```json
        비밀번호가 틀렸습니다.
        ```
        
- 아이디 찾기
    - **API** : `/api/manager-service/FindManagerId`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```json
    {
      "managerName" : manager회원가입시 기입한 Name
      "managerPhoneNumber" : manager회원가입시 기입한 PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
          "managerEmail" : manager회원가입시 기입한 Email
        ```
        
        - 404 *NOT_FOUND*
        
        ```json
        입력하신 정보가 없습니다.
        ```
        
- 비밀번호 초기화
    - **API** : `/api/manager-service/ManagerPasswordReset`
    - **Method : POST**
    - **Request**
    
    ```json
    {
      "managerEmail" : manager회원가입시 기입한 Email
      "managerName" : manager회원가입시 기입한 Name
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        	"ChgManagerPassword" : 랜덤함수 패스워드
        ```
        
        - 404 NOT_FOUND
        
        ```json
        이메일을 다시 확인해주세요.
        ```
        
        - 404 NOT_FOUND
        
        ```json
        이름을 다시 확인해주세요.
        ```
        
- 마이페이지(비밀번호 재 설정)
    - **API** : `/api/manager-service/myPage/changePW/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```json
        "uuid": manager회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        managerName + 님의 비밀번호가 변경되었습니다.
        ```
        

## 🏢 Facility ( 시설물 )

- 시설물 생성
    
    **API** : `/api/facility/register`
    
    **Method** : POST
    
    **Body :  raw (json)**
    
    **Request Data**
    
    ```json
    {
        "facilityOwner": "Test Owner",
        "facilityName" : "Test Name",
        "facilityAddress" : "Test Address"
    }
    ```
    
    **Response Data**
    
    - **400 BAD REQUEST**
    
    ```json
    매니저의 계정만 시설물을 등록할수 있습니다.
    ```
    
    - **409 CONFLICT**
    
    ```json
    입력하신 주소로 이미 등록된 시설이 있습니다.
    ```
    
    - **200 OK**
    
    ```json
    정상적으로 시설물이 등록되었습니다.
    ```
    
- 시설물 생성 - Manager UUID 검색
    - **API** : `/api/facility/find/manager/{managerName}/{managerPhoneNumber}`
    - **Method** : GET
    - **Request**
    
    ```json
    "managerName" : 매니저 이름
    "managerPhoneNumber" : 매니저 전화번호
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```json
        입력하신 데이터와 일치하는 매니저를 찾을수 없습니다.
        ```
        
        - 200 OK
        
        ```json
        managerUuid
        ```
        
- 시설물 삭제
    - **API** : `/api/facility/delete/{managerUuid}/{facilityNo}`
    - **Method :** GET
    - **Request**
    
    ```json
    "managerUuid" : 매니저 아이디
    "facilityNo" : 시설물 번호
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```json
        시설물의 주인만 시설물을 삭제 하실수 있습니다.
        ```
        
        - 200 OK
        
        ```json
        정상적으로 시설물 삭제가 되었습니다.
        ```
        
- 시설물 가입 - 유저, 매니저
    - **API** : `/api/facility/join/{type}`
    - **Method :** POST
    - **Request**
    
    ```json
    "type" : 유저와 매저 구별을 위함 - **매니저는 mg** / **유저는 us**
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```json
        시설물에 가입 하려는 사용자가 존재 하지 않습니다.
        ```
        
        - 404 NOT FOUND
        
        ```json
        가입 하실려는 시설물은 존재하지 않습니다.
        ```
        
        - 409 CONFLICT
        
        ```json
        이미 가입하신 시설물입니다.
        ```
        
        - 200 OK
        
        ```json
        시설물에 정상적으로 가입되셨습니다.
        ```
        
- 시설물 QR 불러오기
    - **API** : `/api/facility/qr/getUrl`
    - **Method : POST**
    - **Body : raw (json)**
    - **Request**
    
    ```json
    {
        "facilityName" : "대구가톨릭대학교",
        "facilityAddress" : "경상북도 경산시 하양읍 하양로 13-13"
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        시설물에 정상적으로 가입되셨습니다.
        ```
        
- 내가 가입한 시설물 보기
    - **API** : `/api/facility/join/{uuid}/{type}/list`
    - **Method : GET**
    - **Request**
    
    ```json
    "uuid" : 매니저, 유저 UUID
    "type" : 유저와 매저 구별을 위함 - **매니저는 mg** / **유저는 us**
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        {
                "uuid": "",
                "name": "",
                "facilityAddress": "",
                "useFacility": "",
                "facilityName": "",
                "facilityOwner": "",
                "isLiked": 
            }
        ```
        
- 내가 가입한 시설물 삭제
    - **API** : `/api/facility/my/delete/{type}/{uuid}/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```json
    "type" : 유저와 매저 구별을 위함 - **매니저는 mg** / **유저는 us**
    "uuid" : 매니저, 유저 UUID
    "facilityNo" : 시설물 번호
    ```
    
    - **Response**
        - 404 NOT FOUND
        
        ```json
        삭제 하려는 시설물이 없습니다.
        ```
        
        - 200 OK
        
        ```json
        정상적으로 가입한 시설물이 삭제 되었습니다.
        ```
        

## 🗒 Content ( 게시물 )

- 게시물 등록
    - **API** : `/api/facility/content/register`
    - **Method : POST**
    - **Body : form-data**
    - **Request**
    
    ```json
    // content-Type : application/json
    KEY : facilityContentDto
    VALUE : {
    					"contentTitle" : "Title",    
    					"contentText" : "Text",
    					"contentLook" : 0,    
    					"contentType" : 0, // 0 =  user, 1 = manager   
    					"facilityNo" : " ",
    					"userUuid" : " "
    				}
    
    KEY : images
    VALUES : images.jpg
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        게시물이 정상적으로 등록되었습니다.
        ```
        
- 게시물 상세 조회
    - **API** : `/api/facility/content/{userUuid}/{contentId}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userUuid" : 사용자 Uuid or 매니저 Uuid
    "contentId" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        {
            "contentNum": 0,
            "contentTitle": "Test Title",
            "contentText": "Test Text",
            "contentImg": "/home/img/test.jpg",
            "contentDate": "2022-09-05T10:17:54",
            "contentLook": "0",
            "writerUuid": " ",
            "writerNickname": "writerNickName",
            "writerImage": " ",
            "userLikeBool": 1
        }
        ```
        
- 게시물 좋아요
    - **API** : `/api/facility/content/liked`
    - **Method : POST**
    - **Request**
    
    ```json
    {
    	"userUuid" : " ",
    	"contentNum" : " "
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        user_like_content에 해당 데이터가 존재 하지 않을 때
        "해당 게시물을 좋아요 처리가 되었습니다." 
        user_like_content에 해당 데이터가 존재 할 때
        ```
        
- 게시물 좋아요 개수 불러오기
    - **API** : `/api/facility/content/liked/{contentNum}`
    - **Method : POST**
    - **Request**
    
    ```json
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        0
        ```
        
- 게시물 삭제
    - **API** : `/api/facility/content/delete/{uuid}/{contentNum}`
    - **Method : GET**
    - **Request**
    
    ```json
    // Body : raw (json)
    
    "uuid" : 유저 UUID,
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```json
        게시물 작성자만 삭제 하실수 있습니다.
        ```
        
        - 200 OK
        
        ```json
        게시물을 정상적으로 삭제 하였습니다.
        ```
        
- 게시물 삭제 - 매니저용
    - **API** : `/api/facility/content/delete/mg/{facilityNo}/{contentId}`
    - **Method : GET**
    - **Request**
    
    ```json
    // Body : raw (json)
    
    "facilityNo" : 유저 UUID,
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        게시물을 정상적으로 삭제 하였습니다.
        ```
        
- 게시물 리스트 불러오기 - 배너용
    - **API** : `/api/facility/{facilityNo}/content/{type}/main`
    - **Method : GET**
    - **Request**
    
    ```json
    "facilityNo" : 시설물 번호,
    "type" : 게시물 타입 - 0 = 일반 게시물, 1 = 공지 사항
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            }
        ]
        ```
        
- 게시물 리스트 불러오기
    - **API** : `/api/facility/content/{facility}/{type}/{position}/list`
    - **Method : GET**
    - **Request**
    
    ```json
    "facilityNo" : 시설물 번호,
    "type" : 게시물 타입 - 0 = 일반 게시물, 1 = 공지 사항
    "position" : 페이지 넘버
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentImg": TestImg.jpg,
                "contentDate": "2022-09-08T10:37:26",
                "name": "Tester"
            }
        ]
        ```
        
- 내가 쓴 게시물 리스트 불러오기 - 배너용
    - **API** : `/api/facility/content/main/user/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userUuid" : 사용자 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            }
        ]
        ```
        
- 내가 쓴 게시물 리스트 불러오기
    - **API** : `/api/facility/my/content/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userUuid" : 사용자 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            },
        		{
                "contentNum": 0,
                "contentTitle": "TestTitle",
                "contentText": "TestText",
                "contentDate": "2022-09-07T17:33:14"
            }
        ]
        ```
        
- 게시물 댓글 등록
    - **API** : `/api/facility/content/comment`
    - **Method : Post**
    - **Body : raw( json )**
    - **Request**
    
    ```json
    {
        "commentText" : "CommentTest",
        "contentNum" : "0",
        "userUuid":"00000-000000-00000",
        "facilityUuid":"0000-aaaaaa-11111-bbb-cc"
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        게시물에 정상적으로 댓글이 등록 되었습니다.
        ```
        
- 게시물 댓글 삭제
    - **API** : `/api/facility/content/comment/delete/{commentNum}/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "contentNum" : 게시물 번호,
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        정상적으로 댓글이 삭제 되었습니다.
        ```
        
- 게시물 댓글 조회
    - **API** : `/api/facility/content/comment/{contentNum}`
    - **Method : GET**
    - **Request**
    
    ```json
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        	{
        		"commentNum": 0,
        		"commentText": "commentTest",
        		"commentDate": "2022-09-07T17:33:14",
            "userUuid": "00000-00000-00000-11-cc",
            "userNickName": "Tester1"
        	},
          {
            "commentNum": 0,
            "commentText": "commentTest",
            "commentDate": "2022-09-07T17:33:14",
            "userUuid": "00000-11111-33333-cc-22",
            "userNickName": "Tester2"
          }
        ```
        

## 📢 Report

- 신고 하기
    - **API** : `/api/report/register`
    - **Method : POST**
    - **Body : form-data**
    - **Request**
    
    ```json
    // content-type : appliciation/json
    KEY : reportDto 
    VALUE : {
    			    "reportTitle" : "신고중입니다.",
    			    "reportText" :"손2들3어 꼼짝마",
    			    "reportType" : "범죄",
    			    "userUuid" : "0f797583-f9dd-4ec3-bb59-39d4cf862ed1",
    			    "facilityNo" :"247f9839-53a4-426c-994d-878f1c05d47b"
    				}
    
    KEY : files
    VALUE : aaaa.jpg
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        신고가 정상적으로 접수 되었습니다.
        ```
        
- 신고 상세 조회
    - **API** : `/api/report/{reportNum}`
    - **Method : GET**
    - **Request**
    
    ```json
    "reportNum" : 신고 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        {
            "reportNum": 0,
            "reportTitle": "ReportTitleTest",
            "reportText": "ReportTextTest",
            "reportType": "누수, 화제, etc...",
            "reportDate": "2022-09-07T17:33:14",
            "reportImg": "report1.jpg report2.jpg report3.jpg",
            "reportStatus": "미해결, 해결, 반려",
            "userUuid": "00000-000000-00000-11-cc",
            "facilityNo": "111-cccc-vvv-222-aaaaa"
        }
        ```
        
- 내가 신고한 리스트 불러오기 - 베너용
    - **API** : `/api/report/list/main/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- 내가 신고한 리스트 불러오기
    - **API** : `/api/report/list/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- 신고 리스트 불러오기 - 처리 현황 별 리스트
    - **API** : `/api/report/list/{facilityNo}/{status}`
    - **Method : GET**
    - **Request**
    
    ```json
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- 신고 리스트 불러오기 - 매니저 배너용 5개 ( 수정 요망 )
    - **API** : `/api/report/list/mg/lt/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```json
    "facilityNo" : 시설물 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "누수, 화제, etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "미해결, 해결, 반려",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- 신고 처리 하기
    - **API** : `/api/report/{reportNum}/{status}`
    - **Method : GET**
    - **Request**
    
    ```json
    "reportNum" : 신고 번호
    "status" : 0 = 미처리, 1 = 처리, 2 = 반려
    ```
    
    - **Response**
        - 200 OK
        
        ```json
        status = 0 일때
        "미처리 되었습니다"
        status = 1 일때
        "처리 되었습니다."
        status = 2 일때
        "반려 되었습니다."
        ```