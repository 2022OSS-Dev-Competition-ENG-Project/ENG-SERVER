## ๐จ๐ปโ๐ปย Manager ( ๋งค๋์  )

- ํ์๊ฐ์
    - **API** : `/api/manager-service/signup`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
    	"managerEmail" : manager์ ID์ ํด๋น
    	"managerPassword" : manager์ Password
    	"managerName" : manager์ Name
    	"managerNickname" : manager์ NickName
    	"managerPhoneNum" : manager์ PhoneNumber
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
        
        - 400 *BAD_REQUEST*
        
        ```jsonc
        ResponsePhoneNum + ๋ ์ด๋ฏธ ํ์๊ฐ์๋ ์ ํ๋ฒํธ์๋๋ค.
        ```
        
- ์ด๋ฉ์ผ ์ค๋ณต ์ฒดํฌ ๋ฐ ์ฝ๋ ๋ฐ์ก
    - **API** : `/api/manager-service/register/check/email/{managerEmail}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "managerEmail" : Email์ ํตํ์ฌ ์ค๋ณต ์ฒดํฌ
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
    - **API** : `/api/manager-service/register/check/nickname/{nickname}/{email}`
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
    - **API** : `/api/manager-service/register/check/email/{email}/{code}`
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
    - **API** : `/api/manager-service/login`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
      "managerEmail" : managerํ์๊ฐ์์ ๊ธฐ์ํ Email
      "managerPassword" : managerํ์๊ฐ์์ ๊ธฐ์ํ Password
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        {
          "managerUuid": managerํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
          "managerName": managerํ์๊ฐ์์ ๊ธฐ์ํ Name
        }
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
    - **API** : `/api/manager-service/FindManagerId`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
      "managerName" : managerํ์๊ฐ์์ ๊ธฐ์ํ Name
      "managerPhoneNumber" : managerํ์๊ฐ์์ ๊ธฐ์ํ PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
          "managerEmail" : managerํ์๊ฐ์์ ๊ธฐ์ํ Email
        ```
        
        - 404 *NOT_FOUND*
        
        ```jsonc
        ์๋ ฅํ์  ์ ๋ณด๊ฐ ์์ต๋๋ค.
        ```
        
- ๋น๋ฐ๋ฒํธ ์ด๊ธฐํ
    - **API** : `/api/manager-service/ManagerPasswordReset`
    - **Method : POST**
    - **Request**
    
    ```jsonc
    {
      "managerEmail" : managerํ์๊ฐ์์ ๊ธฐ์ํ Email
      "managerName" : managerํ์๊ฐ์์ ๊ธฐ์ํ Name
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
        
- ๋ง์ดํ์ด์ง(๋น๋ฐ๋ฒํธ ์ฌ ์ค์ )
    - **API** : `/api/manager-service/myPage/changePW/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
        "uuid": managerํ์๊ฐ์ํ ๋ ์ง์ ๋ Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        managerName + ๋์ ๋น๋ฐ๋ฒํธ๊ฐ ๋ณ๊ฒฝ๋์์ต๋๋ค.
        ```
        

