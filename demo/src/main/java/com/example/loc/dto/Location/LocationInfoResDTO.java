package com.example.loc.dto.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationInfoResDTO {
    private Long id; // 업소 id
    private String name; // 업소 이름
    private String addr; // 업소 주소
    private String comment; // 코멘트 안에 영업 시간이 있음
    private String phone; // 업소 연락처
    private String imgUrl; // 이미지 주소
}
