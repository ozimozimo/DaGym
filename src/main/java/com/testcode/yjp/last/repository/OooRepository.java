package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.OneOnOne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OooRepository extends JpaRepository<OneOnOne, Long>, QuerydslPredicateExecutor<OneOnOne> {
    Page<OneOnOne> findByCategory(String category, Pageable pageable);

    Page<OneOnOne> findByAnswerIsNull(Pageable pageable);

    List<OneOnOne> findByAnswerIsNull();
}
