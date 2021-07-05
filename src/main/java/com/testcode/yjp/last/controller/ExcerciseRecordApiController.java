package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Exercise;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.ExCategoryDto;
import com.testcode.yjp.last.domain.dto.ExerciseRecordDto;
import com.testcode.yjp.last.repository.ExerciseRecordRepository;
import com.testcode.yjp.last.repository.ExerciseRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.ExerciseRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.reflections.util.ConfigurationBuilder.build;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ExRecord")
@Slf4j
public class ExcerciseRecordApiController {

    private final ExerciseRecordService exerciseRecordService;
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;

    // 오늘 데이터 보여주기
    @GetMapping("/exDate")
    public List<ExerciseRecordDto> exDate(String id, String ex_date) {
        log.info(id + " = id , " +ex_date + " = ex_date");
        List<ExRecord> byIdWithExDate = exerciseRecordRepository.findByIdWithExDate(id, ex_date);
        List<ExerciseRecordDto> exerciseRecordDtos = new ArrayList<>();
        for (ExRecord e : byIdWithExDate) {
            ExerciseRecordDto exerciseRecordDto = ExerciseRecordDto.builder()
                    .ex_record_id(e.getEx_record_id())
                    .exercise_id(e.getExercise().getEx_id())
                    .ex_count(e.getEx_count())
                    .ex_date(e.getEx_date())
                    .ex_meter(e.getEx_meter())
                    .ex_set(e.getEx_set())
                    .ex_time(e.getEx_time())
                    .ex_weight(e.getEx_weight())
                    .ex_record_member_id(e.getMember().getId())
                    .ex_name(e.getEx_name())
                    .ex_category(e.getExercise().getEx_category())
                    .ex_parts(e.getEx_parts())
                    .kcal(e.getKcal())
                    .build();
            exerciseRecordDtos.add(exerciseRecordDto);
        }
        return exerciseRecordDtos;
    }
//
//    // 클릭한 날짜 데이터 보여주기
//    @GetMapping("/clickDate")
//    public List<ExerciseRecordDto> clickDate(String id, String ex_date) {
//        log.info("Clicked Ex Date Data");
//        System.out.println("id = " + id);
//        System.out.println("ex_date = " + ex_date);
//        List<ExerciseRecordDto> byIdWithExDate = exerciseRecordService.findByIdWithExDate(id, ex_date);
//        System.out.println("dietService.findByIdWithExDate(id, ex_date) = " + byIdWithExDate);
//        log.info("Clicked Date Exr Data HOLY MOLY");
//        return byIdWithExDate;
//    }


    // 데이터 추가
    @PostMapping("/save/{id}")
    public void save(@PathVariable Long id, @RequestBody ExerciseRecordDto exerciseRecordDto) {
        log.info("ExRecord Controller Save Api Post");

        log.info("ExRecord Controller Post id=" + id);
        log.info("ExRecord Controller Post kcal= " + exerciseRecordDto.getKcal());
        Member member = memberRepository.findById(id).get();

        Long exercise_id = exerciseRecordDto.getExercise_id();
        log.info("ExRecord Controller Post ex_id = " + exercise_id);
        Exercise exercise = new Exercise();
        ExRecord exRecord;
        if (exercise_id != null) {
            exercise = exerciseRepository.findById(exercise_id).get();
        }
        if (exerciseRecordDto.getEx_category().equals("렙만")) {
            log.info("렙만");
            exRecord = ExRecord.builder()
                    .ex_count(exerciseRecordDto.getEx_count())
                    .ex_date(exerciseRecordDto.getEx_date())
                    .ex_set(exerciseRecordDto.getEx_set())
                    .ex_parts(exercise.getEx_parts())
                    .ex_name(exercise.getEx_name())
                    .exercise(exercise)
                    .member(member)
                    .build();
        } else if (exerciseRecordDto.getEx_category().equals("유산소") ) {
            log.info("유산소");
            exRecord = ExRecord.builder()
                    .ex_date(exerciseRecordDto.getEx_date())
                    .ex_meter(exerciseRecordDto.getEx_meter())
                    .ex_set(exerciseRecordDto.getEx_set())
                    .ex_time(exerciseRecordDto.getEx_time())
                    .ex_parts(exercise.getEx_parts())
                    .ex_name(exercise.getEx_name())
                    .kcal(exerciseRecordDto.getKcal())
                    .exercise(exercise)
                    .member(member)
                    .build();
        }
        else if (exerciseRecordDto.getEx_category().equals("기타(유산소)")) {
            exercise = exerciseRepository.findByEx_category(exerciseRecordDto.getEx_category());
            log.info("ex_parts = " + exerciseRecordDto.getEx_parts() + ", ex_name = " + exerciseRecordDto.getEx_name());
            exRecord = ExRecord.builder()
                    .ex_date(exerciseRecordDto.getEx_date())
                    .ex_meter(exerciseRecordDto.getEx_meter())
                    .ex_set(exerciseRecordDto.getEx_set())
                    .ex_time(exerciseRecordDto.getEx_time())
                    .ex_parts(exerciseRecordDto.getEx_parts())
                    .ex_name(exerciseRecordDto.getEx_name())
                    .kcal(exerciseRecordDto.getKcal())
                    .exercise(exercise)
                    .member(member)
                    .build();
        }
        else if (exerciseRecordDto.getEx_category().equals("지속 시간")) {
            log.info("지속 시간");
            exRecord = ExRecord.builder()
                    .ex_date(exerciseRecordDto.getEx_date())
                    .ex_set(exerciseRecordDto.getEx_set())
                    .ex_time(exerciseRecordDto.getEx_time())
                    .ex_parts(exercise.getEx_parts())
                    .ex_name(exercise.getEx_name())
                    .exercise(exercise)
                    .member(member)
                    .build();
        } else {
            exRecord = ExRecord.builder()
                    .ex_count(exerciseRecordDto.getEx_count())
                    .ex_date(exerciseRecordDto.getEx_date())
                    .ex_set(exerciseRecordDto.getEx_set())
                    .ex_weight(exerciseRecordDto.getEx_weight())
                    .ex_parts(exercise.getEx_parts())
                    .ex_name(exercise.getEx_name())
                    .exercise(exercise)
                    .member(member)
                    .build();
        }

        log.info(exRecord.getEx_name());

        exerciseRecordRepository.save(exRecord);
//        return exRecord;
    }

