package com.example.loc.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loc.domain.Member.Member;
import com.example.loc.dto.Location.HomeInfoAllDTO;
import com.example.loc.dto.Location.HomeInfoDTO;
import com.example.loc.dto.Location.LocationInfoReqDTO;
import com.example.loc.dto.Location.LocationInfoResDTO;
import com.example.loc.dto.Location.RegistInfoReqDTO;
import com.example.loc.global.message.MessageResponse;
import com.example.loc.repository.Location.LocationRepository;
import com.example.loc.repository.MemberRepository;
import com.example.loc.service.Location.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Slf4j // log 사용할 수 있게 해주는 거
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;
    private final LocationRepository locationRepository;
    private final MemberRepository memberRepository;
    
    @Operation(summary = "홈페이지 필요한 데이터 송신")
    @GetMapping("/home") // 홈페이지에 필요한 데이터 송신
    public ResponseEntity<MessageResponse> getHomePageData() {
        List<HomeInfoAllDTO> homePageData = locationService.getHomePageData();

        MessageResponse<List<HomeInfoAllDTO>> response = new MessageResponse<>("홈페이지 데이터 성공적 송신 완료");
        response.setData(homePageData);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "홈페이지에서 이미지 클릭 후 접속하는 상세정보 페이지")
    @GetMapping("/home/info/{id}") // 리뷰 작성 페이지를 접속하기 위한 
    public ResponseEntity<MessageResponse> getLocationPageData(@Valid @PathVariable Long id, LocationInfoReqDTO request) {
        LocationInfoResDTO locationPageData = locationService.getLocationPageData(request, id);
        MessageResponse<LocationInfoResDTO> response = new MessageResponse<>("매장 등록 정보 송신 완료");
        response.setData(locationPageData);
        return ResponseEntity.ok(response);
    }
    
    
    @Operation(summary = "매장 등록")
    @PostMapping("/reg") // 매장 등록
    public ResponseEntity<MessageResponse> locationNew(@Valid @ModelAttribute RegistInfoReqDTO registInfoReqDTO, BindingResult bindingResult, @RequestParam("imgFile") MultipartFile imgFile) {
        MessageResponse<List<HomeInfoDTO>> response = new MessageResponse<>("에러 발생");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(response);
        }

        if (imgFile.isEmpty() && registInfoReqDTO.getId() == null) {
            response = new MessageResponse<>("파일 이미지는 필수 입력 대상입니다.");
            return ResponseEntity.ok(response);
        }

        try {
            Optional<Member> member = memberRepository.findById(registInfoReqDTO.getMemberId());
            locationService.regLocation(registInfoReqDTO, imgFile, member.get());
        } catch (Exception e) {
            response = new MessageResponse<>("이미지 등록 중 에러가 발생하였습니다.");
            log.info(e.getMessage());
            return ResponseEntity.ok(response);
        }

        response = new MessageResponse<>("등록 성공");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "매장 수정")
    @PutMapping("/update/{locationId}")
    public ResponseEntity<MessageResponse> updateLocation (
        @PathVariable Long locationId,
        @Valid @ModelAttribute RegistInfoReqDTO updateInfoDTO,
        @RequestParam(value = "imgFile", required = true) MultipartFile imgFile
    ) {
        try {
            locationService.updateLocation(locationId, updateInfoDTO, imgFile);
            return ResponseEntity.ok(new MessageResponse<>("매장 정보 수정 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>("매장 정보 수정 실패 : " + e.getMessage()));
        }
    }
}
