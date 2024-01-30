package com.example.loc.repository.Location;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loc.domain.Location.Location;
import com.example.loc.dto.Location.HomeInfoDTO;

public interface LocationRepository extends JpaRepository<Location, Long>{

    // DB에 있는 데이터중 주소와 name이 같은 것을 찾기
    Optional<Location> findByAddrAndName(String addr, String name);

    // DB에 있는 데이터 중 id가 일치하는 정보 찾기
    Optional<Location> findById(Long id);

    // id,name,comment 조회
    List<HomeInfoDTO> findAllByIdNotNull();

    
}
