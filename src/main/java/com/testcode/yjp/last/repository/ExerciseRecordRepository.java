package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Exercise;
import com.testcode.yjp.last.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExRecord, Long> {

    // 전체 데이터 가져오기
    @Query("select e from ExRecord e where e.member.user_id = :id")
    List<ExRecord> findAllDesc(String id);

    @Query("select e from ExRecord e where e.member.id = :id and e.ex_date = :ex_date")
    List<ExRecord> findAllDesc(Long id, String ex_date);

    // 날짜에 맞는 데이터 가져오기
    @Query(value = "select e from ExRecord e where e.member.user_id = :id and e.ex_date = :ex_date")
    List<ExRecord> findByIdWithExDate(String id, String ex_date);

    @Query("select e from Exercise e where e.ex_category like :category and e.ex_parts like :parts")
    List<Exercise> findBySelectAll(String category, String parts);

    @Query("select e from Exercise e where e.ex_category like :category")
    List<Exercise> findBySelectCategory(String category);

    @Query("select e from Exercise e where e.ex_parts like :parts")
    List<Exercise> findBySelectParts(String parts);

    @Query("select e from ExRecord e where e.exercise = :exercise and e.member = :member")
    ExRecord findByMemberAndExercise(Exercise exercise, Member member);
}
