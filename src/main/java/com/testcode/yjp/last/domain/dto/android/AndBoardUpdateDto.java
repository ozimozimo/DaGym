package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndBoardUpdateDto {
    private String title;
    private String content;

    public AndBoardUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
