# 블로그 검색 & 인기 검색어 조회 API

## 블로그 검색
- `GET /blogs/search?query=검색어&page=1&size=10&sort=accuracy`
  - qurey -> required, String
  - page -> not required, Number, default 1
  - size -> not required, Number, default 10
  - sort -> not required, String, default "accuracy"
- 매 호출마다 검색어 카운트 1 증가
- 다음(daum) 블로그 글을 default로 검색
- 다음 서비스에 연결 불가 시, 네이버 블로그 글 검색
  
## 인기 검색어 조회
- `GET /blogs/popular-queries`
- 블로그 검색 API로 질의한 검색어(query)의 수를 기반으로 최대 10개의 인기 검색어와 카운트 

## Prerequisite
- `application-API-KEY.properties` 파일 생성 후 아래 설정 값을 입력합니다.
  - `kakao.auth.type = KakaoAK`
    `kakao.auth.rest-api-key = {KAKAO-REST-API-KEY}`
    `naver.auth.client-id = {NAVER-CLIENT-ID}`
    `naver.auth.client-secret = {NAVER-CLIENT-SECRET}`
- H2 Database 설치 및 실행이 필요합니다.
  - `url`: `jdbc:h2:tcp://localhost/~/test`
    `username`: `sa`
- Redis 설치 및 실행이 필요합니다.
  - `port`: `6379`

## 기술 스택
- Java 11
- Spring Boot 2.7.9
- Gradle
- H2 Database
- Kakao API, Naver API
- Spring Cloud OpenFeign
  - 카카오, 네이버 API 호출하기 위해 사용
- Redisson
  - 인기 검색어 제공 및 캐싱, 동시성 이슈 처리를 위해 사용
- mockito
  - 유닛 테스트를 위해 사용
