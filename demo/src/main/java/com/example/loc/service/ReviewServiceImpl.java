package com.example.loc.service;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Member.Member;
import com.example.loc.domain.review.Review;
import com.example.loc.dto.MyReviewDTO;
import com.example.loc.dto.MyReviewResDTO;
import com.example.loc.dto.SaveReviewReqDTO;
import com.example.loc.dto.UpdateReviewReqDTO;
import com.example.loc.repository.Location.LocationRepository;
import com.example.loc.repository.MemberRepository;
import com.example.loc.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;


    @Override
    @Transactional
    public Long saveReview(SaveReviewReqDTO request, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        Location findLocation = locationRepository.findById(request.getLocationId()).orElseThrow(
                () -> new IllegalArgumentException("Location Not Exist")
        );

        Review review = Review
                .builder()
                .review(request.getReview())
                .grade(request.getGrade())
                .member(findMember)
                .location(findLocation)
                .build();
        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }

    @Override
    @Transactional
    public MyReviewResDTO getMyReview(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        List<Review> reviews = reviewRepository.findReviewsByMemberId(findMember.getId());
        List<MyReviewDTO> myReviewDTOList = new ArrayList<>();

        for (Review review : reviews) {
            MyReviewDTO myReviewDto = MyReviewDTO
                    .builder()
                    .review(review.getReview())
                    .grade(review.getGrade())
                    .name(review.getLocation().getName())
                    .type(review.getLocation().getType())
                    .build();
            myReviewDTOList.add(myReviewDto);
        }

        return new MyReviewResDTO(myReviewDTOList, myReviewDTOList.size());
    }

    @Override
    @Transactional
    public Long updateReview(UpdateReviewReqDTO request, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        Review findReview = reviewRepository.findById(request.getReviewId()).orElseThrow(
                () -> new IllegalArgumentException("Review Not Exist")
        );

        if (findMember.getId().equals(findReview.getMember().getId())) {
            findReview.updateReview(request.getReview(), request.getGrade());
            return findReview.getId();
        } else {
            throw new IllegalArgumentException("리뷰 작성자와 일치하지 않습니다.");
        }
    }
}
