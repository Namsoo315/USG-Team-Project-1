package com.example.loc.repository.Like;

import com.example.loc.domain.Like.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value ="select r from Like r" +
            " join fetch r.member rm" +
            " join fetch r.location rl" +
            " where r.member.id = :memberId" )
    List<Like> findLikeByMemberId(Long memberId);
}
