# Chatn

로그인을 하고 인증된 사용자만 특정 채팅방에 입장하여 채팅이 가능한 서비스입니다.

채팅 서비스를 만들어보고 싶었고, 제가 원하는 기능을 추가해보고자 개인으로 제작하게 되었습니다.

## 01. 프로젝트 아키텍처

### 01) 시스템 구성 # 1

![시스템 구성도](https://user-images.githubusercontent.com/46776745/115690872-72b8c400-a398-11eb-9a82-29f9ba33b430.PNG)

### 02) 시스템 구성 # 2

![시스템 구성도2](https://user-images.githubusercontent.com/46776745/115690930-8532fd80-a398-11eb-86b7-30b91f6a1942.PNG)

 * **OS** : AWS의 Ubuntu 18.04 LTS
 * **language** : openjdk 15
 * **Server Framework** : Spring Boot framework
 * **Socket module** : Socket.io
 * **DataBase** : MySQL, Redis

## 02. 프로젝트 API 문서

### 01) 회원가입 및 로그인 API

| 기능 | 메소드 | 주소 |
| :--- | :---: | ---: |
| 회원가입 렌더링 | GET | /setting/regMember |
| 회원가입 | POST | /setting/regMember |
| 로그인 렌더링| GET | /setting/loginMember |
| 로그인 | POST | /loginProc |

### 02) 채팅방 API

| 기능 | 메소드 | 주소 |
| :--- | :---: | ---: |
| 전체 채팅방 목록 화면 렌더링 | GET | /chat/room |
| 채팅방 입장 화면 렌더링 | GET | /chat/room/detail |
| 특정 채팅방 입장 화면 렌더링 | GET | /chat/room/detail/:roomid |
| 전체 채팅방 목록 호출 | GET | /api/chat/chat-room |
| 특정 채팅방 생성 | POST | /api/chat/chat-room |
| 특정 채팅방 삭제 | DELETE | /api/chat/delete-room |

### 03) 채팅 API

| 기능 | 메소드 | 주소 |
| :--- | :---: | ---: |
| 특정 채팅방 참여자 목록 호출 | MESSAGE | /:roomId/chat.callParticipants |
| 특정 채팅방 메시지 전송 | MESSAGE | /:roomId/chat.sendMessage |
| 특정 채팅방 유저 입장 알림 | MESSAGE | /:roomId/chat.newUser |


## 03. 프로젝트 실행

### 01) application.yml 설정

src > main > resources > application.yml에 현존하는 설정을 아래와 같은 방식으로 작성해서 이용하시면 됩니다.

```{.no-highlight}
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://[호스트 입력. localhost는 127.0.0.1입니다.]:[포트 번호 입력. 기본 포트는 3306입니다.]/[데이터베이스명 입력]?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf8
    username: [사용자 이름 입력]
    password: [비밀번호 입력]
  redis:
    host: [호스트 입력. localhost는 127.0.0.1입니다.]
    port: [포트 번호 입력. 기본 포트는 6379입니다.]
```

### 02) Docker 설치

### 03) 프로젝트 실행

docker-compose.yml 파일에는 다음이 포함됩니다.
* Redis
* MySQL
* SpringBoot Project (SpringBootChatApp)

docker-compose.yml 파일이 존재하는 위치에서 다음 명령을 실행합니다.
```{.no-highlight}
[SpringBootChatApp folder]> docker-compose up
```
