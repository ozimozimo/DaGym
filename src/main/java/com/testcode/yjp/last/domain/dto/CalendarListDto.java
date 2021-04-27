package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Qcalendar;
import com.testcode.yjp.last.domain.calendar;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CalendarListDto {

    private Long id;
    private String title;
    private String startDate;
    private String endDate;
    private String description;
    private String type;
    private String backgroundColor;
    private String textColor;
    private boolean allDay;

    public CalendarListDto(calendar entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.description = entity.getDescription();
        this.type = entity.getType();
        this.backgroundColor = entity.getBackgroundColor();
        this.textColor = entity.getTextColor();
        this.allDay = entity.isAllDay();

    }
}
