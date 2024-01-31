package com.example.gateway.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_nickname", nullable = false, unique =true)
    private String nickname;

    @Column(name="member_email", nullable = false, unique =true)
    private String email;

    @Column(name="member_password", nullable = false)
    private String password;

    @Column(name="member_birth", nullable = false)
    private LocalDateTime birth;

    @Enumerated(EnumType.STRING)
    @Column(name="member_role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name="member_gender")
    private Gender gender;

    @Builder
    public Member(String nickname, String email, String password, LocalDateTime birth, Role role, Gender gender) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.role = role;
        this.gender = gender;
    }
}
