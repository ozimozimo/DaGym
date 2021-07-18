package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Exercise;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.ExCategoryDto;
import com.testcode.yjp.last.domain.dto.ExerciseRecordDto;
import com.testcode.yjp.last.domain.dto.android.AndExerciseRecordDto;
import com.testcode.yjp.last.repository.ExerciseRecordRepository;
import com.testcode.yjp.last.repository.ExerciseRepository;
import com.testcode.yjp.last.repository.android.AndroidExcerciseRecordRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.service.android.AndExRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/android/exRecord")
@Slf4j
public class AndroidExcerciseRecordController {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final AndroidMemberRepository memberRepository;
    private final AndExRecordService andExRecordService;

    // 운동기록 저장
    @PostMapping("/saveExRecord/{id}")
    public void saveExRecord(@PathVariable Long id, @RequestBody ExerciseRecordDto exerciseRecordDto) {
        ExRecord exRecord = new ExRecord(exerciseRecordDto);
        Member member = memberRepository.findById(id).get();
        Exercise exercise = exerciseRepository.findById(exerciseRecordDto.getExercise_id()).get();
        exRecord.setMember(member);
        exRecord.setExercise(exercise);
        exerciseRecordRepository.save(exRecord);
    }

    // 운동기록 조회
    @PostMapping("/selectExRecord/{id}")
    public ArrayList<ExerciseRecordDto> selectExRecord(@PathVariable("id") Long id, @RequestBody ExerciseRecordDto e) {
        List<ExRecord> exRecords = exerciseRecordRepository.findAllDesc(id, e.getEx_date());
        ArrayList<ExerciseRecordDto> exerciseRecordDtos = new ArrayList<>();
        for (ExRecord exRecord : exRecords) {
            ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto(exRecord);
            exerciseRecordDtos.add(exerciseRecordDto);
        }

        return exerciseRecordDtos;
    }

    @PostMapping("/exercises/select")
    public ArrayList<ExCategoryDto> exercisesSelect(@RequestBody ExCategoryDto exCategoryDto) {
        String ex_category = exCategoryDto.getEx_category();
        String ex_parts = exCategoryDto.getEx_parts();

        List<Exercise> exercises = new ArrayList<>();
        if (ex_category.equals("all")) {
            if (ex_parts.equals("all")) {
                exercises = exerciseRepository.findAll();
            } else {
                exercises = exerciseRepository.findByEx_parts(ex_parts);
            }
        } else {
            if (ex_parts.equals("all")) {
                exercises = exerciseRepository.findByEx_categorys(ex_category);
            } else {
                exercises = exerciseRepository.findByEx_categorysAndEx_parts(ex_category, ex_parts);
            }
        }

        ArrayList<ExCategoryDto> exDtos = new ArrayList<>();
        for (Exercise e : exercises) {
            ExCategoryDto exDto = new ExCategoryDto(e);

            exDtos.add(exDto);
        }

        return exDtos;
    }

    @PostMapping("/exercise/one/{id}")
    public ExCategoryDto selectOne(@PathVariable("id") Long id) {
        Exercise exercise = exerciseRepository.findById(id).get();
        return new ExCategoryDto(exercise);
    }

    // 운동기록 하나만 뽑기
    @PostMapping("/detailExRecord/{id}")
    public ExRecord detailALong(@PathVariable Long id) {

        ExRecord detail = exerciseRecordRepository.findById(id).get();
        System.out.println(detail + "===================================");
        return detail;
    }

    // 운동기록 수정
    @PostMapping("/updateExRecord/{id}")
    public Long update(@PathVariable Long id, @RequestBody AndExerciseRecordDto exerciseRecordDto) {
        return andExRecordService.update(id, exerciseRecordDto);
    }


    // 운동기록 삭제
    @PostMapping("/deleteExRecord/{id}")
    public Long delete(@PathVariable Long id) {
        log.info("ExRecord Controller Delete Api Post");
        return andExRecordService.delete(id);
    }


}
