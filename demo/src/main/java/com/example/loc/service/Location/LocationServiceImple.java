package com.example.loc.service.Location;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Location.LocationImg;
import com.example.loc.domain.Member.Member;
import com.example.loc.dto.Location.HomeInfoDTO;
import com.example.loc.dto.Location.LocationInfoReqDTO;
import com.example.loc.dto.Location.LocationInfoResDTO;
import com.example.loc.dto.Location.HomeInfoAllDTO;
import com.example.loc.dto.Location.RegistInfoReqDTO;
import com.example.loc.repository.Location.LocationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImple implements LocationService{

    private final LocationRepository locationRepository;
    private final ImgService imgService;

    // 홈페이지 송신
    @Override
    public List<HomeInfoAllDTO> getHomePageData() {
        List<HomeInfoDTO> homepageData = locationRepository.findAllByIdNotNull();

        List<HomeInfoAllDTO> homepageAllData = homepageData.stream().map(location ->{
            String imgUrl = location.getLocationImg() != null ? location.getLocationImg().getImgUrl() : null;
            String base64Image = null;
            try {
                base64Image = imgService.getBase64Image(imgUrl);
            } catch (IOException e){
                e.printStackTrace();
            }
            return new HomeInfoAllDTO(location.getId(), location.getName(),location.getComment(), location.getType(), base64Image);
        }).collect(Collectors.toList());
        
        return homepageAllData;
    }

    // 매장 정보 조회
    @Override
    public LocationInfoResDTO getLocationPageData(LocationInfoReqDTO request, Long id) {
        request.setId(id);
        LocationInfoResDTO locationPageData = locationRepository.findById(request.getId()).map(location -> location.toLocationInfoDTO()).orElseThrow(
            () -> new Error("등록되어 있지 않은 매장(업소) 입니다.")
        );
        String base64Image = null;
        try {
            base64Image = imgService.getBase64Image(locationPageData.getImgUrl());
        } catch (IOException e){
            e.printStackTrace();
        }
        return new LocationInfoResDTO(locationPageData.getId(), locationPageData.getName(),locationPageData.getAddr(), locationPageData.getComment(),locationPageData.getPhone(), base64Image);
    }

    // 등록
    public Long regLocation(RegistInfoReqDTO regInfoDTO, MultipartFile imgFile, Member member) throws Exception {

        // 매장(업소) 등록
        Location location = regInfoDTO.createLocation(member);
        locationRepository.save(location);

        // 이미지 등록
        LocationImg locationImg = new LocationImg();
        locationImg.setLocation(location);
        imgService.saveImg(locationImg, imgFile);

        return location.getId();
    }

    

    // 삭제

    // 수정
    @Override
    public void updateLocation(Long locationId, RegistInfoReqDTO updateInfoDTO, MultipartFile imgFile) throws Exception {
        // 주어진 ID에 해당하는 Location 조회
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();

            // 새로운 정보로 업데이트
            location.setName(updateInfoDTO.getName());
            location.setComment(updateInfoDTO.getComment());
            location.setPhone(updateInfoDTO.getPhone());
            location.setAddr(updateInfoDTO.getAddr());
            location.setType(updateInfoDTO.getType());

            // 이미지 업데이트
            if (imgFile != null) {
                LocationImg locationImg = location.getLocationImg();
                if (locationImg == null) {
                    locationImg = new LocationImg();
                    locationImg.setLocation(location);
                }
                imgService.saveImg(locationImg, imgFile);
            }

            // DB 저장
            locationRepository.save(location);
        } else {
            throw new RuntimeException("해당 ID에 대한 매장 정보 없음");
        }
    }

    // 조회

    
    
}
