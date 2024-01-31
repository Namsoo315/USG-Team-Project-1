package com.example.loc.service;

import com.example.loc.dto.MyReviewResDTO;
import com.example.loc.dto.SaveReviewReqDTO;
import com.example.loc.dto.UpdateReviewReqDTO;

public interface ReviewService {

    Long saveReview(SaveReviewReqDTO request, Long memberId);
    MyReviewResDTO getMyReview(Long memberId);
    Long updateReview(UpdateReviewReqDTO request, Long memberId);
}
