## ๐ย Content ( ๊ฒ์๋ฌผ )

- ๊ฒ์๋ฌผ ๋ฑ๋ก
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
        ๊ฒ์๋ฌผ์ด ์ ์์ ์ผ๋ก ๋ฑ๋ก๋์์ต๋๋ค.
        ```
        
- ๊ฒ์๋ฌผ ์์ธ ์กฐํ
    - **API** : `/api/facility/content/{userUuid}/{contentId}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ฌ์ฉ์ Uuid or ๋งค๋์  Uuid
    "contentId" : ๊ฒ์๋ฌผ ๋ฒํธ
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
        
- ๊ฒ์๋ฌผ ์ข์์
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
        user_like_content์ ํด๋น ๋ฐ์ดํฐ๊ฐ ์กด์ฌ ํ์ง ์์ ๋
        "ํด๋น ๊ฒ์๋ฌผ์ ์ข์์ ์ฒ๋ฆฌ๊ฐ ๋์์ต๋๋ค." 
        user_like_content์ ํด๋น ๋ฐ์ดํฐ๊ฐ ์กด์ฌ ํ  ๋
        ```
        
- ๊ฒ์๋ฌผ ์ข์์ ๊ฐ์ ๋ถ๋ฌ์ค๊ธฐ
    - **API** : `/api/facility/content/liked/{contentNum}`
    - **Method : POST**
    - **Request**
    
    ```jsonc
    "contentNum" : ๊ฒ์๋ฌผ ๋ฒํธ
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        0
        ```
        
- ๊ฒ์๋ฌผ ์ญ์ 
    - **API** : `/api/facility/content/delete/{uuid}/{contentNum}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    // Body : raw (json)
    
    "uuid" : ์ ์  UUID,
    "contentNum" : ๊ฒ์๋ฌผ ๋ฒํธ
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        ๊ฒ์๋ฌผ ์์ฑ์๋ง ์ญ์  ํ์ค์ ์์ต๋๋ค.
        ```
        
        - 200 OK
        
        ```jsonc
        ๊ฒ์๋ฌผ์ ์ ์์ ์ผ๋ก ์ญ์  ํ์์ต๋๋ค.
        ```
        
- ๊ฒ์๋ฌผ ์ญ์  - ๋งค๋์ ์ฉ
    - **API** : `/api/facility/content/delete/mg/{facilityNo}/{contentId}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    // Body : raw (json)
    
    "facilityNo" : ์ ์  UUID,
    "contentNum" : ๊ฒ์๋ฌผ ๋ฒํธ
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ๊ฒ์๋ฌผ์ ์ ์์ ์ผ๋ก ์ญ์  ํ์์ต๋๋ค.
        ```
        
- ๊ฒ์๋ฌผ ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ - ๋ฐฐ๋์ฉ
    - **API** : `/api/facility/{facilityNo}/content/{type}/main`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "facilityNo" : ์์ค๋ฌผ ๋ฒํธ,
    "type" : ๊ฒ์๋ฌผ ํ์ - 0 = ์ผ๋ฐ ๊ฒ์๋ฌผ, 1 = ๊ณต์ง ์ฌํญ
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
        
- ๊ฒ์๋ฌผ ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ
    - **API** : `/api/facility/content/{facility}/{type}/{position}/list`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "facilityNo" : ์์ค๋ฌผ ๋ฒํธ,
    "type" : ๊ฒ์๋ฌผ ํ์ - 0 = ์ผ๋ฐ ๊ฒ์๋ฌผ, 1 = ๊ณต์ง ์ฌํญ
    "position" : ํ์ด์ง ๋๋ฒ
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
        
- ๋ด๊ฐ ์ด ๊ฒ์๋ฌผ ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ - ๋ฐฐ๋์ฉ
    - **API** : `/api/facility/content/main/user/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ฌ์ฉ์ ๋ฒํธ
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
        
- ๋ด๊ฐ ์ด ๊ฒ์๋ฌผ ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ
    - **API** : `/api/facility/my/content/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ฌ์ฉ์ ๋ฒํธ
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
        
- ๊ฒ์๋ฌผ ๋๊ธ ๋ฑ๋ก
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
        ๊ฒ์๋ฌผ์ ์ ์์ ์ผ๋ก ๋๊ธ์ด ๋ฑ๋ก ๋์์ต๋๋ค.
        ```
        
- ๊ฒ์๋ฌผ ๋๊ธ ์ญ์ 
    - **API** : `/api/facility/content/comment/delete/{commentNum}/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "contentNum" : ๊ฒ์๋ฌผ ๋ฒํธ,
    "userUuid" : ์ ์  Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ์ ์์ ์ผ๋ก ๋๊ธ์ด ์ญ์  ๋์์ต๋๋ค.
        ```
        
- ๊ฒ์๋ฌผ ๋๊ธ ์กฐํ
    - **API** : `/api/facility/content/comment/{contentNum}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "contentNum" : ๊ฒ์๋ฌผ ๋ฒํธ
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