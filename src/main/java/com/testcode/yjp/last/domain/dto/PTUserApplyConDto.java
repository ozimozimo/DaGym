package com.testcode.yjp.last.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

// trainer.js에서 updateAccept 에서 받아온 id랑 apply_if 값
public class PTUserApplyConDto {
    private Long id;
    private String user_id;
    private String apply_if;

}