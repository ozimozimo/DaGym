package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.dto.CalendarListDto;
import com.testcode.yjp.last.domain.dto.CommentsListResponseDto;
import com.testcode.yjp.last.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


}
