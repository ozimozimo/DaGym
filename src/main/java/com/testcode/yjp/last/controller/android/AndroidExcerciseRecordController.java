package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndExerciseRecordDto;
import com.testcode.yjp.last.repository.android.AndroidExcerciseRecordRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.service.android.AndExRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequiredArgsConstructor
@RequestMapping("/android/ExRecord")
@Slf4j
public class AndroidExcerciseRecordController {

    private final AndroidExcerciseRecordRepository excerciseRecordRepository;
    private final AndroidMemberRepository memberRepository;
    private final AndExRecordService andExRecordService;


    // 운동기록 저장
    @PostMapping("/saveExRecord/{id}")
    public Long saveExRecord(@PathVariable Long id, @RequestBody AndExerciseRecordDto exerciseRecordDto) {
        return andExRecordService.save(id, exerciseRecordDto);
    }

    // 운동기록 조회
    @PostMapping("/selectExRecord")
    public ArrayList<AndExerciseRecordDto> selectExRecord(@RequestBody AndExerciseRecordDto exerciseRecordDto) {
        return andExRecordService.select(exerciseRecordDto);
    }


    // 운동기록 수정
    @PostMapping("/update/{id}")
    public Long update(@PathVariable Long id, AndExerciseRecordDto exerciseRecordDto) {
        return andExRecordService.update(id, exerciseRecordDto);
    }


    // 운동기록 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        log.info("ExRecord Controller Delete Api Post");
        return andExRecordService.delete(id);
    }


}