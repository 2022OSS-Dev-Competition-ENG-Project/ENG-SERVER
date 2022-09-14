## 👨🏻‍💻 Manager ( 매니저 )

- 회원가입
    - **API** : `/api/manager-service/signup`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
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
        
        ```jsonc
        회원가입 완료
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        회원가입 시도중 오류가 발생했습니다. 이메일 인증과 닉네임 중복체크를 확인해주세요.
        ```
        
        - 400 *BAD_REQUEST*
        
        ```jsonc
        ResponsePhoneNum + 는 이미 회원가입된 전화번호입니다.
        ```
        
- 이메일 중복 체크 및 코드 발송
    - **API** : `/api/manager-service/register/check/email/{managerEmail}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "managerEmail" : Email을 통하여 중복 체크
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        사용 가능한 이메일입니다.\n 인증코드가 발송 되었습니다.
        ```
        
        - 409 *CONFLICT*
        
        ```jsonc
        사용중인 이메일입니다.
        ```
        
    
- 닉네임 중복 체크
    - **API** : `/api/manager-service/register/check/nickname/{nickname}/{email}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "nickname" : 중복체크할 닉네임,
    "email" : 중복체크가 성공되면 해당 ID의 닉네임으로 지정
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        사용 가능한 닉네임입니다.
        ```
        
        - 409 *CONFLICT*
        
        ```jsonc
        사용중인 닉네임입니다.
        ```
        
- 이메일 코드 확인
    - **API** : `/api/manager-service/register/check/email/{email}/{code}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "email" : 유저가 Email 중복 체크때 사용한 이메일,
    "code" : 중복체크를 위한 코드번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        이메일 인증이 완료 되었습니다.
        ```
        
        - 203 *NON_AUTHORITATIVE_INFORMATION*
        
        ```jsonc
        잘못된 인증 코드 입니다.
        ```
        
- 로그인
    - **API** : `/api/manager-service/login`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
      "managerEmail" : manager회원가입시 기입한 Email
      "managerPassword" : manager회원가입시 기입한 Password
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        {
          "managerUuid": manager회원가입할때 지정된 Uuid
          "managerName": manager회원가입시 기입한 Name
        }
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        아이디가 틀렸습니다.
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        비밀번호가 틀렸습니다.
        ```
        
- 아이디 찾기
    - **API** : `/api/manager-service/FindManagerId`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
      "managerName" : manager회원가입시 기입한 Name
      "managerPhoneNumber" : manager회원가입시 기입한 PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
          "managerEmail" : manager회원가입시 기입한 Email
        ```
        
        - 404 *NOT_FOUND*
        
        ```jsonc
        입력하신 정보가 없습니다.
        ```
        
- 비밀번호 초기화
    - **API** : `/api/manager-service/ManagerPasswordReset`
    - **Method : POST**
    - **Request**
    
    ```jsonc
    {
      "managerEmail" : manager회원가입시 기입한 Email
      "managerName" : manager회원가입시 기입한 Name
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        	"ChgManagerPassword" : 랜덤함수 패스워드
        ```
        
        - 404 NOT_FOUND
        
        ```jsonc
        이메일을 다시 확인해주세요.
        ```
        
        - 404 NOT_FOUND
        
        ```jsonc
        이름을 다시 확인해주세요.
        ```
        
- 마이페이지(비밀번호 재 설정)
    - **API** : `/api/manager-service/myPage/changePW/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
        "uuid": manager회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        managerName + 님의 비밀번호가 변경되었습니다.
        ```
        

