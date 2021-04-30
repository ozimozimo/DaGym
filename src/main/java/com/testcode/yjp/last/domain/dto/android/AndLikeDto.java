package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndLikeDto {
    private Long member_id;
    private Long board_id;
    private String bool;
    private int recomment_cnt;
}
