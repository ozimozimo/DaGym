package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndTrainerSearchDto {
    private String search;

    private Long member_id; // PTController의 trainerApply()
    private Long trainer_id; // PTController의 trainerApply()
}
