package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Advice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdviceDto {
    private Long id;
    private String advice_type;
    private String advice_content;
    private String advice_date;

    public AdviceDto(Advice advice) {
        this.id = advice.getId();
        this.advice_type = getAdvice_type();
        this.advice_content = getAdvice_content();
        this.advice_date = getAdvice_date();
    }
}
