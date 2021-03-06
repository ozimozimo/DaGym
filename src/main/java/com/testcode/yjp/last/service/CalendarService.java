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
    public List<CalendarListDto> findAll(Long member_id, Long trainer_id) {
        return calendarRepository.findAllDesc(member_id, trainer_id).stream()
                .map(CalendarListDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(String calendar_start, String calendar_end, Long member_id, Long trainer_id) {
        calendarRepository.deleteCalendar(calendar_start,calendar_end,member_id,trainer_id);

    }

    public void update(Long id, CalendarListDto calendarListDto) {
        Calendar calendar = calendarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다다. id=" + id));;
        calendar.update(calendarListDto.getTitle(), calendarListDto.getStart(), calendarListDto.getEnd(),
                calendarListDto.getDescription(), calendarListDto.getType(), calendarListDto.getBackgroundColor(),
                calendarListDto.getTextColor());
        calendarRepository.save(calendar);

    }

    public void timeUpdate(CalendarListDto calendarListDto) {
        Calendar calendar = calendarRepository.findById(calendarListDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다다. id=" + calendarListDto.getId()));
        calendar.ChangeTime(calendarListDto.getStart(), calendarListDto.getEnd());
        calendarRepository.save(calendar);
    }

    public void reSizeUpdate(CalendarListDto calendarListDto) {
        Calendar calendar = calendarRepository.findById(calendarListDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다다. id=" + calendarListDto.getId()));
        calendar.ResizeTime(calendarListDto.getStart(), calendarListDto.getEnd());
        calendarRepository.save(calendar);
    }
}
