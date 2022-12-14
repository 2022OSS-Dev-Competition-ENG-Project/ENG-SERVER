## ๐จ๐ปโ๐ผย User ( ์ฌ์ฉ์ )

- ๋ด๊ฐ ๊ฐ์ํ ์์ค๋ฌผ ์ข์์ ๋๋ฅด๊ธฐ
    - **API** : `/api/facility/like/{userUuid}/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ฌ์ฉ์ UUID
    "facilityNo" : ์์ค๋ฌผ ๊ณ ์  ๋ฒํธ
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        is_like = 0 ์ผ๋
        "์ข์์ ์ฒ๋ฆฌ๊ฐ ์๋ฃ๋์์ต๋๋ค."
        is_like = 1 ์ผ๋
        "์ข์์ ์ทจ์ ์ฒ๋ฆฌ๊ฐ ์๋ฃ๋์์ต๋๋ค."
        ```
        
- ํ์๊ฐ์
    - **API** : `/api/user-service/signup`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
    	"userEmail" : user์ ID์ ํด๋น
    	"userPassword" : user์ Password
    	"userName" : user์ Name
    	"userNickname" : user์ NickName
    	"userPhoneNum" : user์ PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ํ์๊ฐ์ ์๋ฃ
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        ํ์๊ฐ์ ์๋์ค ์ค๋ฅ๊ฐ ๋ฐ์ํ์ต๋๋ค. ์ด๋ฉ์ผ ์ธ์ฆ๊ณผ ๋๋ค์ ์ค๋ณต์ฒดํฌ๋ฅผ ํ์ธํด์ฃผ์ธ์.
        ```
        
- ์ด๋ฉ์ผ ์ค๋ณต ์ฒดํฌ ๋ฐ ์ฝ๋ ๋ฐ์ก
    - **API** : `/api/user-service/register/check/email/{userEmail}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userEmail" : Email์ ํตํ์ฌ ์ค๋ณต ์ฒดํฌ
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ์ฌ์ฉ ๊ฐ๋ฅํ ์ด๋ฉ์ผ์๋๋ค.\n ์ธ์ฆ์ฝ๋๊ฐ ๋ฐ์ก ๋์์ต๋๋ค.
        ```
        
        - 409 *CONFLICT*
        
        ```jsonc
        ์ฌ์ฉ์ค์ธ ์ด๋ฉ์ผ์๋๋ค.
        ```
        
    
- ๋๋ค์ ์ค๋ณต ์ฒดํฌ
    - **API** : `/api/user-service/register/check/nickname/{nickname}/{email}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "nickname" : ์ค๋ณต์ฒดํฌํ  ๋๋ค์,
    "email" : ์ค๋ณต์ฒดํฌ๊ฐ ์ฑ๊ณต๋๋ฉด ํด๋น ID์ ๋๋ค์์ผ๋ก ์ง์ 
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ์ฌ์ฉ ๊ฐ๋ฅํ ๋๋ค์์๋๋ค.
        ```
        
        - 409 *CONFLICT*
        
        ```jsonc
        ์ฌ์ฉ์ค์ธ ๋๋ค์์๋๋ค.
        ```
        
- ์ด๋ฉ์ผ ์ฝ๋ ํ์ธ
    - **API** : `/api/user-service/register/check/email/{email}/{code}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "email" : ์ ์ ๊ฐ Email ์ค๋ณต ์ฒดํฌ๋ ์ฌ์ฉํ ์ด๋ฉ์ผ,
    "code" : ์ค๋ณต์ฒดํฌ๋ฅผ ์ํ ์ฝ๋๋ฒํธ
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ์ด๋ฉ์ผ ์ธ์ฆ์ด ์๋ฃ ๋์์ต๋๋ค.
        ```
        
        - 203 *NON_AUTHORITATIVE_INFORMATION*
        
        ```jsonc
        ์๋ชป๋ ์ธ์ฆ ์ฝ๋ ์๋๋ค.
        ```
        
- ๋ก๊ทธ์ธ
    - **API** : `/api/user-service/login`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
        "userEmail" : userํ์๊ฐ์์ ๊ธฐ์ํ Email
        "userPassword" : userํ์๊ฐ์์ ๊ธฐ์ํ Password
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
            "userUuid": userํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        ์์ด๋๊ฐ ํ๋ ธ์ต๋๋ค.
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        ๋น๋ฐ๋ฒํธ๊ฐ ํ๋ ธ์ต๋๋ค.
        ```
        
- ์์ด๋ ์ฐพ๊ธฐ
    - **API** : `/api/user-service/FindUserId`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {    
    		"userName" : userํ์๊ฐ์์ ๊ธฐ์ํ Name
        "userPhoneNumber" : userํ์๊ฐ์์ ๊ธฐ์ํ PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
          "userEmail" : userํ์๊ฐ์์ ๊ธฐ์ํ Email
        ```
        
        - 404 *NOT_FOUND*
        
        ```jsonc
        ์๋ ฅํ์  ์ ๋ณด๊ฐ ์์ต๋๋ค.
        ```
        
- ๋น๋ฐ๋ฒํธ ์ด๊ธฐํ
    - **API** : `/api/user-service/UserPasswordReset`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {    
    		"userEmail" : userํ์๊ฐ์์ ๊ธฐ์ํ Email
        "userName" : userํ์๊ฐ์์ ๊ธฐ์ํ Name
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        	"ChgManagerPassword" : ๋๋คํจ์ ํจ์ค์๋
        ```
        
        - 404 NOT_FOUND
        
        ```jsonc
        ์ด๋ฉ์ผ์ ๋ค์ ํ์ธํด์ฃผ์ธ์.
        ```
        
        - 404 NOT_FOUND
        
        ```jsonc
        ์ด๋ฆ์ ๋ค์ ํ์ธํด์ฃผ์ธ์.
        ```
        
- ๋ง์ดํ์ด์ง( ๋ฉ์ธ )
    - **API** : `/api/user-service/myPage/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "uuid" : userํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        {
            "userEmail": userํ์๊ฐ์์ ๊ธฐ์ํ Email
            "userNickname": userํ์๊ฐ์์ ๊ธฐ์ํ Nickname
            "userJoinDate": userํ์๊ฐ์์ ๊ธฐ์ํ JoinDate
            "userImg": userํ์๊ฐ์์ ๊ธฐ์ํ ProfileImage
        }
        ```
        
- ๋ง์ดํ์ด์ง( ๋น๋ฐ๋ฒํธ ์ฌ ์ค์  )
    - **API** : `/api/user-service/myPage/changePW/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
        "uuid": userํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        userName + ๋์ ๋น๋ฐ๋ฒํธ๊ฐ ๋ณ๊ฒฝ๋์์ต๋๋ค.
        ```
        
- ํ๋กํ ์ด๋ฏธ์ง ์ ์ฅ
    - **API** : `/api/user-service/SaveProfileImage/{uuid}`
    - **Method : GET**
    - **Body :  form-data**
    - **Request**
    
    ```jsonc
    "uuid": userํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
    
    KEY : images
    VALUES : images.jpg
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ์ด๋ฏธ์ง ์ ์ฅ์ ์ฑ๊ณตํ์ต๋๋ค.
        ```
        
- ํ๋กํ ์ด๋ฏธ์ง ๊ฐ์ ธ์ค๊ธฐ
    - **API** : `/api/user-service/ProfileImage/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "uuid": userํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        fileArray // userImage ์ถ๋ ฅ
        ```