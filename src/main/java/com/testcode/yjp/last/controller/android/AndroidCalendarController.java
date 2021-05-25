package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndInsertCalDto;
import com.testcode.yjp.last.repository.android.AndroidCalendarRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/calendar")
public class AndroidCalendarController {
    private final AndroidCalendarRepository androidCalendarRepository;
    private final AndroidMemberRepository androidMemberRepository;

    // 일정 조회
    @PostMapping("select/{member_id}")
    public ArrayList<AndInsertCalDto> selectCal(@PathVariable("member_id") Long member_id) {
        Member member = androidMemberRepository.findById(member_id).get();
        ArrayList<Calendar> calendarByMember = androidCalendarRepository.findCalendarByMember(member);
        ArrayList<AndInsertCalDto> andInsertCalDtos = new ArrayList<>();
        for (Calendar calendar : calendarByMember) {
            AndInsertCalDto andInsertCalDto = new AndInsertCalDto();
            andInsertCalDto.setId(calendar.getId());
            andInsertCalDto.setAllDay(calendar.isAllDay());
            andInsertCalDto.setDescription(calendar.getDescription());
            andInsertCalDto.setEnd(calendar.getEnd());
            andInsertCalDto.setStart(calendar.getStart());
            andInsertCalDto.setTitle(calendar.getTitle());

            andInsertCalDtos.add(andInsertCalDto);
        }

        return andInsertCalDtos;
    }

    // 일정 추가
    @PostMapping("insert/{member_id}")
    public void insertCal(@RequestBody AndInsertCalDto andInsertCalDto, @PathVariable("member_id") Long member_id) {
        Member member = androidMemberRepository.findById(member_id).get();

        Calendar calendar = new Calendar();
        calendar.setAllDay(andInsertCalDto.isAllDay());
        calendar.setDescription(andInsertCalDto.getDescription());
        calendar.setEnd(andInsertCalDto.getEnd());
        calendar.setStart(andInsertCalDto.getStart());
        calendar.setTitle(andInsertCalDto.getTitle());
        calendar.setType(andInsertCalDto.getType());
        calendar.setMember(member);

        androidCalendarRepository.save(calendar);
    }

    @PutMapping("update")
    public void updateCal(@RequestBody AndInsertCalDto andInsertCalDto){
        Calendar calendar = androidCalendarRepository.findById(andInsertCalDto.getId()).get();
        calendar.setAllDay(andInsertCalDto.isAllDay());
        calendar.setDescription(andInsertCalDto.getDescription());
        calendar.setEnd(andInsertCalDto.getEnd());
        calendar.setStart(andInsertCalDto.getStart());
        calendar.setTitle(andInsertCalDto.getTitle());
        calendar.setType(andInsertCalDto.getType());
        androidCalendarRepository.save(calendar);
    }

    @DeleteMapping("delete/{calendar_id}")
    public void deleteCal(@PathVariable("calendar_id") Long calendar_id) {
        Calendar calendar = androidCalendarRepository.findById(calendar_id).get();

        androidCalendarRepository.delete(calendar);
    }

    @GetMapping("select/date/{member_id}")
    public ArrayList<AndInsertCalDto> selectDate(@PathVariable("member_id") Long member_id, @RequestBody AndInsertCalDto andInsertCalDto) {
        String start = andInsertCalDto.getStart();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = "";
        try {
            Date parse = simpleDateFormat.parse(start);
            format = simpleDateFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Member member = androidMemberRepository.findById(member_id).get();
        ArrayList<Calendar> calendarByDate = androidCalendarRepository.findCalendarByDate(member, format);
        ArrayList<AndInsertCalDto> andInsertCalDtos = new ArrayList<>();

        for (Calendar calendar : calendarByDate) {
            AndInsertCalDto dto = new AndInsertCalDto();
            dto.setId(calendar.getId());
            dto.setAllDay(calendar.isAllDay());
            dto.setDescription(calendar.getDescription());
            dto.setEnd(calendar.getEnd());
            dto.setStart(calendar.getStart());
            dto.setTitle(calendar.getTitle());

            andInsertCalDtos.add(dto);
        }
        return andInsertCalDtos;
    }
}
