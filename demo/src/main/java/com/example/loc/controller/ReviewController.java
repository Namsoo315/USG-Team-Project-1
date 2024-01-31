package com.example.loc.controller;

import com.example.loc.domain.Member.Member;
import com.example.loc.dto.MyReviewResDTO;
import com.example.loc.dto.SaveReviewReqDTO;
import com.example.loc.dto.UpdateReviewReqDTO;
import com.example.loc.global.message.MessageResponse;
import com.example.loc.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final LoginMemberGetter loginMemberGetter;

    @PostMapping("/api/review")
    @Operation(summary = "리뷰 저장 *")
    public ResponseEntity<MessageResponse> saveReview(@RequestBody SaveReviewReqDTO request) {

        Long savedReviewId = reviewService.saveReview(request);

        return ResponseEntity.ok(new MessageResponse(savedReviewId, "리뷰 작성이 완료되었습니다."));
    }

    @GetMapping("/api/review")
    @Operation(summary = "내 리뷰 조회 *")
    public ResponseEntity<MessageResponse> getMyReview(HttpServletRequest request) {

        Member loginMember = loginMemberGetter.getLoginMember(request.getHeader("Authorization"));
        MyReviewResDTO myReviewResDTO = reviewService.getMyReview(loginMember.getId());

        return ResponseEntity.ok(new MessageResponse(myReviewResDTO, "리뷰 목록 반환 완료"));
    }

    @PutMapping("/api/review")
    @Operation(summary = "내 리뷰 수정 *")
    public ResponseEntity<MessageResponse> updateReview(@RequestBody UpdateReviewReqDTO reviewRequest,
                                                        HttpServletRequest request) {
        Member loginMember = loginMemberGetter.getLoginMember(request.getHeader("Authorization"));
        Long updateReviewId = reviewService.updateReview(reviewRequest, loginMember.getId());

        return ResponseEntity.ok(new MessageResponse(updateReviewId, "리뷰 수정 완료"));
    }
}
