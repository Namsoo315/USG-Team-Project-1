1차 경진대회 용

# 업주(OWNER) - 등록된 매장(업소) 관련
## 전체적인 로직 이해
### 홈페이지

`DB CLASS DIAGRAM`



```
--- Location 등록 시 필요한 데이터 ---

localhost:8081/api/location/reg (POST 방식)

name -> 매장(업소) 등록시 필요한 이름
comment -> 매장(업소) 등록시 필요한 설명 (+ 운영 시간)
phone -> 매장(업소) 등록시 필요한 전화번호 (010-1111-1111 or 055-111-1111)
addr -> 매장(업소) 등록시 필요한 주소 (서울시 강남구 ~~~)
type -> 매장(업소) 등록 타입 (PLAYGROUND, SIGHTS, CAFE, RESTAURANT)
memberId -> 매장(업소)를 등록하고 있는 회원 ID값(FK)

imgFile -> 파일 형태로 된 이미지 파일(png, jpg 상관없음), 하지만 img파일만
```

```
--- Location 수정 시 필요한 데이터 ---

localhost:8081/api/location/update (PUT 방식)

id -> 매장(업소) 등록시 지정된 값(PK)
name -> 매장(업소) 등록시 필요한 이름
comment -> 매장(업소) 등록시 필요한 설명 (+ 운영 시간)
phone -> 매장(업소) 등록시 필요한 전화번호 (010-1111-1111 or 055-111-1111)
addr -> 매장(업소) 등록시 필요한 주소 (서울시 강남구 ~~~)
type -> 매장(업소) 등록 타입 (PLAYGROUND, SIGHTS, CAFE, RESTAURANT)
memberId -> 매장(업소)를 등록하고 있는 회원 ID값(FK)

imgFile -> 파일 형태로 된 이미지 파일(png, jpg 상관없음), 하지만 img파일만

```

```
--- 홈페이지에 들어가는 정보 ---

localhost:8081/api/location/home (GET 방식)

id -> 매장(업소) 등록시 지정된 값(PK)
name -> 매장(업소) 이름
comment -> 매장(업소) 설명 (+ 운영 시간)
type -> 매장(업소) 타입 (PLAYGROUND, SIGHTS, CAFE, RESTAURANT)
imgUrl -> (data:image;base64) base64로 변환된 이미지 바이트

-> Type 값으로 비교를 해 필요한 데이터를 알맞게 배치하면 되겠죠?
```

```
--- 홈페이지에서 이미지 클릭 후 접속 들어가는 정보 ---

localhost:8081/api/location/home/info/{id} (GET 방식)

프론트에서 id 값을 변경시켜 주며 리뷰까지 나와 있는 정보 페이지에 들어가면
받을 수 있는 데이터

id -> 매장(업소) id
name -> 매장(업소) 이름
addr -> 매장(업소) 주소
comment -> 매장(업소) 설명 (+ 운영 시간)
phone -> 매장(업소)
imgUrl -> (data:image;base64) base64로 변환된 이미지 바이트

```