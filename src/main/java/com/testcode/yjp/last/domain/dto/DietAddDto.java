package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Diet;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietAddDto {
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

    public DietAddDto(Diet diet) {
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

    public Diet toEntity() {
        return Diet.builder()
                .diet_id(diet_id)
                .diet_name(diet_name)
                .diet_kcal(diet_kcal)
                .diet_carbo(diet_carbo)
                .diet_protein(diet_protein)
                .diet_fat(diet_fat)
                .diet_time(diet_time)
                .diet_date(diet_date)
                .eat_rate(eat_rate)
                .build();
    }

    @Builder
    public DietAddDto(Long diet_id, String diet_name, String diet_kcal, String diet_carbo, String diet_protein, String diet_fat, String diet_time, String diet_date, String eat_rate) {
        this.diet_id = diet_id;
        this.diet_name = diet_name;
        this.diet_kcal = diet_kcal;
        this.diet_carbo = diet_carbo;
        this.diet_protein = diet_protein;
        this.diet_fat = diet_fat;
        this.diet_time = diet_time;
        this.diet_date = diet_date;
        this.eat_rate = eat_rate;
    }
}

