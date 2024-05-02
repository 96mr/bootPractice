## 쇼핑몰
> Spring boot 기반으로 진행한 개인 프로젝트입니다.


## 제작 기간
2023년 4월 10일 ~ (진행중)

## 사용 기술
__Back-end__
* Java 8
* Spring Boot 2.6
* Spring Security 5
* JPA
* Oracle


__Front-end__
* HTML
* CSS
* JavaScript(JQuery)

## Notion & Blog
[Notion](https://spotty-gardenia-d4a.notion.site/b0bfa1fe9dba401da530ccf37403a9a2?pvs=4)

[Blog](https://cookie9606.tistory.com/115)

## 기능
* 회원
  * 회원가입
  * 로그인, 로그아웃
* 제품
  * 제품 등록
  * 제품 조회
  * 장바구니 담기
* 회원 페이지
  <details>
   <summary>
    권한 체크
   </summary>
   
    //SecurityConfiguration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		    http
			    .authorizeHttpRequests((authz)->authz			//요청에 대한 권한 설정
							.antMatchers("/member/**").hasAnyRole("GUEST","USER","ADMIN")
							.antMatchers("/admin/**").hasRole("ADMIN")
							.antMatchers("/**").permitAll()
							.anyRequest().authenticated()
       );
    }
   
  </details>
  * 배송지 등록, 변경
* 장바구니
  * 장바구니 조회
  * 제품 수량 변경, 삭제
* 주문
  * 장바구니 -> 주문
  * 바로구매 -> 주문
  * 주문 조회
* 리뷰
  * 리뷰 작성
  * 리뷰 조회
