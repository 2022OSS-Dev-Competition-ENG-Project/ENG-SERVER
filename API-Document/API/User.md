## 👨🏻‍💼 User ( 사용자 )

- 내가 가입한 시설물 좋아요 누르기
    - **API** : `/api/facility/like/{userUuid}/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : 사용자 UUID
    "facilityNo" : 시설물 고유 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
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
        
        ```jsonc
        회원가입 완료
        ```
        
        - 401 *UNAUTHORIZED*
        
        ```jsonc
        회원가입 시도중 오류가 발생했습니다. 이메일 인증과 닉네임 중복체크를 확인해주세요.
        ```
        
- 이메일 중복 체크 및 코드 발송
    - **API** : `/api/user-service/register/check/email/{userEmail}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userEmail" : Email을 통하여 중복 체크
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
    - **API** : `/api/user-service/register/check/nickname/{nickname}/{email}`
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
    - **API** : `/api/user-service/register/check/email/{email}/{code}`
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
    - **API** : `/api/user-service/login`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {
        "userEmail" : user회원가입시 기입한 Email
        "userPassword" : user회원가입시 기입한 Password
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
            "userUuid": user회원가입할때 지정된 Uuid
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
    - **API** : `/api/user-service/FindUserId`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {    
    		"userName" : user회원가입시 기입한 Name
        "userPhoneNumber" : user회원가입시 기입한 PhoneNumber
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
          "userEmail" : user회원가입시 기입한 Email
        ```
        
        - 404 *NOT_FOUND*
        
        ```jsonc
        입력하신 정보가 없습니다.
        ```
        
- 비밀번호 초기화
    - **API** : `/api/user-service/UserPasswordReset`
    - **Method : POST**
    - **Body :  raw (json)**
    - **Request**
    
    ```jsonc
    {    
    		"userEmail" : user회원가입시 기입한 Email
        "userName" : user회원가입시 기입한 Name
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
        
- 마이페이지( 메인 )
    - **API** : `/api/user-service/myPage/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "uuid" : user회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        {
            "userEmail": user회원가입시 기입한 Email
            "userNickname": user회원가입시 기입한 Nickname
            "userJoinDate": user회원가입시 기입한 JoinDate
            "userImg": user회원가입시 기입한 ProfileImage
        }
        ```
        
- 마이페이지( 비밀번호 재 설정 )
    - **API** : `/api/user-service/myPage/changePW/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
        "uuid": user회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        userName + 님의 비밀번호가 변경되었습니다.
        ```
        
- 프로필 이미지 저장
    - **API** : `/api/user-service/SaveProfileImage/{uuid}`
    - **Method : GET**
    - **Body :  form-data**
    - **Request**
    
    ```jsonc
    "uuid": user회원가입할때 지정된 Uuid
    
    KEY : images
    VALUES : images.jpg
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        이미지 저장을 성공했습니다.
        ```
        
- 프로필 이미지 가져오기
    - **API** : `/api/user-service/ProfileImage/{uuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "uuid": user회원가입할때 지정된 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        fileArray // userImage 출력
        ```