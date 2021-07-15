package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Exercise;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExCategoryDto {
    private Long ex_id;
    private String ex_category;
    private String ex_parts;
    private String ex_name;

    public ExCategoryDto(Exercise e) {
        this.ex_id = e.getEx_id();
        this.ex_category = e.getEx_category();
        this.ex_parts = e.getEx_parts();
        this.ex_name = e.getEx_name();
    }
}
