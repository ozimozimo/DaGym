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
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/android/exRecord")
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

    // 운동기록 하나만 뽑기
    @PostMapping("/detailExRecord/{id}")
    public ExRecord detailALong(@PathVariable Long id) {
        ExRecord detail = excerciseRecordRepository.findById(id).get();
        System.out.println(detail+"===================================");
        return detail;
    }

    // 운동기록 수정
    @PostMapping("/updateExRecord/{id}")
    public Long update(@PathVariable Long id,@RequestBody AndExerciseRecordDto exerciseRecordDto) {
        return andExRecordService.update(id, exerciseRecordDto);
    }


    // 운동기록 삭제
    @PostMapping("/deleteExRecord/{id}")
    public Long delete(@PathVariable Long id) {
        log.info("ExRecord Controller Delete Api Post");
        return andExRecordService.delete(id);
    }


}
