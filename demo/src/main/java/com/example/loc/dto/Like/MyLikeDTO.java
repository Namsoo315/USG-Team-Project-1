package com.example.loc.dto.Like;

import com.example.loc.domain.Location.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyLikeDTO {

    private String name;
    private Type type;
    private String address;
    private Double grade;
}
