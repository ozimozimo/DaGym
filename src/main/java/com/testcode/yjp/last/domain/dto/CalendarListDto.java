package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CalendarListDto {

    private Long id;
    private String title;
    private String start;
    private String end;
    private String description;
    private String type;
    private String backgroundColor;
    private String textColor;
    private Member member;
    private TrainerInfo trainerInfo;

    public CalendarListDto(Calendar entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.start = entity.getStart();
        this.end = entity.getEnd();
        this.description = entity.getDescription();
        this.type = entity.getType();
        this.backgroundColor = entity.getBackgroundColor();
        this.textColor = entity.getTextColor();
        this.member = entity.getMember();
        this.trainerInfo = entity.getTrainerInfo();
    }
}
