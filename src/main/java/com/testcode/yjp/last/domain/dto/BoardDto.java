package com.testcode.yjp.last.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDto {
    private Long id;
    private String title;
    private String user_id;
    private String content;
    private int hit;
    private int recommend;
    private LocalDateTime modifiedDate,regDate;
}
