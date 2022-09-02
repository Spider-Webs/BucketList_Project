# Rest API 란??

- Representational State Transfer 의 약자

- 즉, 자원의 표현에 의한 상태 전달
- 데이터와 기능의 집합을 제공하여 컴퓨터 프로그램 간 상호 작용을 촉진하며 
- 서로 정보 교환이 가능하도록 하는 것


## Rest 구성 요소


- 자원 : URI
- 행위 : HTTP Method
- 표현 : 데이터 타입 (Json)


## Rest 특징 

- Server - Client 구조 (HTTP 프로토콜)
- 무 상태성
  - 굉장히 효율적
  - 확장성이 높음
- Cacheable
  - 웹의 가장 강력한 특징


## 장점

- 모든 플랫폼에서 사용 가능
- 의도하는 바를 쉽게 파악할 수 있음


## 단점

- 표준 자체가 존재하지 않음
- 사용할 수 있는 메소드가 제한적임
- 구형 브라우저에서 호환이 되지 않는 경우가 많음



## 설계 예시


- URI 는 데이터를 표현하는 명사 사용 (단수형)
- 마지막에는 슬래시 포함 X
- 언더바 대신에 하이픈 사용 (Kebab Case)
- 파일 확장자는 URI 에 포함하지 않음
- 행위를 포함하지 않음


## CRUD (Car)

- 전체 목록 조회 GET  /car
- 한개 객체 조회 GET  /car/id
- 한개 객체 생성 POST /car/id
- 한개 객체 수정 PUT  /car/id
- 한개 객체 삭제 DELETE  /car/id
- 한개 객체 일부 수정 PATCH  /car/id









