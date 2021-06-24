package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Boastboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoastboardRepository extends JpaRepository<Boastboard, Long>, QuerydslPredicateExecutor<Boastboard> {

    @Query("select b from Boastboard b order by b.id DESC ")
    List<Boastboard> findAllDesc();

}
