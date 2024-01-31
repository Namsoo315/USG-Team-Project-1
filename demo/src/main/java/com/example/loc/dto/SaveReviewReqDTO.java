package com.example.loc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveReviewReqDTO {

    private Long locationId;
    private String review;
    private Double grade;
}
