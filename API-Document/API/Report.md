## ๐ขย Report

- ์ ๊ณ  ํ๊ธฐ
    - **API** : `/api/report/register`
    - **Method : POST**
    - **Body : form-data**
    - **Request**
    
    ```jsonc
    // content-type : appliciation/json
    KEY : reportDto 
    VALUE : {
    			    "reportTitle" : "์ ๊ณ ์ค์๋๋ค.",
    			    "reportText" :"์2๋ค3์ด ๊ผผ์ง๋ง",
    			    "reportType" : "๋ฒ์ฃ",
    			    "userUuid" : "0f797583-f9dd-4ec3-bb59-39d4cf862ed1",
    			    "facilityNo" :"247f9839-53a4-426c-994d-878f1c05d47b"
    				}
    
    KEY : files
    VALUE : aaaa.jpg
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        ์ ๊ณ ๊ฐ ์ ์์ ์ผ๋ก ์ ์ ๋์์ต๋๋ค.
        ```
        
- ์ ๊ณ  ์์ธ ์กฐํ
    - **API** : `/api/report/{reportNum}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "reportNum" : ์ ๊ณ  ๋ฒํธ
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        {
            "reportNum": 0,
            "reportTitle": "ReportTitleTest",
            "reportText": "ReportTextTest",
            "reportType": "๋์, ํ์ , etc...",
            "reportDate": "2022-09-07T17:33:14",
            "reportImg": "report1.jpg report2.jpg report3.jpg",
            "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
            "userUuid": "00000-000000-00000-11-cc",
            "facilityNo": "111-cccc-vvv-222-aaaaa"
        }
        ```
        
- ๋ด๊ฐ ์ ๊ณ ํ ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ - ๋ฒ ๋์ฉ
    - **API** : `/api/report/list/main/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ ์  Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- ๋ด๊ฐ ์ ๊ณ ํ ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ
    - **API** : `/api/report/list/{userUuid}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ ์  Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- ์ ๊ณ  ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ - ์ฒ๋ฆฌ ํํฉ ๋ณ ๋ฆฌ์คํธ
    - **API** : `/api/report/list/{facilityNo}/{status}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "userUuid" : ์ ์  Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- ์ ๊ณ  ๋ฆฌ์คํธ ๋ถ๋ฌ์ค๊ธฐ - ๋งค๋์  ๋ฐฐ๋์ฉ 5๊ฐ ( ์์  ์๋ง )
    - **API** : `/api/report/list/mg/lt/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "facilityNo" : ์์ค๋ฌผ Uuid
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        [
            {
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            },
        		{
                "reportNum": 0,
                "reportTitle": "ReportTitleTest",
                "reportText": "ReportTextTest",
                "reportType": "๋์, ํ์ , etc...",
                "reportDate": "2022-09-07T17:33:14",
                "reportImg": "report1.jpg report2.jpg report3.jpg",
                "reportStatus": "๋ฏธํด๊ฒฐ, ํด๊ฒฐ, ๋ฐ๋ ค",
                "userUuid": "00000-000000-00000-11-cc",
                "userNickname": "Tester",
                "facilityNo": "111-cccc-vvv-222-aaaaa"
            }
        ]
        ```
        
    
- ์ ๊ณ  ์ฒ๋ฆฌ ํ๊ธฐ
    - **API** : `/api/report/{reportNum}/{status}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "reportNum" : ์ ๊ณ  ๋ฒํธ
    "status" : 0 = ๋ฏธ์ฒ๋ฆฌ, 1 = ์ฒ๋ฆฌ, 2 = ๋ฐ๋ ค
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        status = 0 ์ผ๋
        "๋ฏธ์ฒ๋ฆฌ ๋์์ต๋๋ค"
        status = 1 ์ผ๋
        "์ฒ๋ฆฌ ๋์์ต๋๋ค."
        status = 2 ์ผ๋
        "๋ฐ๋ ค ๋์์ต๋๋ค."
        ```