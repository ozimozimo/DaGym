package com.testcode.yjp.last.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class OooDto {
    private Long id;
    private String title;
    private String user_id;
    private String content;
    private String answer;

    private LocalDateTime modifiedDate,regDate;
}
