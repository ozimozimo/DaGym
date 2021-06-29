package com.testcode.yjp.last.domain.dto;

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
}
