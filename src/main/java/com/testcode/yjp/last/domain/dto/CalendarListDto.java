package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Qcalendar;
import com.testcode.yjp.last.domain.calendar;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CalendarListDto {

    private Long id;
    private String calendar_date;
    private String calendar_memo;

    public CalendarListDto(calendar entity) {
        this.id = entity.getId();
        this.calendar_date = entity.getCalendar_date();
        this.calendar_memo = entity.getCalendar_memo();
    }
}
