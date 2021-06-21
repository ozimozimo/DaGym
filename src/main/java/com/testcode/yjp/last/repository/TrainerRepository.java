package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<TrainerInfo, Long>, QuerydslPredicateExecutor<TrainerInfo> {


    @Query("select m,t from Member m, TrainerInfo t where m.id=t.member.id and m.user_role='trainer' ")
    List<Object[]> getTrainerList();

    @Query("select t from TrainerInfo t where t.member.id=:member_id")
    TrainerInfo findTrainer_id(Long member_id);
}
