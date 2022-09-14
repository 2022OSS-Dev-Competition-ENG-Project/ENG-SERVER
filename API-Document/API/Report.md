## 📢 Report

- 신고 하기
    - **API** : `/api/report/register`
    - **Method : POST**
    - **Body : form-data**
    - **Request**
    
    ```jsonc
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
        
        ```jsonc
        신고가 정상적으로 접수 되었습니다.
        ```
        
- 신고 상세 조회
    - **API** : `/api/report/{reportNum}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "reportNum" : 신고 번호
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "userUuid" : 유저 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "facilityNo" : 시설물 Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
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
    
    ```jsonc
    "reportNum" : 신고 번호
    "status" : 0 = 미처리, 1 = 처리, 2 = 반려
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        status = 0 일때
        "미처리 되었습니다"
        status = 1 일때
        "처리 되었습니다."
        status = 2 일때
        "반려 되었습니다."
        ```