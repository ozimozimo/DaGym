package com.testcode.yjp.last.domain.dto.android;

import com.testcode.yjp.last.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class AndPTUserSaveDto {
    private Long member_id;
    private Long trainer_id;
    private String start_date;
    private String end_date;
}