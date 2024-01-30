package com.example.loc.domain.Location;

import com.example.loc.domain.Member.Member;
import com.example.loc.dto.Location.LocationInfoResDTO;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@Table (name="location_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @Column(name="reg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 등록 ID
    
    @Column(name="reg_name", nullable=false)
    private String name; // 등록 이름

    @Column(name="reg_comment", nullable=false)
    private String comment; // 등록 코멘트 - 여기서 시작시간과 마지막 시간도 적어 넣을 수 있음

    @Column(name="reg_phone", nullable = false)
    private String phone; // 등록 전화번호

    @Column(name="reg_addr", nullable=false)
    private String addr; // 등록 주소

    @Enumerated(EnumType.STRING)
    private Type type; // CAFE, RESTAURANT ...

    // Location DB와 Member DB는 다대1 관계
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Member member;

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY)
    private LocationImg locationImg;

    @Builder
    public Location(Long id, String name, String comment, String phone, String addr, Type type, byte[] imageFile, Member member) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.phone = phone;
        this.addr = addr;
        this.type = type;
        this.member = member;
    }

    public LocationInfoResDTO toLocationInfoDTO() {
        LocationInfoResDTO locationInfoDTO = new LocationInfoResDTO(
            this.getId(),
            this.getName(),
            this.getAddr(),
            this.getComment(),
            this.getPhone(),
            this.getLocationImg().getImgUrl()
        );
        return locationInfoDTO;
    }
}