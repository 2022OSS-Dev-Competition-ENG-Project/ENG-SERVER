## 🏢 Facility ( 시설물 )

- 시설물 생성
    
    **API** : `/api/facility/register`
    
    **Method** : POST
    
    **Body :  raw (json)**
    
    **Request Data**
    
    ```jsonc
    {
        "facilityOwner": "Test Owner",
        "facilityName" : "Test Name",
        "facilityAddress" : "Test Address"
    }
    ```
    
    **Response Data**
    
    - **400 BAD REQUEST**
    
    ```jsonc
    매니저의 계정만 시설물을 등록할수 있습니다.
    ```
    
    - **409 CONFLICT**
    
    ```jsonc
    입력하신 주소로 이미 등록된 시설이 있습니다.
    ```
    
    - **200 OK**
    
    ```jsonc
    정상적으로 시설물이 등록되었습니다.
    ```
    
- 시설물 생성 - Manager UUID 검색
    - **API** : `/api/facility/find/manager/{managerName}/{managerPhoneNumber}`
    - **Method** : GET
    - **Request**
    
    ```jsonc
    "managerName" : 매니저 이름
    "managerPhoneNumber" : 매니저 전화번호
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        입력하신 데이터와 일치하는 매니저를 찾을수 없습니다.
        ```
        
        - 200 OK
        
        ```jsonc
        managerUuid
        ```
        
- 시설물 삭제
    - **API** : `/api/facility/delete/{managerUuid}/{facilityNo}`
    - **Method :** GET
    - **Request**
    
    ```jsonc
    "managerUuid" : 매니저 아이디
    "facilityNo" : 시설물 번호
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        시설물의 주인만 시설물을 삭제 하실수 있습니다.
        ```
        
        - 200 OK
        
        ```jsonc
        정상적으로 시설물 삭제가 되었습니다.
        ```
        
- 시설물 가입 - 유저, 매니저
    - **API** : `/api/facility/join/{type}`
    - **Method :** POST
    - **Request**
    
    ```jsonc
    "type" : 유저와 매저 구별을 위함 - **매니저는 mg** / **유저는 us**
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        시설물에 가입 하려는 사용자가 존재 하지 않습니다.
        ```
        
        - 404 NOT FOUND
        
        ```jsonc
        가입 하실려는 시설물은 존재하지 않습니다.
        ```
        
        - 409 CONFLICT
        
        ```jsonc
        이미 가입하신 시설물입니다.
        ```
        
        - 200 OK
        
        ```jsonc
        시설물에 정상적으로 가입되셨습니다.
        ```
        
- 시설물 QR 불러오기
    - **API** : `/api/facility/qr/getUrl`
    - **Method : POST**
    - **Body : raw (json)**
    - **Request**
    
    ```jsonc
    {
        "facilityName" : "대구가톨릭대학교",
        "facilityAddress" : "경상북도 경산시 하양읍 하양로 13-13"
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        시설물에 정상적으로 가입되셨습니다.
        ```
        
- 내가 가입한 시설물 보기
    - **API** : `/api/facility/join/{uuid}/{type}/list`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "uuid" : 매니저, 유저 UUID
    "type" : 유저와 매저 구별을 위함 - **매니저는 mg** / **유저는 us**
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "type" : 유저와 매저 구별을 위함 - **매니저는 mg** / **유저는 us**
    "uuid" : 매니저, 유저 UUID
    "facilityNo" : 시설물 번호
    ```
    
    - **Response**
        - 404 NOT FOUND
        
        ```jsonc
        삭제 하려는 시설물이 없습니다.
        ```
        
        - 200 OK
        
        ```jsonc
        정상적으로 가입한 시설물이 삭제 되었습니다.
        ```