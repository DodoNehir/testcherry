# 쇼핑몰 서비스

* 상품을 검색하고 주문할 수 있게 해주는 rest api 입니다.
* 지금은 로컬에서 포스트맨으로 데이터를 볼 수 있지만 언젠가는 실제 서비스를 꿈꾸고 있어요

### 빌드 결과물

* ...

### 기술 스택

* Spring Boot 3.x
* Java 17
* H2 in-memory

### 아키텍처

* 서버 : Spring Boot
* 데이터베이스 : H2

### 시나리오

* 쇼핑몰을 방문하면 제품을 볼 수 있어요
* 주문하려면 회원가입을 해야 해요
* 여러 상품을 같이 주문할 수 있어요. 하나의 주문(Order)에 들어간 각 제품들은 OrderItem으로 구분해요
* 재고가 없으면 주문할 수 없어요
  

### 핵심 기술 설명

#### JWT
웹 브라우저와 모바일에서 동시에 사용 가능하게 만든다는 생각과 나중에 클라우드에서 서비스하게 될 것을 염두에 두고 세션 대신 선택했어요. access 토큰은 1시간,
refresh 토큰은 24시간의 유효해요

#### Swagger UI
프론트와의 협력에 필요할것이라 생각해서 추가했어요

#### 헬스체크
1시간마다 정기적으로 확인해서 문제가 생기면 메시지를 전송해요

#### SSL 인증서
직접 만든 인증서라 효력은 없지만 https를 사용하고 싶었어요  
  


### 프로젝트 구성 소개

```
.
├── build.gradle.kts
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── testcherry
    │   │               ├── config
    │   │               ├── controller
    │   │               ├── domain
    │   │               ├── dto
    │   │               ├── repository
    │   │               └── service
    │   └── resources
    │       ├── application.yaml
    │       ├── data.sql
    │       └── schema.sql
    └── test


```

```mermaid
graph LR
A(입력)-->B[연산]
B-->C(출력)
```

### 발생한 문제와 해결방법
* 
