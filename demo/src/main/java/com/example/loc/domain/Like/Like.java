package com.example.loc.domain.Like;

import static jakarta.persistence.FetchType.*;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;
    private String address;
    private Double grade;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reg_id")
    private Location location;

    @Builder
    public Like(String address, Double grade, Member member, Location location){
        this.address = address;
        this.grade = grade;
        this.member = member;
        this.location = location;
    }
    //삭제 추가.
}
