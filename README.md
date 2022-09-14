</br>

# 🪧 소개

>### IOS APP 과의 관계
>> - [IOS APP](https://github.com/2022OSS-Dev-Competition-ENG-Project/ENG-iOS-APP)을 통해서 사용자가 속해 있는 시설물에 파손이 되어 있거나 불편한점 또는 위험해 보이는 점들을 매니저 또는 관리자에게 신고를 할수 있고, 시설물에 속해있는 사용자들에게 정보를 게시판을 통해 공유할수 있습니다.

>### WEB 과의 관계
>> - [웹](https://github.com/2022OSS-Dev-Competition-ENG-Project/ENG_Web)을 통해서 매니저가 관리하는 시설물들의 정보를 확인하고 신고가 들어 왔을 사용자들의 불편한 점이나 위험해 보이는 것들을 해결해줄수 있습니다.

</br></br>

# 🏆 프로젝트 컨벤션
- 함수 네이밍을 보고 누구나 이 함수가 무엇을 의미하는지 알수 있도록 하는 것이 목표입니다.
- 중복된 코드를 최소화 하고 불필요한 코드들을 없애는 것이 목표 입니다.
- 문서화를 통해 Setting이나 Return 되는 값들을 누구나 손쉽게 수정할수 있도록 하는 것이 목표 입니다.
- 프로젝트 개발의 편의성을 위해 자동화 구축하는것이 목표 입니다.

</br></br>
# 🎯 코딩 컨벤션
1. 문서화
2. 중복된 코드 간소화
3. 누구나 알아 볼수 있도록 하는 함수 네이밍
4. DB와 네트워크 통신 최소화
5. 모든 함수 또는 API에 주석 작성

</br></br>
# 🙇🏻 개발
>### Develop Enviroment
- Language  : JAVA
- Framework : Spring Boot
- DBMS      : MariaDB
- TestTool  : PostMan 
- Build Version : Java 11
- Devleop Tool : Intelli J
- JDK : open-jdk:11

>### Dependencies
>> 아래 Dependencies들은 기본 셋팅 이외에 추가로 설치 한 것들입니다.
>>
>> user-service
``` java
    //mybatis
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2'
    implementation 'mysql:mysql-connector-java:8.0.28'

    //mariaDB JDBC
    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client:2.7.4'

    //Security
    implementation 'org.springframework.boot:spring-boot-starter-security'
    testImplementation 'org.springframework.security:spring-security-test'

    //email(SMTP)
    implementation 'org.springframework.boot:spring-boot-starter-mail'

    //Token
    implementation 'javax.xml.bind:jaxb-api'
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
    implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    //Redis
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'

    //Apache Commons IO
    implementation group: 'commons-io', name: 'commons-io', version: '2.11.0'
```
>> manager-service
```java
    // QR Code
    implementation 'com.google.zxing:core:3.5.0'
    implementation group: 'com.solvoj.zxing-java6', name: 'javase-java6', version: '3.2.0'
```

</br></br>
>#### __step 1__
>> __툴 설치__
>
>>프로젝트를 시작하기 앞서 개발하기전 위 개발 환경과 동일하게 해주시기 바랍니다.

- 우선 자신이 개발을 할수 있는 툴이 있다면 이 단계는 넘어 가도 좋습니다. 만약 자신이 개발을 하는 툴이 없다면 이 [링크](https://www.jetbrains.com/ko-kr/idea/download/)에 들어가 다운받아 주세요.
- Intelli J 설치가 다 되었다면 이제 아래 터미널을 열고 소스 파일을 다운받아 열어 주세요.

>#### __Step 2__
>> __소스 파일 설치__

- 아래의 명령어를 통해 자신이 원하는 디렉토리 안에 소스 파일을 다운 받으실수 있습니다.
```shell
$ git clone https://github.com/2022OSS-Dev-Competition-ENG-Project/ENG-SERVER.git
```

>#### __Step 3__
>>Docker 설치
- MariaDB를 설치 하기 이전에 자신의 컴퓨터의 자원 관리를 위해 Docker Desktop을 먼저 설치 해줍니다.
    - [Dokcer Desktop - mac](https://docs.docker.com/desktop/install/mac-install/)
    - [Docker Desktop - windows](https://docs.docker.com/desktop/install/windows-install/)

- 위에 Dcoker Desktop이 설치가 다 된되면 아래 명령어를 입력을 해 도커를 실행 시키고 Docker-Compose를 설치해주세요.

```shell
# Docker 실행
$ docker run -d -p 80:80 docker/getting-started

# 도커 권환 설정후 재 실행
$ sudo usermod -aG docker ${USER}
$ sudo service docker restart


# Dcoker-Compose 설치
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
$ sudo chmod +x /usr/local/bin/docker-compose
$ sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
$ docker-compose -version 

# Docker-Compose 버전 확인
$ docker-compose -version 
```
- Docker가 정상적으로 실행되고 docker-compose의 설치 확인이 완료 되면 Docker Install은 끝입니다.

>#### __Step 4__
>> MariaDB 설치
```shell
$ docker pull mariadb
```

 
