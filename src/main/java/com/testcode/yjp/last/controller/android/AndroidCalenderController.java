package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndInsertCalDto;
import com.testcode.yjp.last.repository.android.AndroidBoardRepository;
import com.testcode.yjp.last.repository.android.AndroidCalendarRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/calender")
public class AndroidCalenderController {
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

    @DeleteMapping("delete/{calender_id}")
    public void deleteCal(@PathVariable("calender_id") Long calender_id) {
        Calendar calendar = androidCalendarRepository.findById(calender_id).get();

        androidCalendarRepository.delete(calendar);
    }
}
