## 쇼핑몰
> 회원 관리, 제품 소개, 장바구니, 주문, 리뷰 기능을 포함한 기본적인 전자상거래 흐름을 구현했습니다. 관리자는 제품 등록 가능하며, 회원은 배송지 등록, 장바구니, 구매 기능을 이용 가능합니다. (실제 결제 구현X)

---
## 제작 기간
2023년 4월 10일 ~ 2023년 6월 13일

---
## 기술 스택
![Java 8](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 2.6](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring-Data-JPA](https://img.shields.io/badge/SpringDataJPA-000000?style=for-the-badge&logo=SpringDataJPA&logoColor=white)
![Oracle](https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymleaf-2c83b9?style=for-the-badge&logo=thymeleaf&logoColor=white) 

---
## 기능
### ✏ 회원
-Spring Security를 이용하여 권한 부여 
- 회원 페이지 권한 체크
- 다음 주소 API를 이용한 배송지 등록, 변경
### ✏ 제품
- SummerNote를 이용하여 제품 설명 등록
- 카테고리 별 제품 조회
- 제품 상세 조회
### ✏ 장바구니
- 장바구니 담기 : 비회원은 세션에, 회원은 데이터베이스에 제품 상품과 수량 저장
- 장바구니 조회
### ✏ 주문
- 구매 방식 : 바로구매 / 장바구니 구매, 비회원 / 회원
- 주문서 조회
### ✏ 후기
- 구매한 제품 후기 작성
- 작성한 후기 내역 조회

---
## 상세 내용
[Notion](https://spotty-gardenia-d4a.notion.site/b0bfa1fe9dba401da530ccf37403a9a2?pvs=4)
[Blog](https://cookie9606.tistory.com/115)
