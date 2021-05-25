package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Diet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietDto {
    private Long diet_id;
    private String diet_name;
    private String diet_member_id;
    private String eat_rate;
    private String diet_kcal;
    private String diet_carbo;
    private String diet_protein;
    private String diet_fat;
    private String diet_time;
    private String diet_date;

    public DietDto(Diet diet) {
        this.diet_id = diet.getDiet_id();
        this.diet_member_id = diet.getDiet_member_id();
        this.diet_name = diet.getDiet_name();
        this.diet_kcal = diet.getDiet_kcal();
        this.diet_carbo = diet.getDiet_carbo();
        this.diet_protein = diet.getDiet_protein();
        this.diet_fat = diet.getDiet_fat();
        this.diet_time = diet.getDiet_time();
        this.diet_date = diet.getDiet_date();
        this.eat_rate = diet.getEat_rate();
    }
}
