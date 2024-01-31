package com.example.loc.controller;

import com.example.loc.domain.Member.Member;
import com.example.loc.dto.Like.MyLikeResDTO;
import com.example.loc.dto.Like.SaveLikeReqDTO;
import com.example.loc.global.message.MessageResponse;
import com.example.loc.service.Like.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LoginMemberGetter loginMemberGetter;

    @PostMapping("/api/like")
    @Operation(summary = "관심 목록 저장 *")
    public ResponseEntity<MessageResponse> saveLike(@RequestBody @Valid SaveLikeReqDTO request) {

        Long savedLikeId = likeService.saveLike(request);
        return ResponseEntity.ok(new MessageResponse(savedLikeId, "리뷰 작성이 완료되었습니다."));
    }

    @GetMapping("/api/like")
    @Operation(summary = "내 관심 목록 조회 *")
    public  ResponseEntity<MessageResponse> getMyLike(HttpServletRequest request) {
        Member loginMember = loginMemberGetter.getLoginMember(request.getHeader("Authorization"));
        MyLikeResDTO myLikeResDTO = likeService.getMyLike(loginMember.getId());

        return ResponseEntity.ok(new MessageResponse(myLikeResDTO, "관심 목록 반환 완료"));
    }
}
