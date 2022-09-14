## 🗒 Content ( 게시물 )

- 게시물 등록
    - **API** : `/api/facility/content/register`
    - **Method : POST**
    - **Body : form-data**
    - **Request**
    
    ```jsonc
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
        
        ```jsonc
        게시물이 정상적으로 등록되었습니다.
        ```
        
- 게시물 상세 조회
    - **API** : `/api/facility/content/{userUuid}/{contentId}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : 사용자 Uuid or 매니저 Uuid
    "contentId" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    {
    	"userUuid" : " ",
    	"contentNum" : " "
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        user_like_content에 해당 데이터가 존재 하지 않을 때
        "해당 게시물을 좋아요 처리가 되었습니다." 
        user_like_content에 해당 데이터가 존재 할 때
        ```
        
- 게시물 좋아요 개수 불러오기
    - **API** : `/api/facility/content/liked/{contentNum}`
    - **Method : POST**
    - **Request**
    
    ```jsonc
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        0
        ```
        
- 게시물 삭제
    - **API** : `/api/facility/content/delete/{uuid}/{contentNum}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    // Body : raw (json)
    
    "uuid" : 유저 UUID,
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        게시물 작성자만 삭제 하실수 있습니다.
        ```
        
        - 200 OK
        
        ```jsonc
        게시물을 정상적으로 삭제 하였습니다.
        ```
        
- 게시물 삭제 - 매니저용
    - **API** : `/api/facility/content/delete/mg/{facilityNo}/{contentId}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    // Body : raw (json)
    
    "facilityNo" : 유저 UUID,
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        게시물을 정상적으로 삭제 하였습니다.
        ```
        
- 게시물 리스트 불러오기 - 배너용
    - **API** : `/api/facility/{facilityNo}/content/{type}/main`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "facilityNo" : 시설물 번호,
    "type" : 게시물 타입 - 0 = 일반 게시물, 1 = 공지 사항
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "facilityNo" : 시설물 번호,
    "type" : 게시물 타입 - 0 = 일반 게시물, 1 = 공지 사항
    "position" : 페이지 넘버
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "userUuid" : 사용자 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "userUuid" : 사용자 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    {
        "commentText" : "CommentTest",
        "contentNum" : "0",
        "userUuid":"00000-000000-00000",
        "facilityUuid":"0000-aaaaaa-11111-bbb-cc"
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        게시물에 정상적으로 댓글이 등록 되었습니다.
        ```
        
- 게시물 댓글 삭제
    - **API** : `/api/facility/content/comment/delete/{commentNum}/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "contentNum" : 게시물 번호,
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        정상적으로 댓글이 삭제 되었습니다.
        ```
        
- 게시물 댓글 조회
    - **API** : `/api/facility/content/comment/{contentNum}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "contentNum" : 게시물 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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