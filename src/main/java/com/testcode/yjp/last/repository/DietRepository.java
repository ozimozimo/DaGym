package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DietRepository extends JpaRepository<Diet,Long> {

    @Query("select d from Diet d where d.diet_member_id = :id and d.modDate = :modDate")
    List<Diet> findModDate(String id, String modDate);

    @Query("select d from Diet d where d.diet_member_id = :id")
    List<Diet> findAllDesc(String id);
}