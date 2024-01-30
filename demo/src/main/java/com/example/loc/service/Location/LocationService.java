package com.example.loc.service.Location;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.loc.domain.Member.Member;
import com.example.loc.dto.Location.LocationInfoReqDTO;
import com.example.loc.dto.Location.LocationInfoResDTO;
import com.example.loc.dto.Location.HomeInfoAllDTO;
import com.example.loc.dto.Location.RegistInfoReqDTO;

public interface LocationService {

     
    /*
     * 홈페이지 송신 (프론트에 모든 데이터 전송하기 위해 repo에서 받아오는 객체들)
     */
    List<HomeInfoAllDTO> getHomePageData();

    /*
     * 매장(업소) 송신 (하나의 id 값을 받으면 그 값에 대응하는 필드 정보 가져오기)
     */
    LocationInfoResDTO getLocationPageData(LocationInfoReqDTO request, Long id);

    /*
     * 등록
     */
    Long regLocation(RegistInfoReqDTO regDTO, MultipartFile imgFile, Member member) throws Exception;

    // 삭제

    /*
     * 수정
     */
    void updateLocation(Long locationId, RegistInfoReqDTO updateInfoDTO, MultipartFile imgFile) throws Exception;
    // 조회
} 
