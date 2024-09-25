# testcherry

### 설명

쇼핑몰

* 상품들을 판매하는 서비스를 RESTfulAPI로 구현했습니다.
* Java 기반으로 작성되었으며 콘솔로 확인이 가능합니다.

### 요구사항

주문 (Order):
* 사용자는 원하는 수량만큼 상품을 주문할 수 있다.
* 하나 이상의 상품을 주문할 때 주문을 만들 수 있다.
* 주문이 들어가기 전에 재고를 확인한다.
  * 주문 내 하나의 상품이라도 품절이라면 주문을 취소한다.
  * 주문이 성공하면 재고를 업데이트한다.

회원 (Member):
* 주문을 하기 위해서는 미리 회원가입이 되어 있어야 한다.
* 회원은 이름, 주소, 전화번호가 필요하다.

상품 (Product):
* 상품을 등록할 수 있다.
* 상품은 이름, 설명, 가격이 필요하다.

