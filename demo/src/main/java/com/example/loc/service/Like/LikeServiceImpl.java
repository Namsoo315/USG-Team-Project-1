package com.example.loc.service.Like;

import com.example.loc.domain.Like.Like;
import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Member.Member;
import com.example.loc.dto.Like.MyLikeDTO;
import com.example.loc.dto.Like.MyLikeResDTO;
import com.example.loc.dto.Like.SaveLikeReqDTO;
import com.example.loc.repository.Like.LikeRepository;
import com.example.loc.repository.Location.LocationRepository;
import com.example.loc.repository.MemberRepository;
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
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Long saveLike(SaveLikeReqDTO request) {
        Member findMember = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        Location findLocation = locationRepository.findById(request.getLocationId()).orElseThrow(
                () -> new IllegalArgumentException("Location Not Exist")
        );

        Like like = Like
                .builder()
                .address(request.getAddress())
                .grade(request.getGrade())
                .member(findMember)
                .location(findLocation)
                .build();
        Like savedLike = likeRepository.save(like);

        return savedLike.getId();
    }

    @Override
    @Transactional
    public MyLikeResDTO getMyLike(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        List<Like> likes = likeRepository.findLikeByMemberId(findMember.getId());
        List<MyLikeDTO> myLikeDTOList = new ArrayList<>();

        for (Like like : likes) {
            MyLikeDTO myLikeDTO = MyLikeDTO
                    .builder()
                    .address(like.getAddress())
                    .grade(like.getGrade())
                    .name(like.getLocation().getName())
                    .type(like.getLocation().getType())
                    .build();
            myLikeDTOList.add(myLikeDTO);
        }

        return new MyLikeResDTO(myLikeDTOList, myLikeDTOList.size());
    }
}
