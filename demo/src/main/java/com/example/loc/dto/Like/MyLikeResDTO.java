package com.example.loc.dto.Like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyLikeResDTO {

    private List<MyLikeDTO> myLikeDTOS;
    private Integer myLikeCount;
}
