package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.dto.CalendarListDto;
import com.testcode.yjp.last.domain.dto.CommentsListResponseDto;
import com.testcode.yjp.last.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    @Transactional(readOnly = true)
    public List<CalendarListDto> findAll(Long id) {
        return calendarRepository.findAll(id).stream()
                .map(CalendarListDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(String calendar_start, String calendar_end) {
        calendarRepository.deleteCalendar(calendar_start,calendar_end);
    }

    public void update(Long id, CalendarListDto calendarListDto) {
        Calendar calendar = calendarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다다. id=" + id));;
        calendar.update(calendarListDto.getTitle(), calendarListDto.getStart(), calendarListDto.getEnd(),
                calendarListDto.getDescription(), calendarListDto.getType(), calendarListDto.getBackgroundColor(),
                calendarListDto.getTextColor(), calendarListDto.isAllDay());
        calendarRepository.save(calendar);

    }

}
