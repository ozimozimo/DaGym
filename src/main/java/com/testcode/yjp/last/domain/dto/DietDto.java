package com.testcode.yjp.last.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietDto {
    private Long diet_id;
    private String diet_name;
    private String diet_kcal;
    private String diet_carbo;
    private String diet_protein;
    private String diet_fat;
    private String diet_time;
}
