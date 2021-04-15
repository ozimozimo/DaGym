package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>,
        QuerydslPredicateExecutor<Board> {

    @Query("select b from Board b order by b.id DESC ")
    List<Board> findAllDesc();

    @Modifying
    @Query("update Board p set p.hit = p.hit + 1 where p.id = :hb_num")
    int updateView(Long hb_num);



}
