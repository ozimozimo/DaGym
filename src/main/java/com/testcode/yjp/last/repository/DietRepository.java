package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.dto.DietDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DietRepository extends JpaRepository<Diet,Long> {

    @Query("select d from Diet d where d.member.user_id = :id")
    List<Diet> findAllDesc(String id);

    // 날짜에 맞는 데이터 가져오기
    @Query(value = "select * from Diet d where d.diet_member_id = :id and d.diet_date = :diet_date", nativeQuery = true)
    List<Diet> findByIdWithDietDate(String id, String diet_date);
}
