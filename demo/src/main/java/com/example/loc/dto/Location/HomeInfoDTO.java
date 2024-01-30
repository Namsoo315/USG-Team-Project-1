package com.example.loc.dto.Location;

import com.example.loc.domain.Location.LocationImg;
import com.example.loc.domain.Location.Type;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class HomeInfoDTO {
    
    private Long id;
    private String name;
    private String comment;
    private Type type;
    private LocationImg locationImg;
}
