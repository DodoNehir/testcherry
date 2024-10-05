# 쇼핑몰 서비스
* 상품 구매와 관련된 서비스를 제공합니다.
* REST API로 구현하였고 콘솔로 확인이 가능합니다

## 빌드 결과물
* ...

## 환경 소개
* IntelliJ IDEA 2024.1.2
* JAVA 17
* Spring Boot 3.3.3, Gradle
* H2 in-memory
* Junit5 기반 테스트 작성
* (REST docs? / Swagger?)기반 API 명세 작성)

## 프로젝트 구성 소개



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

회원 (Member):
* (이름, 주소, 전화번호)
* 주문을 하기 위해서는 미리 회원가입이 되어 있어야 한다.

제품 (Product):
* (이름, 설명, 수량)
* 제품을 등록할 수 있다.

주문정보 (Order):
* (회원 id, 주문날짜, 주문 아이템 리스트)
* 주문이 들어가기 전에 재고를 확인한다.
  * 주문 내 하나의 제품이라도 재고가 없으면 주문을 취소한다.
  * 주문이 성공하면 재고를 업데이트한다.

주문 아이템 (Order Item):
* (주문 id, 제품 id, 제품 수량)
* 주문에 들어간 개별 제품의 수량을 나타낸다.



