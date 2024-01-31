package com.example.loc.service.Like;

import com.example.loc.dto.Like.MyLikeResDTO;
import com.example.loc.dto.Like.SaveLikeReqDTO;

public interface LikeService {

    Long saveLike(SaveLikeReqDTO request);
    MyLikeResDTO getMyLike(Long memberId);
}
