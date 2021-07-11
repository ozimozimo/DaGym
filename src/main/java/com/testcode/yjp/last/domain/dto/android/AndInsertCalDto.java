package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class AndInsertCalDto {
    private Long id;
    private String title;
    private String start;
    private String end;
    private String description;
    private String type = "카테고리1";


}
