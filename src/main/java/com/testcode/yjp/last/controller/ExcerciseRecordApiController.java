package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.ExerciseRecordDto;
import com.testcode.yjp.last.repository.ExerciseRecordRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.ExerciseRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ExRecord")
@Slf4j
public class ExcerciseRecordApiController {

    private final ExerciseRecordService exerciseRecordService;
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final MemberRepository memberRepository;

    // 오늘 데이터 보여주기
    @GetMapping("/exDate")
    public List<ExerciseRecordDto> exDate(String id, String ex_date) {
        log.info("Today Ex Data");
        System.out.println("id = " + id);
        System.out.println("ex_date = " + ex_date);
        List<ExerciseRecordDto> byIdWithExDate = exerciseRecordService.findByIdWithExDate(id, ex_date);
        System.out.println("dietService.findByIdWithExDate(id, ex_date) = " + byIdWithExDate);
        log.info("Today Exr Data HOLY MOLY");
        return byIdWithExDate;
    }

    // 클릭한 날짜 데이터 보여주기
    @GetMapping("/clickDate")
    public List<ExerciseRecordDto> clickDate(String id, String ex_date) {
        log.info("Clicked Ex Date Data");
        System.out.println("id = " + id);
        System.out.println("ex_date = " + ex_date);
        List<ExerciseRecordDto> byIdWithExDate = exerciseRecordService.findByIdWithExDate(id, ex_date);
        System.out.println("dietService.findByIdWithExDate(id, ex_date) = " + byIdWithExDate);
        log.info("Clicked Date Exr Data HOLY MOLY");
        return byIdWithExDate;
    }


    // 데이터 추가
    @PostMapping("/save/{id}")
    public ExRecord save(@PathVariable Long id, @RequestBody ExRecord exRecord) {
        log.info("ExRecord Controller Save Api Post");
        Optional<Member> result = memberRepository.findById(id);
        exRecord.setMember(result.get());
        exerciseRecordRepository.save(exRecord);
        return exRecord;
    }

    // 데이터 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        log.info("ExRecord Controller Delete Api Post");
        exerciseRecordService.delete(id);
        return id;
    }
}
