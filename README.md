# 웹 소켓을 이용한 채팅 애플리케이션

로그인을 하고 인증된 사용자만 채팅방에 입장하여 채팅이 가능한 서비스입니다.

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
| 로그인 실패 | POST | /setting/loginMember/fail |

### 02) 채팅방 API

| 기능 | 메소드 | 주소 |
| :--- | :---: | ---: |
| 전체 채팅방 목록 화면 렌더링 | GET | /chat/room |
| 채팅방 입장 화면 렌더링 | GET | /chat/room/detail |
| 특정 채팅방 입장 화면 렌더링 | GET | /chat/room/detail/:roomid |
| 전체 채팅방 목록 호출 | GET | /api/chat/chat-room |
| 특정 채팅방 생성 | POST | /api/chat/chat-room |
| 특정 채팅방 삭제 | DELETE | /api/chat/delete-room |
| 특정 채팅방 접근 실패 | GET | /error/room_failed |
| 특정 채팅방 참여 인원수 제한으로 인한 접근 실패 | GET | /error/participant_join_failed |

### 03) 채팅 API

| 기능 | 메소드 | 주소 |
| :--- | :---: | ---: |
| 특정 채팅방 참여자 목록 호출 | MESSAGE | /:roomId/chat.callParticipants |
| 특정 채팅방 메시지 전송 | MESSAGE | /:roomId/chat.sendMessage |
| 특정 채팅방 유저 입장 알림 | MESSAGE | /:roomId/chat.newUser |
