package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndBoardSearchDto {
    private String search;
    private String head;

    public AndBoardSearchDto(String search, String head) {
        this.search = search;
        this.head = head;
    }
}
