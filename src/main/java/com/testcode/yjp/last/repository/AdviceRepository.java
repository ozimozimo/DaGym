package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdviceRepository extends JpaRepository<Advice, Long> {

    @Query("select a from Advice a order by a.id DESC")
    List<Advice> findAllDesc();
}
