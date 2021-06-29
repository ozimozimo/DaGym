package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.domain.dto.ExerciseRecordDto;
import com.testcode.yjp.last.repository.ExerciseRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    @Transactional(readOnly = true)
    public List<ExerciseRecordDto> findAll(String id) {
        System.out.println("service id = " + id);
        log.info("ExRecord Service List");
        return exerciseRecordRepository.findAllDesc(id).stream()
                .map(ExerciseRecordDto::new)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public List<ExerciseRecordDto> findByIdWithExDate(String id, String ex_date) {
//        log.info("service id, ex_date get");
//        System.out.println("id = " + id);
//        System.out.println("ex_date = " + ex_date);
//        return exerciseRecordRepository.findByIdWithExDate(id, ex_date).stream()
//                .map(ExerciseRecordDto::new)
//                .collect(Collectors.toList());
//    }

    // 데이터 추가
    @Transactional
    public Long save(ExerciseRecordDto exerciseRecordDto) {
        return exerciseRecordRepository.save(exerciseRecordDto.toEntity()).getEx_record_id();
    }

    // 데이터 삭제
    @Transactional
    public void delete(Long id) {
        exerciseRecordRepository.deleteById(id);
    }
}
