package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.ExRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRecordRepository extends JpaRepository<ExRecord, Long> {

    // 전체 데이터 가져오기
    @Query("select e from ExRecord e where e.ex_record_member_id = :id")
    List<ExRecord> findAllDesc(String id);

    // 날짜에 맞는 데이터 가져오기
    @Query(value = "select * from ExRecord e where e.ex_record_member_id = :id and e.ex_date = :ex_date", nativeQuery = true)
    List<ExRecord> findByIdWithExDate(String id, String ex_date);
}