    // 데이터 삭제
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        log.info("ExRecord Controller Delete Api Post");
        exerciseRecordService.delete(id);
        return id;
    }

    @PostMapping("/select")
    public List<ExCategoryDto> selectBox(@RequestBody ExCategoryDto exCategoryDto) {
        log.info("selectBox in");

        String category = exCategoryDto.getEx_category();
        String parts = exCategoryDto.getEx_parts();
        log.info("selectBox = " + category + parts);

        List<Exercise> bySelectBox = new ArrayList<>();
        log.info("selectbox list if문 시작");

        if (!category.isEmpty() && !parts.isEmpty()) {
            log.info("exercise in1");
            bySelectBox = exerciseRecordRepository.findBySelectAll(category, parts);
        } else if (category.isEmpty() && !parts.isEmpty()) {
            log.info("exercise in2");
            bySelectBox = exerciseRecordRepository.findBySelectParts(parts);
        } else if (!category.isEmpty() && parts.isEmpty()) {
            log.info("exercise in3");
            bySelectBox = exerciseRecordRepository.findBySelectCategory(category);
            log.info(bySelectBox.get(0).getEx_name());
        } else {
            log.info("exercise in4");
            bySelectBox = exerciseRepository.findAll();
        }

        List<ExCategoryDto> exCategoryDtos = new ArrayList<>();
        for (Exercise exercise : bySelectBox) {

            ExCategoryDto e = ExCategoryDto.builder()
                    .ex_id(exercise.getEx_id())
                    .ex_category(exercise.getEx_category())
                    .ex_name(exercise.getEx_name())
                    .ex_parts(exercise.getEx_parts())
                    .build();
            exCategoryDtos.add(e);
        }

//        log.info("if문 끝" + bySelectBox.get(0).getEx_name());
        return exCategoryDtos;
    }

    @PostMapping("/nameSelect/{id}")
    public Exercise selectName(@PathVariable("id") Long id) {
        log.info("id = " + id);
        Exercise exercise = exerciseRepository.findById(id).get();
        log.info(exercise.getEx_category());

        return exercise;
    }

    @PostMapping("/random")
    public ExCategoryDto findRandom(@RequestBody ExCategoryDto exCategoryDto) {
        Exercise rand = exerciseRepository.findRand(exCategoryDto.getEx_category(), exCategoryDto.getEx_parts());
        log.info("id = "+rand.getEx_id());
        ExCategoryDto e = ExCategoryDto.builder()
                .ex_id(rand.getEx_id())
                .ex_category(rand.getEx_category())
                .ex_name(rand.getEx_name())
                .ex_parts(rand.getEx_parts())
                .build();
        return e;

    }


}
