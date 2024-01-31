package com.example.loc.dto.Like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveLikeReqDTO {

    private Long memberId;
    private Long locationId;
    private String address;
    private Double grade;
}
