package com.example.loc.dto.Location;

import com.example.loc.domain.Location.Type;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomeInfoAllDTO {
    
    private Long id;
    private String name;
    private String comment;
    private Type type;
    private String imgUrl;

}
