## π’Β Facility ( μμ€λ¬Ό )

- μμ€λ¬Ό μμ±
    
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
    λ§€λμ μ κ³μ λ§ μμ€λ¬Όμ λ±λ‘ν μ μμ΅λλ€.
    ```
    
    - **409 CONFLICT**
    
    ```jsonc
    μλ ₯νμ  μ£Όμλ‘ μ΄λ―Έ λ±λ‘λ μμ€μ΄ μμ΅λλ€.
    ```
    
    - **200 OK**
    
    ```jsonc
    μ μμ μΌλ‘ μμ€λ¬Όμ΄ λ±λ‘λμμ΅λλ€.
    ```
    
- μμ€λ¬Ό μμ± - Manager UUID κ²μ
    - **API** : `/api/facility/find/manager/{managerName}/{managerPhoneNumber}`
    - **Method** : GET
    - **Request**
    
    ```jsonc
    "managerName" : λ§€λμ  μ΄λ¦
    "managerPhoneNumber" : λ§€λμ  μ νλ²νΈ
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        μλ ₯νμ  λ°μ΄ν°μ μΌμΉνλ λ§€λμ λ₯Ό μ°Ύμμ μμ΅λλ€.
        ```
        
        - 200 OK
        
        ```jsonc
        managerUuid
        ```
        
- μμ€λ¬Ό μ­μ 
    - **API** : `/api/facility/delete/{managerUuid}/{facilityNo}`
    - **Method :** GET
    - **Request**
    
    ```jsonc
    "managerUuid" : λ§€λμ  μμ΄λ
    "facilityNo" : μμ€λ¬Ό λ²νΈ
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        μμ€λ¬Όμ μ£ΌμΈλ§ μμ€λ¬Όμ μ­μ  νμ€μ μμ΅λλ€.
        ```
        
        - 200 OK
        
        ```jsonc
        μ μμ μΌλ‘ μμ€λ¬Ό μ­μ κ° λμμ΅λλ€.
        ```
        
- μμ€λ¬Ό κ°μ - μ μ , λ§€λμ 
    - **API** : `/api/facility/join/{type}`
    - **Method :** POST
    - **Request**
    
    ```jsonc
    "type" : μ μ μ λ§€μ  κ΅¬λ³μ μν¨ - **λ§€λμ λ mg** / **μ μ λ us**
    ```
    
    - **Response**
        - 400 BAD REQUEST
        
        ```jsonc
        μμ€λ¬Όμ κ°μ νλ €λ μ¬μ©μκ° μ‘΄μ¬ νμ§ μμ΅λλ€.
        ```
        
        - 404 NOT FOUND
        
        ```jsonc
        κ°μ νμ€λ €λ μμ€λ¬Όμ μ‘΄μ¬νμ§ μμ΅λλ€.
        ```
        
        - 409 CONFLICT
        
        ```jsonc
        μ΄λ―Έ κ°μνμ  μμ€λ¬Όμλλ€.
        ```
        
        - 200 OK
        
        ```jsonc
        μμ€λ¬Όμ μ μμ μΌλ‘ κ°μλμ¨μ΅λλ€.
        ```
        
- μμ€λ¬Ό QR λΆλ¬μ€κΈ°
    - **API** : `/api/facility/qr/getUrl`
    - **Method : POST**
    - **Body : raw (json)**
    - **Request**
    
    ```jsonc
    {
        "facilityName" : "λκ΅¬κ°ν¨λ¦­λνκ΅",
        "facilityAddress" : "κ²½μλΆλ κ²½μ°μ νμμ νμλ‘ 13-13"
    }
    ```
    
    - **Response**
        - 200 OK
        
        ```jsonc
        μμ€λ¬Όμ μ μμ μΌλ‘ κ°μλμ¨μ΅λλ€.
        ```
        
- λ΄κ° κ°μν μμ€λ¬Ό λ³΄κΈ°
    - **API** : `/api/facility/join/{uuid}/{type}/list`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "uuid" : λ§€λμ , μ μ  UUID
    "type" : μ μ μ λ§€μ  κ΅¬λ³μ μν¨ - **λ§€λμ λ mg** / **μ μ λ us**
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
        
- λ΄κ° κ°μν μμ€λ¬Ό μ­μ 
    - **API** : `/api/facility/my/delete/{type}/{uuid}/{facilityNo}`
    - **Method : GET**
    - **Request**
    
    ```jsonc
    "type" : μ μ μ λ§€μ  κ΅¬λ³μ μν¨ - **λ§€λμ λ mg** / **μ μ λ us**
    "uuid" : λ§€λμ , μ μ  UUID
    "facilityNo" : μμ€λ¬Ό λ²νΈ
    ```
    
    - **Response**
        - 404 NOT FOUND
        
        ```jsonc
        μ­μ  νλ €λ μμ€λ¬Όμ΄ μμ΅λλ€.
        ```
        
        - 200 OK
        
        ```jsonc
        μ μμ μΌλ‘ κ°μν μμ€λ¬Όμ΄ μ­μ  λμμ΅λλ€.
        ```