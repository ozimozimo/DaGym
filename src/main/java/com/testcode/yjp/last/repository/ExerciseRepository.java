package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query(value = "select * from Exercise e where e.ex_category like :category and e.ex_parts like :parts order by DBMS_RANDOM.RANDOM()", nativeQuery = true)
    List<Exercise> findRand(String category, String parts);

    @Query("select e from Exercise e where e.ex_category =:ex_category")
    Exercise findByEx_category(String ex_category);
}
